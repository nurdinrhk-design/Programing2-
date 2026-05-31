package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Nilai {
    private String nim;
    private String kodeMataKuliah;
    private double nilaiTugas;
    private double nilaiUts;
    private double nilaiUas;
    
    // Properti hasil perhitungan otomatis
    private double nilaiAkhir;
    private String grade;

    public Nilai() {}

    public Nilai(String nim, String kodeMataKuliah, double nilaiTugas, double nilaiUts, double nilaiUas) {
        this.nim = nim;
        this.kodeMataKuliah = kodeMataKuliah;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
        hitungNilai();
    }

    private void hitungNilai() {
        // Bobot: Tugas 30%, UTS 30%, UAS 40%
        this.nilaiAkhir = (this.nilaiTugas * 0.3) + (this.nilaiUts * 0.3) + (this.nilaiUas * 0.4);
        
        // Penentuan Grade
        if (this.nilaiAkhir >= 85) this.grade = "A";
        else if (this.nilaiAkhir >= 75) this.grade = "B";
        else if (this.nilaiAkhir >= 60) this.grade = "C";
        else if (this.nilaiAkhir >= 50) this.grade = "D";
        else this.grade = "E";
    }

    public boolean simpan() throws SQLException {
        String sql = "INSERT INTO tbnilai (nim, kode_matakuliah, nilai_tugas, nilai_uts, nilai_uas, nilai_akhir, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Koneksi koneksiDb = new Koneksi();
        
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, this.nim);
            ps.setString(2, this.kodeMataKuliah);
            ps.setDouble(3, this.nilaiTugas);
            ps.setDouble(4, this.nilaiUts);
            ps.setDouble(5, this.nilaiUas);
            ps.setDouble(6, this.nilaiAkhir);
            ps.setString(7, this.grade);
            
            return ps.executeUpdate() > 0;
        }
    }

    // Fungsi statis untuk mengambil rangkuman laporan (Join Table)
    public static List<String[]> getLaporan() throws SQLException {
        List<String[]> list = new ArrayList<>();
        // Query Join untuk mendapatkan nama Mahasiswa dan nama Mata Kuliah
        String sql = "SELECT n.nim, m.nama, mk.nama_matakuliah, n.nilai_akhir, n.grade " +
                     "FROM tbnilai n " +
                     "JOIN tbmahasiswa m ON n.nim = m.nim " +
                     "JOIN tbmatakuliah mk ON n.kode_matakuliah = mk.kode_matakuliah";
        
        Koneksi koneksiDb = new Koneksi();
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("nama_matakuliah"),
                    String.valueOf(rs.getDouble("nilai_akhir")),
                    rs.getString("grade")
                });
            }
        }
        return list;
    }
}

