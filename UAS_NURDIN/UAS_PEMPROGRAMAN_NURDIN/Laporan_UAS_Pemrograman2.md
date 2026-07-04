# LAPORAN UJIAN AKHIR SEMESTER (UAS) PEMROGRAMAN II
## APLIKASI RESERVASI HOTEL INTERAKTIF (SPRING BOOT & THYMELEAF)

**Dibuat Oleh:**
* **Nama:** [Nama Anda]
* **NIM:** [NIM Anda]
* **Kelas:** [Kelas Anda]
* **Program Studi:** Teknik Informatika S-1
* **Mata Kuliah:** Pemrograman II
* **Dosen Pengampu:** Niki Ratama, S.Kom., M.Kom / Tim Dosen Pemrograman II
* **Kampus:** Universitas Pamulang (UNPAM)
* **Tahun Akademik:** 2025/2026

---

## 1. PENDAHULUAN & SPESIFIKASI TEKNOLOGI

Aplikasi **Reservasi Hotel** ini dirancang untuk mengelola pemesanan kamar hotel secara interaktif melalui platform web. Proyek ini dibangun menggunakan arsitektur modern Java dengan framework **Spring Boot** dan didesain sesuai spesifikasi **Jakarta EE 10** menggunakan **Java 21**.

### Spesifikasi Teknologi:
1. **Java Development Kit (JDK):** Java 21 (LTS).
2. **Framework:** Spring Boot 3.3.4 (menggunakan spesifikasi servlet Jakarta EE 10 secara default).
3. **Template Engine (View):** Thymeleaf (untuk GUI web interaktif).
4. **Database & Connection:** MySQL (via XAMPP) dan Spring Data JPA (JDBC Wrapper).
5. **Styling & Layout:** Bootstrap 5 (Responsive Layout).
6. **IDE:** NetBeans 21.

---

## 2. SKEMA DATABASE & SETUP MYSQL (XAMPP)

Database menggunakan MySQL yang diaktifkan melalui XAMPP. Tabel `reservasi` menyimpan data id reservasi, nama tamu, tipe kamar, tanggal check-in, dan tanggal check-out.

### Script SQL (`db_setup.sql`):
```sql
-- Membuat Database
CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- Membuat Tabel Reservasi
CREATE TABLE IF NOT EXISTS reservasi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama_tamu VARCHAR(100) NOT NULL,
    tipe_kamar VARCHAR(50) NOT NULL,
    tanggal_check_in DATE NOT NULL,
    tanggal_check_out DATE NOT NULL
);
```

### Penjelasan Database:
* **id**: Bertipe `BIGINT` dengan sifat `AUTO_INCREMENT` sebagai Primary Key.
* **nama_tamu**: Menyimpan nama tamu (`VARCHAR`).
* **tipe_kamar**: Menyimpan pilihan tipe kamar (`Single`, `Double`, atau `Suite`).
* **tanggal_check_in & tanggal_check_out**: Menyimpan tanggal menginap bertipe `DATE`.

---

## 3. STRUKTUR PROYEK (SOURCE CODE JAVA)

Berikut adalah komponen utama kelas-kelas Java (OOP) beserta penjelasannya:

### A. Model: `Reservasi.java`
Kelas ini merepresentasikan Entity Database dan menerapkan konsep OOP **Enkapsulasi** (menggunakan variabel `private` dan menyediakan `getter` & `setter` publik).

```java
package com.unpam.reservasihotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaTamu;
    private String tipeKamar;
    private LocalDate tanggalCheckIn;
    private LocalDate tanggalCheckOut;

    // Konstruktor Default
    public Reservasi() {}

    // Konstruktor Parameter
    public Reservasi(String namaTamu, String tipeKamar, LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.namaTamu = namaTamu;
        this.tipeKamar = tipeKamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
    }

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNamaTamu() { return namaTamu; }
    public void setNamaTamu(String namaTamu) { this.namaTamu = namaTamu; }

    public String getTipeKamar() { return tipeKamar; }
    public void setTipeKamar(String tipeKamar) { this.tipeKamar = tipeKamar; }

    public LocalDate getTanggalCheckIn() { return tanggalCheckIn; }
    public void setTanggalCheckIn(LocalDate tanggalCheckIn) { this.tanggalCheckIn = tanggalCheckIn; }

    public LocalDate getTanggalCheckOut() { return tanggalCheckOut; }
    public void setTanggalCheckOut(LocalDate tanggalCheckOut) { this.tanggalCheckOut = tanggalCheckOut; }
}
```

