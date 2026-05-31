package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class Koneksi: Menangani hubungan ke server database MySQL.
 * Menggunakan Driver modern (cj) dan parameter timezone agar tidak error.
 */
public class Koneksi {
    // Driver modern untuk MySQL Connector/J 8.0 ke atas
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // URL Database (Pastikan nama DB sesuai: dbaplikasipenilaianmahasiswa)
    private static final String URL = "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    
    // Konfigurasi user default XAMPP
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        try {
            // Mendaftarkan driver ke sistem
            Class.forName(DRIVER);
            
            // Membuka koneksi ke database
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver MySQL tidak ditemukan. Pastikan sudah menambahkannya di pom.xml: " + e.getMessage());
        }
    }
}