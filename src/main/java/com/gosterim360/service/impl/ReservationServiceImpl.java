package com.gosterim360.service.impl;

import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.exception.*;
import com.gosterim360.mapper.ReservationMapper;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;
import com.gosterim360.model.User;
import com.gosterim360.notification.NotificationService;
import com.gosterim360.repository.ReservationRepository;
import com.gosterim360.repository.SeatRepository;
import com.gosterim360.repository.SessionRepository;
import com.gosterim360.repository.UserRepository;
import com.gosterim360.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private  final SessionRepository sessionRepository;
    private final ReservationMapper reservationMapper ;

    private final NotificationService notificationService;


    @Override
    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO request) {
        log.info("Attempting to create reservation for session: {}, seat: {}", request.getSessionId(), request.getSeatId());
        if (reservationRepository.existsBySession_IdAndSeat_Id(request.getSessionId(), request.getSeatId())) {
            log.warn("Reservation creation failed: seat already reserved for this session");
            throw new ReservationAlreadyExistsException("This seat is already reserved for the selected session.");
        }


        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(()->new SessionNotFoundException("Session not found"));

        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setUser(user);
        reservation.setSeat(seat);
        reservation.setSession(session);

        if (reservation.getStatus() == null || (!reservation.getStatus().equals(ReservationStatus.PRE_RESERVED) && !reservation.getStatus().equals(ReservationStatus.PAID))) {
            log.warn("Reservation creation failed: invalid status '{}'", reservation.getStatus());
            throw new ReservationStatusInvalidException("Invalid reservation status.");
        }

        Reservation saved = reservationRepository.saveAndFlush(reservation);
        log.info("Reservation created successfully with id: {}", saved.getId());

        //  Bildirim gönderiliyor
        notificationService.sendReservationConfirmation(saved);

        log.info("Reservation created successfully :",saved);

        return reservationMapper.toDTO(saved);
    }

    /*
    Evet, bu @Scheduled görev gayet başarılı yazılmış.
     60 saniyede bir çalışıyor ve oluşturulma süresi
     1 dakikadan eski olan PRE_RESERVED durumundaki rezervasyonları
      EXPIRED olarak işaretliyor. Bildirim gönderip DB'ye kaydediyor. 👇
     */
    @Scheduled(fixedRate = 300_000) // // her 5 dakikada bir çalışır
    @Transactional
    public void  expireUnpaidReservations(){
        Instant threshold = Instant.now().minusSeconds(300); //  5 dakika önce
        List<Reservation> expiredReservations = reservationRepository.findExpiredPreReservedReservations(threshold);

        if (expiredReservations.isEmpty()){
            return;
        }

        for (Reservation reservation : expiredReservations){
            reservation.setStatus(ReservationStatus.EXPIRED);
            log.info("Expire task ran but no unpaid PRE_RESERVED reservations older than 5 minutes found.");
            notificationService.sendReservationExpired(reservation);
        }

        reservationRepository.saveAll(expiredReservations);
    }


    @Override
    @Transactional
    public ReservationResponseDTO update(UUID id, ReservationRequestDTO request) {
        log.info("Attempting to update reservation with id: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reservation update failed: id '{}' not found", id);
                    return new ReservationNotFoundException("Reservation not found.");
                });

        UUID currentSessionId = reservation.getSession() != null ? reservation.getSession().getId() : null;
        UUID currentSeatId = reservation.getSeat() != null ? reservation.getSeat().getId() : null;

        if (!currentSessionId.equals(request.getSessionId()) || !currentSeatId.equals(request.getSeatId())) {
            if (reservationRepository.existsBySession_IdAndSeat_Id(request.getSessionId(), request.getSeatId())) {
                log.warn("Reservation update failed: seat already reserved for this session");
                throw new ReservationSeatUnavailableException("This seat is already reserved for the selected session.");
            }
        }
        if (reservation.getStatus() == null || (!reservation.getStatus().equals(ReservationStatus.PRE_RESERVED) && !reservation.getStatus().equals(ReservationStatus.PAID))) {
            log.warn("Reservation update failed: invalid status '{}'", reservation.getStatus());
            throw new ReservationStatusInvalidException("Invalid reservation status.");
        }

        Session session = new Session();
        session.setId(request.getSessionId());
        Seat seat = new Seat();
        seat.setId(request.getSeatId());

        reservation.setSession(session);
        reservation.setSeat(seat);
        reservation.setStatus(reservation.getStatus());
        Reservation updated = reservationRepository.saveAndFlush(reservation);
        log.info("Reservation updated successfully with id: {}", updated.getId());
        return reservationMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Attempting to delete reservation with id: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reservation deletion failed: id '{}' not found", id);
                    return new ReservationNotFoundException("Reservation not found.");
                });
        if (reservation.getStatus() == ReservationStatus.PAID) {
            log.warn("Reservation deletion failed: reservation already paid");
            throw new ReservationDeleteNotAllowedException("Cannot delete a reservation that has already been paid.");
        }
        reservationRepository.delete(reservation);
        log.info("Reservation deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO getById(UUID id) {
        log.info("Fetching reservation with id: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reservation fetch failed: id '{}' not found", id);
                    return new ReservationNotFoundException("Reservation not found.");
                });
        log.info("Reservation fetched successfully with id: {}", id);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getAll() {
        log.info("Fetching all reservations");
        List<ReservationResponseDTO> reservations = reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Fetched {} reservations", reservations.size());
        return reservations;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getBySessionId(UUID sessionId) {
        log.info("Fetching reservations for session: {}", sessionId);
        List<ReservationResponseDTO> reservations = reservationRepository.findAllBySession_Id(sessionId)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Fetched {} reservations for session: {}", reservations.size(), sessionId);
        return reservations;
    }


  /*  Sonraki Adım: Rezervasyon Sonrası İşlemleri ve İş Akışı Yönetimi
1. Rezervasyon Sonrası:
    Rezervasyon yapıldıktan sonra, kullanıcıya rezervasyon detaylarının onaylandığı bir bildirim (email, SMS, push notification) gönderilmeli.

    Rezervasyonun durumu (status) PRE_RESERVED iken, belirli bir süre içerisinde kullanıcı ödeme yapmazsa rezervasyonun iptal edilmesi (timeout mekanizması).

    Ödeme onayı gelirse rezervasyonun durumu CONFIRMED olarak güncellenmeli.

            2. Ödeme İşlemi Entegrasyonu:
    Ödeme servisinin entegre edilmesi (örneğin Stripe, PayPal vb.).

    Ödeme başarıyla tamamlanırsa, rezervasyon durumu güncellenmeli.

    Ödeme başarısızsa veya iptal edilirse, rezervasyon iptal edilmeli.

3. Koltuk Yönetimi:
    Rezervasyon yapılırken seçilen koltukların başka bir rezervasyon tarafından bloke edilmemesi için kilitleme (locking) mekanizması eklenmeli.

    Aynı koltuğun çift rezervasyonunun engellenmesi.

4. Admin Paneli / Yönetim:
    Rezervasyonların listelenmesi, filtrelenmesi, iptali veya durumu değiştirme yetkisi.

    Kullanıcıların rezervasyon geçmişlerinin görüntülenmesi.

            5. Gelişmiş Doğrulamalar ve Hata Yönetimi:
    Rezervasyon yapılırken seans, koltuk, salon ve kullanıcı doğrulamalarının tam ve eksiksiz yapılması.

    Hata ve edge-case senaryoları için exception handling geliştirilmesi.*/
}