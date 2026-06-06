package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NilaiDAO {

    public Mahasiswa cariMahasiswa(String nim) {
        Mahasiswa mhs = null;
        String query = "SELECT * FROM mahasiswa WHERE nim = ?";
        
        Connection conn = Koneksi.getKoneksi();
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mhs = new Mahasiswa();
                    mhs.setNim(rs.getString("nim"));
                    mhs.setNama(rs.getString("nama"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error cariMahasiswa: " + e.getMessage());
        }
        return mhs;
    }

    public MataKuliah cariMataKuliah(String kodeMk) {
        MataKuliah mk = null;
        String query = "SELECT * FROM mata_kuliah WHERE kode_mk = ?";
        
        Connection conn = Koneksi.getKoneksi();
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, kodeMk);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mk = new MataKuliah();
                    mk.setKodeMk(rs.getString("kode_mk"));
                    mk.setNamaMk(rs.getString("nama_mk"));
                    mk.setSks(rs.getInt("sks"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error cariMataKuliah: " + e.getMessage());
        }
        return mk;
    }

    public boolean simpan(Nilai nilai) {
        String query = "INSERT INTO nilai (nim, kode_mk, semester, kelas, tugas, uts, uas) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getKoneksi();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nilai.getNim());
            ps.setString(2, nilai.getKodeMk());
            ps.setString(3, nilai.getSemester());
            ps.setString(4, nilai.getKelas());
            ps.setDouble(5, nilai.getTugas());
            ps.setDouble(6, nilai.getUts());
            ps.setDouble(7, nilai.getUas());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error simpan nilai: " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(String nim, String kodeMk) {
        String query = "DELETE FROM nilai WHERE nim = ? AND kode_mk = ?";
        Connection conn = Koneksi.getKoneksi();
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nim);
            ps.setString(2, kodeMk);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error hapus nilai: " + e.getMessage());
            return false;
        }
    }

    public List<Nilai> tampilSemua() {
        List<Nilai> listNilai = new ArrayList<>();
        String query = "SELECT n.id_nilai, n.nim, m.nama, n.semester, n.kelas, n.kode_mk, mk.nama_mk, " +
                       "n.tugas, n.uts, n.uas, n.nilai_akhir, n.huruf, n.status " +
                       "FROM nilai n " +
                       "JOIN mahasiswa m ON n.nim = m.nim " +
                       "JOIN mata_kuliah mk ON n.kode_mk = mk.kode_mk " +
                       "ORDER BY n.id_nilai DESC";
                       
        Connection conn = Koneksi.getKoneksi();
        if (conn == null) return listNilai;

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                Nilai n = new Nilai();
                n.setIdNilai(rs.getInt("id_nilai"));
                n.setNim(rs.getString("nim"));
                n.setKodeMk(rs.getString("kode_mk"));
                n.setSemester(rs.getString("semester"));
                n.setKelas(rs.getString("kelas"));
                n.setTugas(rs.getDouble("tugas"));
                n.setUts(rs.getDouble("uts"));
                n.setUas(rs.getDouble("uas"));
                n.setNilaiAkhir(rs.getDouble("nilai_akhir"));
                n.setHuruf(rs.getString("huruf"));
                n.setStatus(rs.getString("status"));
                
                n.getMahasiswa().setNama(rs.getString("nama"));
                n.getMataKuliah().setNamaMk(rs.getString("nama_mk"));
                
                listNilai.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Error tampilSemua: " + e.getMessage());
        }
        return listNilai;
    }
}