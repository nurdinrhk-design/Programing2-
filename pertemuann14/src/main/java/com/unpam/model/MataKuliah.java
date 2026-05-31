package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MataKuliah {
    private String kodeMataKuliah;
    private String namaMataKuliah;
    private int jumlahSks;

    public MataKuliah() {}

    public MataKuliah(String kodeMataKuliah, String namaMataKuliah, int jumlahSks) {
        this.kodeMataKuliah = kodeMataKuliah;
        this.namaMataKuliah = namaMataKuliah;
        this.jumlahSks = jumlahSks;
    }

    public String getKodeMataKuliah() { return kodeMataKuliah; }
    public void setKodeMataKuliah(String kodeMataKuliah) { this.kodeMataKuliah = kodeMataKuliah; }

    public String getNamaMataKuliah() { return namaMataKuliah; }
    public void setNamaMataKuliah(String namaMataKuliah) { this.namaMataKuliah = namaMataKuliah; }

    public int getJumlahSks() { return jumlahSks; }
    public void setJumlahSks(int jumlahSks) { this.jumlahSks = jumlahSks; }

    public boolean simpan() throws SQLException {
        String sql = "INSERT INTO tbmatakuliah (kode_matakuliah, nama_matakuliah, jumlah_sks) VALUES (?, ?, ?)";
        Koneksi koneksiDb = new Koneksi();
        
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, this.kodeMataKuliah);
            ps.setString(2, this.namaMataKuliah);
            ps.setInt(3, this.jumlahSks);
            
            return ps.executeUpdate() > 0;
        }
    }

    public static List<MataKuliah> getAll() throws SQLException {
        List<MataKuliah> list = new ArrayList<>();
        String sql = "SELECT * FROM tbmatakuliah";
        Koneksi koneksiDb = new Koneksi();
        
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new MataKuliah(
                    rs.getString("kode_matakuliah"),
                    rs.getString("nama_matakuliah"),
                    rs.getInt("jumlah_sks")
                ));
            }
        }
        return list;
    }
}