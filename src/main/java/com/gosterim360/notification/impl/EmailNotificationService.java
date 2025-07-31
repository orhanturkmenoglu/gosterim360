package com.gosterim360.notification.impl;

import com.gosterim360.model.Reservation;
import com.gosterim360.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendReservationConfirmation(Reservation reservation) {
        log.info("Sending reservation confirmation to user");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservation.getUser().getEmail());
        message.setSubject("Rezervasyon Onayı");
        message.setText("Merhaba " + reservation.getUser().getFirstName() + ",\n\n" +
                "Rezervasyonunuz onaylanmıştır.\n" +
                "Seans: " + reservation.getSession().getMovie().getName() + "\n" +
                "Koltuk: " + reservation.getSeat().getSeatNumber() + "\n\n" +
                "Teşekkür ederiz.");

        javaMailSender.send(message);

    }

    @Override
    public void sendPaymentSuccess(Reservation reservation) {

    }

    @Override
    public void sendReservationExpired(Reservation reservation) {

    }
}
