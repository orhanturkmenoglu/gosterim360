package com.gosterim360.payment;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.exception.ReservationNotFoundException;
import com.gosterim360.model.Reservation;
import com.gosterim360.repository.ReservationRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class StripeApiClient {

    private final ReservationRepository reservationRepository;

    public String  startCheckoutSession(PaymentRequestDTO  paymentRequestDTO) throws StripeException {
        // 1. Rezervasyon durumunu kontrol et
        // 2. Ödeme sağlayıcıya request gönder
        // 3. Response başarılıysa payment kaydı oluştur
        // 4. Reservation durumunu PAID yap
        // 5. Ticket üret / QR oluştur
        // 6. PaymentResponseDTO dön
        // Gerçek Stripe entegrasyonu burada yapılmalı
        // Bu örnekte mock
        log.info("Stripe ödeme simülasyonu başlatıldı. ");

        Reservation reservation = reservationRepository.findById(paymentRequestDTO.getReservationId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.PRE_RESERVED){
            throw new IllegalStateException("Reservation not in a payable state");
        }

         BigDecimal unitPrice = reservation.getSession().getTimes().get(0).getPrice();
        long quantity = 1L; // Tek koltuk için quantity = 1

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080" + "/payment/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080" + "/payment/cancel?reservation_id=" + paymentRequestDTO.getReservationId())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(quantity)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("try")
                                                .setUnitAmount(unitPrice.
                                                        multiply(BigDecimal.valueOf(100)).longValue()) // kuruş cinsinden
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Gösterim360 Sinema Bileti")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("reservation_id", paymentRequestDTO.getReservationId().toString())
                .build();


      try {
          Session session = Session.create(params);
          return  session.getUrl();
      }catch (StripeException e) {
          log.error("Checkout oturumu oluşturulurken hata oluştu", e);
          return null;
      }
    }

}
