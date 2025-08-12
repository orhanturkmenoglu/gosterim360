package com.gosterim360.notification.impl;

import com.gosterim360.model.Reservation;
import com.gosterim360.notification.NotificationService;
import com.gosterim360.util.QRCodeUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

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
        byte[] qrBytes = QRCodeUtil.generateQRCodeBytes(buildTicketContent(reservation));
        String messageText = buildReservationMessage(reservation, "🎉 Rezervasyonunuz başarıyla oluşturuldu!", qrBytes);
        sendHtmlMail(reservation.getUser().getEmail(), "🎟️ GÖSTERİM360 Rezervasyon Onayı", messageText, qrBytes);
    }

    @Override
    public void sendPaymentSuccess(Reservation reservation) {
        log.info("Sending payment success notification to user");
        byte[] qrBytes = QRCodeUtil.generateQRCodeBytes(buildTicketContent(reservation));
        String messageText = buildReservationMessage(reservation, "💳 Ödemeniz başarıyla alınmıştır!\n\n" +
                "Biletiniz onaylandı ve kaydedildi. Lütfen girişte QR kodunuzu göstermek için hazır bulundurun.", qrBytes);
        sendHtmlMail(reservation.getUser().getEmail(), "💳 GÖSTERİM360 Ödeme Onayı", messageText, qrBytes);
    }

    @Override
    public void sendReservationExpired(Reservation reservation) {
        log.info("Sending reservation expired notification to user");
        byte[] qrBytes = QRCodeUtil.generateQRCodeBytes(buildTicketContent(reservation));
        String messageText = buildReservationMessage(reservation, "⚠ Rezervasyon süreniz dolmuştur. Ödeme tamamlanmadığı için rezervasyon iptal edilmiştir.", qrBytes);
        sendHtmlMail(reservation.getUser().getEmail(), "⚠️ GÖSTERİM360 Rezervasyon İptali", messageText, qrBytes);
    }

    private String buildReservationMessage(Reservation reservation, String header, byte[] qrImageBytes) {
        String userName = reservation.getUser().getFirstName();
        int seatNumber = reservation.getSeat().getSeatNumber();
        int rowNumber = reservation.getSeat().getRowNumber();

        LocalDateTime sessionTime = reservation.getSession().getTimes().get(0).getTime();
        String formattedDate = sessionTime.toLocalDate().format(DATE_FORMATTER);
        String formattedTime = sessionTime.toLocalTime().format(TIME_FORMATTER);
        String sessionDateTime = formattedDate + " " + formattedTime;

        String hallName = reservation.getSession().getSalon().getName();
        String movieTitle = reservation.getSession().getMovie().getName();
        String reservationId = reservation.getId().toString();

        // Base64'e çevir ve HTML içinde göster
        String qrBase64 = Base64.getEncoder().encodeToString(qrImageBytes);

        return """
                <div style="font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #eee; border-radius: 8px; background-color: #fafafa;">
                    <h2 style="color: #2c3e50;">Merhaba %s,</h2>
                    <p style="font-size: 16px; line-height: 1.5;">%s</p>
                    <hr style="border: none; border-top: 1px solid #ddd; margin: 20px 0;">
                    <table style="width: 100%%; font-size: 15px; border-collapse: collapse;">
                        <tr>
                            <td style="padding: 8px 0; font-weight: bold;">🎬 Film</td>
                            <td style="padding: 8px 0;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px 0; font-weight: bold;">🕒 Seans</td>
                            <td style="padding: 8px 0;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px 0; font-weight: bold;">🏢 Salon</td>
                            <td style="padding: 8px 0;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px 0; font-weight: bold;">💺 Koltuk</td>
                            <td style="padding: 8px 0;">Sıra %d, Koltuk %d</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px 0; font-weight: bold;">🔖 Rezervasyon ID</td>
                            <td style="padding: 8px 0;">%s</td>
                        </tr>
                    </table>
                    <p style="font-size: 13px; color: #999;">© 2025 GÖSTERİM360. Tüm hakları saklıdır.</p>
                </div>
                """.formatted(userName, header, movieTitle, sessionDateTime, hallName, rowNumber, seatNumber, reservationId, qrBase64);
    }

    private String buildTicketContent(Reservation reservation) {
        return "Film: " + reservation.getSession().getMovie().getName() + "\n"
                + "Salon: " + reservation.getSession().getSalon().getName() + "\n"
                + "Seans: " + reservation.getSession().getDate().format(DATE_FORMATTER)
                + " " + reservation.getSession().getTimes().get(0).getTime().format(TIME_FORMATTER) + "\n"
                + "Koltuk: Sıra " + reservation.getSeat().getRowNumber() + ", Koltuk " + reservation.getSeat().getSeatNumber() + "\n"
                + "Rezervasyon ID: " + reservation.getId();
    }

    private void sendHtmlMail(String to, String subject, String htmlBody, byte[] qrImageBytes) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            helper.addAttachment("qr-code.png", new ByteArrayResource(qrImageBytes));

            javaMailSender.send(message);
            log.info("HTML mail gönderimi başarılı!");
        } catch (MessagingException e) {
            log.error("HTML mail gönderimi başarısız", e);
        }
    }
}
