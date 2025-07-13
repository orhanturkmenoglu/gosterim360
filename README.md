# 🎬 GÖSTERİM360 – Akıllı Sinema Biletleme Sistemi

🚀 GÖSTERİM360, sinema salonları için uçtan uca dijital biletleme deneyimi sunmayı amaçlar. Sistem; kullanıcı dostu arayüz, hızlı işlem süreçleri, güvenli geçiş teknolojileri ve yapay zeka destekli koltuk önerileri ile sektörde modern bir çözüm sunar.

---

## 📌 Amaç & Vizyon

### 🎯 Amaç
- Modern, hızlı ve kullanıcı dostu sinema bileti çözümü sağlamak  
- Akıllı koltuk önerisi ve kişisel bildirimlerle deneyimi iyileştirmek  
- QR kod destekli geçiş ile güvenli, temassız giriş sağlamak
  
---

### 💡 Neden Bu Proje?
- Geleneksel biletleme sistemleri, hem kullanıcı hem de salon yöneticileri açısından sınırlı ve hantal kalmaktadır.  
- GÖSTERİM360, bu boşluğu yapay zekâ, gerçek zamanlı veri işleme ve modern ödeme altyapıları ile kapatmayı hedefler.  
- Girişte QR doğrulama, anlık koltuk doluluk bilgisi ve SMS/email bildirimleri ile kusursuz bir sinema deneyimi sağlar.

---

### 🌍 Vizyon & Katkı Sağlayacağı Alanlar

#### 🔭 Uzun Vadeli Vizyon
GÖSTERİM360, Türkiye’de ve global pazarda sinema salonlarına özel yeni nesil bir dijital altyapı standardı oluşturmayı hedefler. Sistemin ölçeklenebilir yapısı sayesinde farklı sinema zincirlerine ve bağımsız salonlara entegre edilebilir.

#### 📈 Sunacağı Katma Değerler
- **Salon İşletmeleri için:** Kolay seans yönetimi, satış analitiği, doluluk raporları  
- **Kullanıcılar için:** Kolay koltuk seçimi, güvenli ödeme, hızlı giriş  
- **Sektör için:** Dijitalleşmeyi hızlandıran, modüler ve yenilikçi bir çözüm altyapısı

---

### 🛠️ Kullanım Şekli

GÖSTERİM360 kullanıcıları aşağıdaki adımlarla sistemi kullanır:

1. **Film Seçimi:** Kullanıcı, sistemde mevcut olan filmler arasından izlemek istediği filmi seçer.  
2. **Seans Seçimi:** Film için uygun tarih ve saatlerdeki seanslar listelenir, kullanıcı uygun seansı seçer.  
3. **Koltuk Seçimi:** Yapay zeka destekli önerilerle en uygun koltuk kullanıcıya sunulur. Kullanıcı önerilen koltuğu veya dilediği başka bir koltuğu seçebilir.  
4. **Ödeme İşlemi:** Stripe API entegrasyonu sayesinde kullanıcı güvenli ve hızlı bir şekilde ödeme yapar.  
5. **Bilet Gönderimi:** Ödeme tamamlandıktan sonra kullanıcıya QR kodlu bilet, SMS ve e-posta yoluyla iletilir.  
6. **Salon Girişi:** Kullanıcı, salon girişinde QR kodunu okutarak hızlı ve temassız şekilde içeri giriş yapar.  
7. **Canlı Bildirimler:** Sistem, koltuk durumu ve kampanyalar hakkında kullanıcılara anlık bildirimler gönderir.

---


## 🧭 Kullanım Akışı

1. 🎞 Film & seans seçimi  
2. 🪑 AI destekli koltuk önerisi  
3. 💳 Stripe ile ödeme  
4. 📩 QR + SMS/Email bilet gönderimi  
5. 🏷️ Salon girişinde QR okuma ile geçiş  

---

### 🗄️ Veritabanı Seçimi

Projemizde **Oracle Database 19c** kullanılmaktadır.

#### Neden Oracle DB?  
- Kurumsal seviyede güçlü performans ve ölçeklenebilirlik  
- Yüksek veri güvenliği ve sağlamlık  
- Karmaşık ilişkisel veri modellerini destekleme kapasitesi  
- Güvenilirlik ve geniş kurumsal destek  

Bu sayede, sinema salonlarının karmaşık seans, koltuk, rezervasyon ve ödeme verileri tutarlı ve hızlı şekilde yönetilir.

---


## 🔐 Güvenlik

