package com.gosterim360.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {

    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;
    private Long amount; // kuruş cinsinden
    private String currency;
}
