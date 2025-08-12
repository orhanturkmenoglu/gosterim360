package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import com.gosterim360.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity<UUID> {

    @Column(nullable = false)
    private UUID reservationId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency; // tr, usd, eur

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // SUCCESS, FAILED, PENDING vs.

    private String transactionId; // ödeme sağlayıcıya gönderilen ID (opsiyonel)
}