- Admin paneli JWT tabanlı yetkilendirme ile korunur  
- Stripe ödemeleri PCI DSS uyumludur  
- QR kod token'ları tek kullanımlık ve zaman kısıtlıdır  
- HTTPS zorunludur  

---

## ⚙️ DevOps & CI/CD

- Tüm servisler Docker ile containerize edilir  
- CI/CD: GitHub Actions veya Jenkins üzerinden:  
  `Build → Test → Docker Image → Deploy`

---

## ✨ Öne Çıkan Özellikler

✅ AI destekli koltuk önerisi  
✅ QR destekli hızlı giriş  
✅ Stripe ödeme entegrasyonu  
✅ Gerçek zamanlı koltuk güncellemeleri  
✅ Docker + Kafka + Redis ile kurumsal seviye yapı  

---

## 🧱 Teknoloji Yığını

| Katman         | Teknoloji                            | Açıklama                              |
|----------------|--------------------------------------|----------------------------------------|
| Backend        | Java 21, Spring Boot 3.x             | Modüler monolit yapı                  |
| Veritabanı     | Oracle 19c                           | İlişkisel veri yönetimi               |
| Ön Bellek      | Redis                                | Canlı koltuk durumu için              |
| Event Stream   | Apache Kafka                         | Asenkron koltuk bildirimleri          |
| Gerçek Zamanlı | Spring WebSocket                     | Canlı koltuk güncellemeleri           |
| Ödeme          | Stripe API                           | PCI uyumlu ödeme                      |
| Bildirim       | SendGrid (email), Twilio (SMS)       | Bilet ve promosyon bildirimleri       |
| Güvenlik       | Spring Security + JWT                | Rol bazlı kimlik doğrulama            |
| CI/CD          | GitHub Actions / Jenkins             | Otomatik build ve deploy              |
| Container      | Docker                               | Servis konteynerleştirme              |
| Frontend       | React + TailwindCSS                  | Modern ve mobil uyumlu arayüz         |

---

## 📚 Ana Entity İlişkileri

```
💡 Entity’ler ve İlişkiler

Film
 └── 1 - N → Seans

Salon
 └── 1 - N → Seans
 └── 1 - N → Koltuk

Seans
 └── 1 - N → Rezervasyon

Koltuk
 └── 1 - N → Rezervasyon

Rezervasyon
 └── 1 - 1 → Ödeme
 └── 1 - 1 → QrCode

AdminUser
 └── 1 - N → AdminLog


```

---

## 🗺 Kullanım Akış Diyagramı

```
Kullanıcı 
  ↓ 
Film → Seans → Koltuk (AI önerili)  
  ↓ 
Ödeme (Stripe)  
  ↓ 
QR Bilet + Bildirim  
  ↓ 
Salon Giriş QR Okuma  
```

---

## 🔗 API Endpointleri

### 🎟️ Kullanıcı
- `GET /films` → Film listesi  
- `GET /films/{id}/sessions` → Film seans listesi  
- `GET /sessions/{id}/seats` → Seans koltuk durumu  
- `POST /reservations` → Koltuk rezervasyonu (AI önerili)  
- `POST /payments` → Ödeme başlat  
- `GET /payments/{id}/status` → Ödeme durumu  
- `POST /qr/validate` → QR doğrulama  

### 🛠️ Admin (JWT Protected)
- `POST /admin/films` → Yeni film ekle  
- `PUT /admin/films/{id}` → Film güncelle  
- `DELETE /admin/films/{id}` → Film sil  
- `POST /admin/sessions` → Yeni seans ekle  
- `GET /admin/reports` → Raporlar  


## 🧪 Örnek Request / Response

### POST /reservations
```json
{
  "sessionId": 10
}
```
**Response**
```json
{
  "reservationId": 123,
  "suggestedSeatId": 55,
  "status": "PRE_RESERVED"
}
```

### POST /payments
```json
{
  "reservationId": 123,
  "paymentMethodId": "stripe_token_xxx"
}
```
**Response**
```json
{
  "paymentId": 987,
  "status": "PENDING",
  "stripeSessionUrl": "https://checkout.stripe.com/pay/..."
}
```

---

## 🧑‍💻 Katkıda Bulun

Bu projeye katkıda bulunmak için PR gönderin veya [Issue açın](https://github.com/your-repo/gosterim360/issues).

---

## 📄 Lisans

Bu proje MIT lisansı ile lisanslanmıştır. Ayrıntılar için [LICENSE](LICENSE) dosyasına bakınız.

---

## 💬 İletişim

Geliştirici: [Orhan Türkmenoğlu](https://github.com/orhanturkmenoglu)  
Email: orhantrkmn749@gmail.com
