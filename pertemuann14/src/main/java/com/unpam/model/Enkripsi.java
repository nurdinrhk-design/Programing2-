package com.unpam.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class Enkripsi: Digunakan untuk mengubah teks password menjadi format MD5.
 * Menggunakan teknik modern String.format agar kode lebih ringkas.
 */
public class Enkripsi {
    
    public String hashMD5(String value) {
        if (value == null) return null;
        
        try {
            // Mengambil instance algoritma MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Memproses teks input menjadi array of bytes
            byte[] byteData = md.digest(value.getBytes());
            
            // Mengonversi byte data menjadi format Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : byteData) {
                // %02x artinya: Ubah ke hex, pastikan 2 digit, huruf kecil
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // RuntimeException dilempar jika algoritma MD5 tidak tersedia di sistem
            throw new RuntimeException("Gagal melakukan enkripsi MD5", e);
        }
    }
}