package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.PaymentRequestDTO;
import com.gosterim360.dto.response.PaymentResponseDTO;
import com.gosterim360.payment.PaymentService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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


    @PostMapping("/reservation")
    @Operation(summary = "Make a payment for a reservation",
            description = "Process payment for a specific reservation by ID")
    public ResponseEntity<BaseResponse<PaymentResponseDTO>> payForReservation(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {

        try {

            PaymentResponseDTO paymentResponseDTO = paymentService.processPayment(paymentRequestDTO);

            return ResponseEntity.ok(
                    BaseResponse.success(paymentResponseDTO, "Payment processed successfully", HttpStatus.OK.value())
            );
        } catch (IllegalStateException ise) {
            return ResponseEntity.badRequest()
                    .body(BaseResponse.failure(ise.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                    .body(BaseResponse.failure("Payment failed: " + e.getMessage(), HttpStatus.PAYMENT_REQUIRED.value()));
        }
    }


    /**
     * Ödeme tamamlandıktan sonra frontend'den gelen session_id ile ödeme onayı alınır.
     *
     * @param sessionId Stripe Checkout Session ID
     * @return Ödeme durumu bilgisi
     */
    @GetMapping("/confirm")
    public ResponseEntity<PaymentResponseDTO> confirmPayment(@RequestParam("session_id") String sessionId) {
        try {
            PaymentResponseDTO response = paymentService.confirmPayment(sessionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    PaymentResponseDTO.builder()
                            .message("Ödeme doğrulama sırasında beklenmeyen hata oluştu")
                            .build()
            );
        }
    }
}
