package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mahasiswa {
    private String nim;
    private String nama;
    private int semester;
    private String kelas;
    private String password;

    public Mahasiswa() {}

    public Mahasiswa(String nim, String nama, int semester, String kelas, String password) {
        this.nim = nim;
        this.nama = nama;
        this.semester = semester;
        this.kelas = kelas;
        this.password = password;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean simpan() throws SQLException {
        String sql = "INSERT INTO tbmahasiswa (nim, nama, semester, kelas, password) VALUES (?, ?, ?, ?, ?)";
        Koneksi koneksiDb = new Koneksi();
        
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, this.nim);
            ps.setString(2, this.nama);
            ps.setInt(3, this.semester);
            ps.setString(4, this.kelas);
            ps.setString(5, this.password);
            
            return ps.executeUpdate() > 0;
        }
    }

    public static List<Mahasiswa> getAll() throws SQLException {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM tbmahasiswa";
        Koneksi koneksiDb = new Koneksi();
        
        try (Connection conn = koneksiDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getInt("semester"),
                    rs.getString("kelas"),
                    rs.getString("password")
                ));
            }
        }
        return list;
    }
}

