package com.gosterim360.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private String message;  // Ödeme sonucu ile ilgili ek bilgi

    private String redirectUrl;
}