package com.gosterim360.payment;

import com.gosterim360.dto.request.PaymentRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StripeApiClient {

    public boolean charge(PaymentRequestDTO  paymentRequestDTO){
        // Gerçek Stripe entegrasyonu burada yapılmalı
        // Bu örnekte mock
        log.info("Stripe ödeme simülasyonu başlatıldı. Kart: {}", paymentRequestDTO.getCardNumber());

        // test kartı ise başarılı, değilse başarısız diyelim
        return paymentRequestDTO.getCardNumber().startsWith("4242");
    }
}
