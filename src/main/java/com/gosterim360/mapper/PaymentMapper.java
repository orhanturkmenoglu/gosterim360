package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.dto.response.PaymentResponseDTO;
import com.gosterim360.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper  extends BaseMapper<Payment, PaymentResponseDTO, PaymentRequestDTO> {
    @Override
    public PaymentResponseDTO toDTO(Payment entity) {
        return PaymentResponseDTO.builder()
                .message("Ödeme işlemi başarılı")
                .build();
    }

    @Override
    public Payment toEntity(PaymentRequestDTO request) {
        return Payment.builder()
                .reservationId(request.getReservationId())
                .currency(request.getCurrency())
                .build();
    }
}
