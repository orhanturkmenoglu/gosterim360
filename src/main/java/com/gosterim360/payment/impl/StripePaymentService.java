package com.gosterim360.payment.impl;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.dto.response.PaymentResponseDTO;
import com.gosterim360.enums.PaymentStatus;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.exception.ReservationNotFoundException;
import com.gosterim360.mapper.PaymentMapper;
import com.gosterim360.model.Payment;
import com.gosterim360.model.Reservation;
import com.gosterim360.notification.NotificationService;
import com.gosterim360.payment.PaymentService;
import com.gosterim360.payment.StripeApiClient;
import com.gosterim360.repository.PaymentRepository;
import com.gosterim360.repository.ReservationRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final StripeApiClient stripeApiClient; //   Stripe ödeme entegrasyonu
    private final NotificationService notificationService;

    @Override
    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        Reservation reservation = reservationRepository.findById(paymentRequestDTO.getReservationId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        if (!reservation.getStatus().equals(ReservationStatus.PRE_RESERVED)) {
            throw new IllegalStateException("Reservation is not in a payable state");
        }

        try {
            String checkoutUrl = stripeApiClient.startCheckoutSession(paymentRequestDTO);

            if (checkoutUrl == null || checkoutUrl.isEmpty()) {
                log.error("Checkout URL oluşturulamadı, ödeme sayfasına yönlendirme başarısız.");
                throw new RuntimeException("Ödeme sayfası oluşturulamadı");
            }

            log.info("Checkout URL başarıyla oluşturuldu: {}", checkoutUrl);

            // Burada ödeme kaydı oluşturma veya rezervasyon durum güncelleme yapılmaz.
            // Çünkü ödeme henüz tamamlanmadı, ödeme onayı confirmPayment veya webhook ile alınacak.

            return PaymentResponseDTO.builder()
                    .redirectUrl(checkoutUrl)
                    .message("Lütfen ödeme sayfasına yönlendirin.")
                    .build();

        } catch (Exception e) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
            notificationService.sendReservationExpired(reservation);
            log.error("Ödeme işlemi sırasında hata oluştu. Rezervasyon ID: {}", reservation.getId(), e);
            throw new RuntimeException("Ödeme işlemi başarısız oldu");
        }
    }

    @Override
    @Transactional
    public PaymentResponseDTO confirmPayment(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);

            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
                String reservationId = session.getMetadata().get("reservation_id");

                Reservation reservation = reservationRepository.findById(UUID.fromString(reservationId))
                        .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

                // Ödeme öncesi başka durum kontrolü yapabilirsin, örneğin zaten PAID ise tekrar işleme alma
                if (reservation.getStatus() == ReservationStatus.PAID) {
                    log.info("Rezervasyon zaten PAID durumda, tekrar işleme alınmadı. ReservationId: {}", reservationId);
                    return PaymentResponseDTO.builder()
                            .message("Ödeme zaten tamamlanmış.")
                            .build();
                }

                Payment payment = Payment.builder()
                        .amount(reservation.getSession().getTimes().getFirst().getPrice())
                        .currency("try")
                        .status(PaymentStatus.SUCCESS)
                        .transactionId(session.getPaymentIntent()) // Stripe paymentIntent id'si transactionId olarak kullanılabilir
                        .reservationId(reservation.getId()) // İlişkilendirme için
                        .build();

                paymentRepository.save(payment);

                reservation.setStatus(ReservationStatus.PAID);
                reservationRepository.save(reservation);

                notificationService.sendPaymentSuccess(reservation);

                log.info("Ödeme onaylandı ve rezervasyon güncellendi. ReservationId: {}", reservationId);

                return PaymentResponseDTO.builder()
                        .message("Ödeme başarıyla tamamlandı")
                        .build();
            } else {
                log.warn("Ödeme durumu 'paid' değil: {}", session.getPaymentStatus());
                return PaymentResponseDTO.builder()
                        .message("Ödeme tamamlanmadı veya başarısız oldu")
                        .build();
            }
        } catch (StripeException e) {
            log.error("Stripe API hatası: {}", e.getMessage(), e);
            throw new RuntimeException("Stripe API hatası: " + e.getMessage());
        }
    }
}
