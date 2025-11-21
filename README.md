# Market Satış Uygulaması 🛒

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0.0-green?style=flat-square)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey?style=flat-square)

Modern ve kullanıcı dostu bir market satış yönetim sistemi. Java 21 ve Swing GUI kullanılarak geliştirilmiştir.

## 📋 Özellikler

- ✅ **Ürün Yönetimi**: Ürün ekleme, silme, güncelleme
- 🛒 **Sepet Sistemi**: Sepete ürün ekleme/çıkarma
- 💰 **Fiyat Hesaplama**: Otomatik toplam hesaplama
- 🔍 **Arama ve Filtreleme**: Ürün arama ve kategoriye göre filtreleme
- 📊 **Stok Takibi**: Gerçek zamanlı stok yönetimi
- 💳 **Satış İşlemleri**: Hızlı ve güvenli satış tamamlama
- 🎨 **Modern Arayüz**: Temiz ve kullanıcı dostu GUI

## 🚀 Teknolojiler

- **Java 21**: Son Java özelliklerinden yararlanma
- **Swing**: GUI geliştirme
- **Java Collections Framework**: Veri yönetimi
- **Stream API**: Modern veri işleme

## 📂 Proje Yapısı

```
MarketSatisUygulamasi/
├── src/
│   ├── model/
│   │   ├── Product.java          # Ürün modeli
│   │   ├── CartItem.java         # Sepet ögesi
│   │   └── ShoppingCart.java     # Sepet yönetimi
│   ├── service/
│   │   └── MarketService.java    # İş mantığı
│   └── ui/
│       └── MarketGUI.java         # Kullanıcı arayüzü
├── .gitignore
└── README.md
```

## 🛠️ Kurulum ve Çalıştırma

### Gereksinimler
- Java 21 veya üzeri
- JDK kurulu olmalı

### Derleme ve Çalıştırma

#### Windows:
```cmd
run.bat
```

#### Linux/macOS:
```bash
chmod +x run.sh
./run.sh
```

#### Manuel Derleme:
```bash
# Projeyi derle
javac -encoding UTF-8 -d bin src/model/*.java src/service/*.java src/ui/*.java

# Uygulamayı çalıştır
java -cp bin ui.MarketGUI
```

#### IDE Kullanarak (IntelliJ IDEA, Eclipse, VS Code):
1. Projeyi IDE'ye import edin
2. `MarketGUI.java` dosyasını açın
3. `main` metodunu çalıştırın

## 💡 Kullanım

### Ürün Ekleme
1. Sağ paneldeki "Ürün Yönetimi" bölümünden ürün bilgilerini girin
2. "Yeni Ürün Ekle" butonuna tıklayın

### Ürün Güncelleme
1. Sol panelden güncellenecek ürünü seçin
2. Sağ panelde bilgileri düzenleyin
3. "Seçili Ürünü Güncelle" butonuna tıklayın

### Ürün Silme
1. Sol panelden silinecek ürünü seçin
2. "Seçili Ürünü Sil" butonuna tıklayın
3. Onaylayın

### Satış Yapma
1. Sol panelden ürünü seçin
2. "Sepete Ekle" butonuna tıklayın
3. Adet girin
4. Sepet dolduğunda "Satışı Tamamla" butonuna tıklayın

### Arama ve Filtreleme
- Arama kutusuna ürün adı veya kategori yazın
- "Ara" butonuna tıklayın veya Enter'a basın
- Kategori seçerek filtreleme yapın

## 🎯 Örnek Ürünler

Uygulama başlatıldığında aşağıdaki örnek ürünler otomatik yüklenir:
- Ekmek (5 TL)
- Süt (25 TL)
- Yumurta (45 TL)
- Peynir (150 TL)
- Domates (35 TL)
- Salatalık (20 TL)
- Elma (40 TL)
- Portakal (30 TL)

## 🎨 Arayüz Özellikleri

Uygulama modern ve kullanıcı dostu bir arayüze sahiptir:
- **Üst Panel**: Uygulama başlığı ve logo
- **Sol Panel**: Ürün listesi tablosu, arama ve kategori filtreleme
- **Sağ Panel**: Ürün ekleme/düzenleme/silme formu
- **Alt Panel**: Alışveriş sepeti ve toplam tutar gösterimi

> 💡 **Not**: Ekran görüntülerini görmek için uygulamayı çalıştırın veya `screenshots/` klasörüne bakın.

## 🤝 Katkıda Bulunma

Katkılarınızı bekliyoruz! Detaylı bilgi için [CONTRIBUTING.md](CONTRIBUTING.md) dosyasına bakın.

1. Bu repo'yu fork edin
2. Feature branch oluşturun (`git checkout -b feature/yeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/yeniOzellik`)
5. Pull Request oluşturun

## 📝 Lisans

Bu proje [MIT Lisansı](LICENSE) altında lisanslanmıştır. Detaylar için LICENSE dosyasına bakın.

## 👨‍💻 Geliştirici

Proje, modern Java teknolojileri kullanılarak geliştirilmiştir.

## 📧 İletişim

Sorularınız veya önerileriniz için issue açabilirsiniz.

---

⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!
