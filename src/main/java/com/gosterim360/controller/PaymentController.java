package com.gosterim360.controller;

import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/reservation/{reservationId}")
    public ResponseEntity<String> payForReservation(
            @PathVariable UUID reservationId,
            @RequestBody PaymentRequestDTO paymentRequestDTO) {

        try {
            paymentService.processPayment(reservationId, paymentRequestDTO);
            return ResponseEntity.ok("Payment successful");
        } catch (IllegalStateException ise) {
            return ResponseEntity.badRequest().body(ise.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Payment failed");
        }
    }

}
