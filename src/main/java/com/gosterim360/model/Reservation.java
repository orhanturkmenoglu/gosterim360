package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import com.gosterim360.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity<UUID> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // user_id eklenecek.
    // yani hangi kullanıcının rezerve ettiği görülecek.
    // ticket alanı olacak bilet

    // 🎟️ Ticket
    //id
    //
    //seat_reservation_id
    //
    //qr_code
    //
    //sent_via (SMS, Email)
    //
    //status (DELIVERED, FAILED)

    /*
    Koltuk Seçimi ve Rezerve Etme (10 dk süreli)

    Stripe ile Ödeme

    Bilet Teslimi (Email + SMS + QR)

    QR ile Temassız Giriş

    Canlı Bildirimler (Örn. kampanya, doluluk uyarısı)


     */
}