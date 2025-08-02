package com.gosterim360.notification.impl;

import com.gosterim360.model.Reservation;
import com.gosterim360.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender javaMailSender;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    @Override
    public void sendReservationConfirmation(Reservation reservation) {
        log.info("Sending reservation confirmation to user");
        String messageText = buildReservationMessage(reservation, "🎉 Rezervasyonunuz başarıyla oluşturuldu!");
        sendSimpleMail(reservation.getUser().getEmail(), "🎟️ GÖSTERİM360 Rezervasyon Onayı", messageText);
    }

    @Override
    public void sendPaymentSuccess(Reservation reservation) {
        log.info("Sending payment success notification to user");
        String messageText = buildReservationMessage(reservation, "💳 Ödemeniz başarıyla alınmıştır!\n\n" +
                "Biletiniz onaylandı ve kaydedildi. Lütfen girişte QR kodunuzu göstermek için hazır bulundurun.");
        sendSimpleMail(reservation.getUser().getEmail(), "💳 GÖSTERİM360 Ödeme Onayı", messageText);

        // burda qr kod gönderecez kullanıcıya
    }

    @Override
    public void sendReservationExpired(Reservation reservation) {
        log.info("Sending reservation expired notification to user");
        String messageText = buildReservationMessage(reservation, "⚠ Rezervasyon süreniz dolmuştur. Ödeme tamamlanmadığı için rezervasyon iptal edilmiştir.");
        sendSimpleMail(reservation.getUser().getEmail(), "⚠️ GÖSTERİM360 Rezervasyon İptali", messageText);
    }

    private String buildReservationMessage(Reservation reservation, String header) {
        String userName = reservation.getUser().getFirstName();
        String seatNumbers = reservation.getSeats().stream()
                .map(seat -> String.valueOf(seat.getSeatNumber()))
                .collect(Collectors.joining(", "));

        LocalDate sessionDate = reservation.getSession().getDate();
        LocalDateTime sessionTime = reservation.getSession().getTimes().get(0).getTime();

        String formattedDate = sessionDate.format(DATE_FORMATTER);
        String formattedTime = sessionTime.format(TIME_FORMATTER);
        String sessionDateTime = formattedDate + " " + formattedTime;

        String hallName = reservation.getSession().getSalon().getName();
        String movieTitle = reservation.getSession().getMovie().getName();
        String reservationId = reservation.getId().toString();

        return "Merhaba " + userName + ",\n\n" +
                header + "\n\n" +
                "📽️ Film: " + movieTitle + "\n" +
                "🕒 Seans Tarihi ve Saati: " + sessionDateTime + "\n" +
                "🏢 Salon: " + hallName + "\n" +
                "💺 Koltuklar: " + seatNumbers + "\n" +
                "🔖 Rezervasyon Numarası: " + reservationId + "\n\n";
    }

    private void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text + "GÖSTERİM360 Ekibi");
        javaMailSender.send(message);
    }
}
