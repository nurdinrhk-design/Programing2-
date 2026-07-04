# Panduan Menjalankan Projek UAS Reservasi Hotel di NetBeans 21

Projek ini dibuat menggunakan **Spring Boot 3.3.4 (Java 21 / Jakarta EE 10)** dan terstruktur sebagai proyek Maven standar. Berikut adalah langkah-langkah untuk membuka, build, dan menjalankan aplikasi:

---

## 1. Persiapan Database (MySQL XAMPP)
1. Buka **XAMPP Control Panel**.
2. Klik tombol **Start** pada modul **Apache** dan **MySQL**.
3. Buka browser dan pergi ke **http://localhost/phpmyadmin**.
4. Buat database baru dengan nama `hotel_db`.
   * *Catatan:* Anda juga bisa mengimpor file `db_setup.sql` yang ada di folder ini ke phpMyAdmin untuk membuat database dan tabel secara otomatis beserta data dummy awal.

---

## 2. Membuka Projek di NetBeans 21
1. Buka **NetBeans 21**.
2. Klik menu **File** -> **Open Project** (atau tekan `Ctrl + Shift + O`).
3. Cari dan pilih direktori folder projek ini (`E:\UAS_PEMPROGRAMAN_NURDIN`).
4. NetBeans akan otomatis mengenali projek ini sebagai projek **Maven** (ditandai dengan ikon kubus orange-kuning).
5. Tunggu proses loading dependency selesai (NetBeans akan mendownload dependency Spring Boot secara otomatis di latar belakang).

---

## 3. Build & Run Aplikasi
1. Klik kanan pada nama projek (`reservasihotel`) di tab Projects sebelah kiri.
2. Pilih **Clean and Build**. Pastikan proses sukses (`BUILD SUCCESS`).
3. Untuk menjalankan aplikasi:
   * Klik kanan kembali pada projek dan pilih **Run**.
   * Atau cari file `ReservasiApplication.java` di dalam package `com.unpam.reservasihotel`, klik kanan file tersebut, lalu pilih **Run File**.
4. Tunggu konsol log menampilkan pesan:
   `Tomcat started on port 8080 (http) with context path ''` dan `Started ReservasiApplication in ... seconds`

---

## 4. Menguji Aplikasi
1. Buka browser Anda dan akses alamat:
   **http://localhost:8080**
2. Coba fitur-fitur berikut untuk bahan screenshot Laporan UAS Anda:
   * **Halaman Dashboard**: Menampilkan ringkasan status hotel.
   * **Reservasi Baru**: Masukkan data nama tamu, pilih tipe kamar, masukkan tanggal, lalu klik **Pesan Sekarang**.
     * *Kasus Sukses:* Coba isi data yang valid, maka Anda akan dialihkan ke daftar reservasi.
     * *Kasus Gagal (Validasi Tanggal):* Masukkan tanggal check-out sebelum check-in, lalu klik pesan. Layar akan menampilkan alert merah.
     * *Kasus Gagal (Kamar Penuh):* Pesan tipe kamar **Suite** sebanyak 3 kali (karena batas maksimal Suite diatur hanya 2 unit). Pemesanan ketiga akan ditolak dengan alert merah.
   * **Daftar Reservasi**: Melihat daftar tamu hotel yang datanya tersimpan permanen di database MySQL.

---

## 5. Membuat PDF Laporan UAS
1. Buka file `Laporan_UAS_Pemrograman2.md` di folder ini.
2. Anda bisa menyalin isinya ke **Microsoft Word** atau editor teks pilihan Anda.
3. Lengkapi bagian identitas diri Anda (`[Nama Anda]`, `[NIM Anda]`, dll.) di halaman pertama.
4. Tempelkan screenshot hasil pengujian web browser Anda pada bagian-bagian yang ditandai `[TEMPEL SCREENSHOT ...]`.
5. Simpan dokumen sebagai format **PDF** dan unggah ke portal kampus Anda sebelum jam **18.00 WIB**.
6. Unggah source code proyek ini ke repositori Git Anda masing-masing.
