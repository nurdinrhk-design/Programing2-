-- SQL Script untuk Setup Database UAS Pemrograman II (Aplikasi Reservasi Hotel)
-- Jalankan script ini di phpMyAdmin atau MySQL Client XAMPP Anda.

-- 1. Membuat Database (jika belum ada)
CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- 2. Membuat Tabel Reservasi
CREATE TABLE IF NOT EXISTS reservasi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama_tamu VARCHAR(100) NOT NULL,
    tipe_kamar VARCHAR(50) NOT NULL,
    tanggal_check_in DATE NOT NULL,
    tanggal_check_out DATE NOT NULL
);

-- 3. Menambahkan Beberapa Data Dummy (Opsional untuk Uji Coba Awal)
INSERT INTO reservasi (nama_tamu, tipe_kamar, tanggal_check_in, tanggal_check_out) VALUES 
('Nurdin', 'Suite', '2026-07-04', '2026-07-06'),
('Ahmad Musyafa', 'Double', '2026-07-05', '2026-07-08'),
('Farizi Ilham', 'Single', '2026-07-04', '2026-07-05');
