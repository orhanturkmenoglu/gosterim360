package com.gosterim360.payment;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.model.Reservation;

import java.util.UUID;

public interface PaymentService {

    void processPayment(UUID reservationId, PaymentRequestDTO paymentRequestDTO);
}
