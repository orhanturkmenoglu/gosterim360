package com.gosterim360.service.impl;

import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.exception.*;
import com.gosterim360.mapper.ReservationMapper;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;
import com.gosterim360.repository.ReservationRepository;
import com.gosterim360.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper ;

    @Override
    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO request) {
        log.info("Attempting to create reservation for session: {}, seat: {}", request.getSessionId(), request.getSeatId());
        if (reservationRepository.existsBySession_IdAndSeat_Id(request.getSessionId(), request.getSeatId())) {
            log.warn("Reservation creation failed: seat already reserved for this session");
            throw new ReservationAlreadyExistsException("This seat is already reserved for the selected session.");
        }
        if (request.getStatus() == null || (!request.getStatus().equals(ReservationStatus.PRE_RESERVED) && !request.getStatus().equals(ReservationStatus.PAID))) {
            log.warn("Reservation creation failed: invalid status '{}'", request.getStatus());
            throw new ReservationStatusInvalidException("Invalid reservation status.");
        }
        Reservation reservation = reservationMapper.toEntity(request);
        Reservation saved = reservationRepository.saveAndFlush(reservation);
        log.info("Reservation created successfully with id: {}", saved.getId());
        return reservationMapper.toDTO(saved);
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
        if (request.getStatus() == null || (!request.getStatus().equals(ReservationStatus.PRE_RESERVED) && !request.getStatus().equals(ReservationStatus.PAID))) {
            log.warn("Reservation update failed: invalid status '{}'", request.getStatus());
            throw new ReservationStatusInvalidException("Invalid reservation status.");
        }

        Session session = new Session();
        session.setId(request.getSessionId());
        Seat seat = new Seat();
        seat.setId(request.getSeatId());

        reservation.setSession(session);
        reservation.setSeat(seat);
        reservation.setStatus(request.getStatus());
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