---

### B. Repository: `ReservasiRepository.java`
Interface ini mewarisi `JpaRepository` dari Spring Data JPA. Digunakan untuk mengeksekusi operasi database CRUD tanpa menulis query SQL manual secara mentah, namun tetap berjalan di atas koneksi JDBC.

```java
package com.unpam.reservasihotel.repository;

import com.unpam.reservasihotel.model.Reservasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {
    long countByTipeKamar(String tipeKamar);
}
```
* `countByTipeKamar`: Kueri otomatis untuk menghitung jumlah kamar terisi pada tipe tertentu untuk penanganan exception kamar penuh.

---

### C. Custom Exception
Digunakan untuk menangani error bisnis sesuai dengan kriteria soal UAS.

#### 1. `KamarPenuhException.java`
```java
package com.unpam.reservasihotel.exception;

public class KamarPenuhException extends RuntimeException {
    public KamarPenuhException(String message) {
        super(message);
    }
}
```

#### 2. `InvalidReservationException.java`
```java
package com.unpam.reservasihotel.exception;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(String message) {
        super(message);
    }
}
```

---

### D. Service Layer: `ReservasiService.java`
Mengandung logika bisnis utama, validasi input, dan exception handling untuk kasus kuota kamar penuh atau tanggal salah.

```java
package com.unpam.reservasihotel.service;

import com.unpam.reservasihotel.exception.InvalidReservationException;
import com.unpam.reservasihotel.exception.KamarPenuhException;
import com.unpam.reservasihotel.model.Reservasi;
import com.unpam.reservasihotel.repository.ReservasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservasiService {

    private final ReservasiRepository reservasiRepository;
    private static final Map<String, Integer> KOTA_KAMAR_LIMITS = new HashMap<>();

    static {
        KOTA_KAMAR_LIMITS.put("Single", 5);
        KOTA_KAMAR_LIMITS.put("Double", 3);
        KOTA_KAMAR_LIMITS.put("Suite", 2);
    }

    @Autowired
    public ReservasiService(ReservasiRepository reservasiRepository) {
        this.reservasiRepository = reservasiRepository;
    }

    public List<Reservasi> getAllReservations() {
        return reservasiRepository.findAll();
    }

    @Transactional
    public Reservasi saveReservation(Reservasi reservasi) {
        // Validasi Tanggal Check-Out tidak boleh sebelum Check-In
        if (reservasi.getTanggalCheckOut().isBefore(reservasi.getTanggalCheckIn())) {
            throw new InvalidReservationException("Tanggal Check-Out tidak boleh mendahului tanggal Check-In!");
        }
        if (reservasi.getTanggalCheckOut().isEqual(reservasi.getTanggalCheckIn())) {
            throw new InvalidReservationException("Reservasi minimal harus 1 malam!");
        }

        // Validasi Kapasitas Kamar (Exception Kamar Penuh)
        String tipe = reservasi.getTipeKamar();
        int maxLimit = KOTA_KAMAR_LIMITS.getOrDefault(tipe, 5);
        long activeReservations = reservasiRepository.countByTipeKamar(tipe);

        if (activeReservations >= maxLimit) {
            throw new KamarPenuhException("Kamar bertipe '" + tipe + "' sudah penuh! Kapasitas maksimal hanya " + maxLimit + " unit.");
        }

        return reservasiRepository.save(reservasi);
    }

    public Map<String, Integer> getRoomLimits() {
        return KOTA_KAMAR_LIMITS;
    }
}
```

---

### E. Controller Layer: `ReservasiController.java`
Menangani pemetaan URL, pengiriman data ke halaman Thymeleaf, dan menangkap exception dari Service layer untuk dikembalikan sebagai pesan error di halaman form.

