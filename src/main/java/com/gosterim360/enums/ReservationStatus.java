package com.gosterim360.enums;

public enum ReservationStatus {
    PRE_RESERVED,  // Henüz ödeme yapılmadı, koltuk geçici olarak ayrıldı
    PAID,          // Ödeme tamamlandı, rezervasyon kesinleşti
    CANCELLED,     // Kullanıcı tarafından veya sistem tarafından iptal edildi
    EXPIRED        // Ödeme süresi doldu, rezervasyon geçersiz oldu
}