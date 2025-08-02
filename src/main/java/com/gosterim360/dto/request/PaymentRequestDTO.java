package com.gosterim360.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "Rezervasyon ID boş olamaz")
    private UUID reservationId;

    @Pattern(regexp = "^(try|usd|eur)$", message = "Geçerli para birimi: try, usd veya eur")
    private String currency;

    private int seatCount; // yeni eklenecek
    private BigDecimal totalPrice; // opsiyonel ama önerilir
}
