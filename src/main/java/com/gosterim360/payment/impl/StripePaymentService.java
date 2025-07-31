package com.gosterim360.payment.impl;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.exception.ReservationNotFoundException;
import com.gosterim360.model.Reservation;
import com.gosterim360.notification.NotificationService;
import com.gosterim360.payment.PaymentService;
import com.gosterim360.payment.StripeApiClient;
import com.gosterim360.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentService implements PaymentService {

    private final ReservationRepository reservationRepository;
    private final StripeApiClient stripeApiClient; //   Stripe ödeme entegrasyonu
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void processPayment(UUID reservationId, PaymentRequestDTO paymentRequestDTO) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        if (!reservation.getStatus().equals(ReservationStatus.PRE_RESERVED)){
            throw new IllegalStateException("Reservation is not in a payable state");
        }

        try {
            boolean isSuccess = stripeApiClient.charge(paymentRequestDTO);

            if (isSuccess){
                reservation.setStatus(ReservationStatus.PAID);
                notificationService.sendReservationConfirmation(reservation);
                log.info("Ödeme başarılı. Rezervasyon ID: {}", reservation.getId());

            }else{
                reservation.setStatus(ReservationStatus.CANCELLED);
                notificationService.sendReservationExpired(reservation);
                log.warn("Ödeme başarısız. Rezervasyon ID: {}", reservation.getId());
            }

            reservationRepository.save(reservation);
        }catch (Exception e){
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
            notificationService.sendReservationExpired(reservation);
            log.error("Ödeme işlemi sırasında hata oluştu. Rezervasyon ID: {}", reservation.getId(), e);
            throw new RuntimeException("Ödeme işlemi başarısız oldu");
        }

    }
}
