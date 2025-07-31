package com.gosterim360.notification;

import com.gosterim360.model.Reservation;

public interface NotificationService {

    /*
        sendReservationConfirmation: PRE_RESERVED durumunda tetiklenir.

        sendPaymentSuccess: Ödeme sonrası.

        sendReservationExpired: Timeout ile iptal edilen rezervasyon için.
     */
    void sendReservationConfirmation(Reservation reservation);

    void sendPaymentSuccess(Reservation reservation);

    void sendReservationExpired(Reservation reservation);
}