```java
package com.unpam.reservasihotel.controller;

import com.unpam.reservasihotel.exception.InvalidReservationException;
import com.unpam.reservasihotel.exception.KamarPenuhException;
import com.unpam.reservasihotel.model.Reservasi;
import com.unpam.reservasihotel.service.ReservasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservasiController {

    private final ReservasiService reservasiService;

    @Autowired
    public ReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Aplikasi Reservasi Hotel - Dashboard");
        model.addAttribute("totalReservasi", reservasiService.getAllReservations().size());
        model.addAttribute("limits", reservasiService.getRoomLimits());
        return "index";
    }

    @GetMapping("/reservasi/baru")
    public String showForm(Model model) {
        model.addAttribute("title", "Form Reservasi Baru");
        model.addAttribute("reservasi", new Reservasi());
        return "form";
    }

    @PostMapping("/reservasi/simpan")
    public String saveReservation(@ModelAttribute("reservasi") Reservasi reservasi, Model model) {
        try {
            reservasiService.saveReservation(reservasi);
            return "redirect:/reservasi/daftar?success=true";
        } catch (KamarPenuhException | InvalidReservationException e) {
            model.addAttribute("title", "Form Reservasi Baru");
            model.addAttribute("error", e.getMessage());
            return "form";
        }
    }

    @GetMapping("/reservasi/daftar")
    public String showList(Model model) {
        model.addAttribute("title", "Daftar Reservasi Hotel");
        model.addAttribute("daftarReservasi", reservasiService.getAllReservations());
        return "list";
    }
}
```

---

## 4. DOKUMENTASI & SCREENSHOT PENGUJIAN APLIKASI

*Petunjuk Penguji:* Jalankan aplikasi di Netbeans, buka browser di `http://localhost:8080`.

### A. Halaman Dashboard Utama (`/`)
Menampilkan ringkasan status hotel, total reservasi aktif, dan info ambang batas kapasitas kamar.

> **[TEMPEL SCREENSHOT DASHBOARD DI SINI]**
> *(Jelaskan secara singkat tampilan visual dashboard dengan navigasi menu)*

---

### B. Uji Coba Input Valid (Skenario Sukses)
Menginput nama tamu: **Budi Santoso**, tipe kamar: **Double**, tanggal check-in dan check-out yang valid.

> **[TEMPEL SCREENSHOT FORM INPUT VALID DI SINI]**
> *(Menunjukkan form terisi data yang benar)*

---

### C. Halaman Daftar Reservasi / Output (`/reservasi/daftar`)
Menampilkan tabel daftar reservasi yang mengambil data langsung dari database MySQL.

> **[TEMPEL SCREENSHOT DAFTAR RESERVASI DI SINI]**
> *(Menunjukkan data Budi Santoso berhasil masuk ke tabel)*

---

### D. Uji Coba Exception Handling - Kamar Penuh (Skenario Gagal)
Mencoba memesan kamar bertipe **Suite** lebih dari 2 kali. Sistem akan menolak pemesanan ketiga karena kuota Suite dibatasi 2 unit.

> **[TEMPEL SCREENSHOT ERROR KAMAR PENH DI SINI]**
> *(Menunjukkan banner alert berwarna merah dengan pesan: "Kamar bertipe 'Suite' sudah penuh! Kapasitas maksimal hanya 2 unit.")*

---

### E. Uji Coba Exception Handling - Validasi Tanggal (Skenario Gagal)
Mencoba memesan dengan memasukkan tanggal check-out mendahului tanggal check-in (misal: Check-in 10 Juli, Check-out 8 Juli).

> **[TEMPEL SCREENSHOT ERROR TANGGAL DI SINI]**
> *(Menunjukkan alert merah: "Tanggal Check-Out tidak boleh mendahului tanggal Check-In!")*

---

## 5. KESIMPULAN

Aplikasi **Reservasi Hotel** ini telah berhasil dikembangkan dengan memenuhi seluruh kriteria soal UAS:
1. **Spring Boot & Thymeleaf** sebagai fondasi web yang interaktif.
2. **Koneksi Database MySQL** berjalan lancar melalui wrapper Spring Data JPA.
3. **Desain Berorientasi Objek (OOP)** terimplementasi dengan baik pada Model kelas `Reservasi`.
4. **Layout Management** menggunakan Bootstrap 5 berjalan responsif.
5. **Exception Handling** berhasil mengamankan sistem dari kapasitas kamar berlebih (*over-occupancy*) dan data input tanggal yang tidak valid.

Proyek ini siap dibuka di NetBeans 21, dicompile (`Clean and Build`), dan dijalankan di port 8080 laptop Anda.
