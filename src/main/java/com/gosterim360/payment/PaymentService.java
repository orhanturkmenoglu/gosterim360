package com.gosterim360.payment;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.dto.response.PaymentResponseDTO;
import com.gosterim360.model.Reservation;

import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO confirmPayment(String sessionId);
}
