package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "MahasiswaController", urlPatterns = {"/MahasiswaController"})
public class MahasiswaController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String aksi = request.getParameter("aksi");
        String konten = "";
        
        // JIKA TOMBOL SIMPAN DIKLIK
        if ("simpan".equals(aksi)) {
            String nim = request.getParameter("nim");
            String nama = request.getParameter("nama");
            int semester = Integer.parseInt(request.getParameter("semester"));
            String kelas = request.getParameter("kelas");
            String passwordSederhana = request.getParameter("password");
            
            // Enkripsi Password ke MD5 [cite: 23]
            Enkripsi enkripsi = new Enkripsi();
            String passwordTerenkripsi = enkripsi.hashMD5(passwordSederhana);
            
            // Bungkus ke objek model Mahasiswa [cite: 85-87]
            Mahasiswa mhs = new Mahasiswa(nim, nama, semester, kelas, passwordTerenkripsi);
            
            try {
                if (mhs.simpan()) { 
                    konten += "<div style='color: green; font-weight: bold; margin-bottom: 15px;'>✔ Data Mahasiswa berhasil disimpan!</div>";
                } else {
                    konten += "<div style='color: red; font-weight: bold; margin-bottom: 15px;'>❌ Gagal menyimpan data mahasiswa.</div>"; 
                }
            } catch (SQLException e) {
                konten += "<div style='color: red; font-weight: bold; margin-bottom: 15px;'>❌ Error Database: " + e.getMessage() + "</div>"; 
            }
        }
        
        // GENERATE FORM HTML MODERN (Disuntikkan dengan Class CSS Profesional kita)
        konten += """
            <div class="form-container">
                <h3 style="margin-top:0; color:#1e3c72;">Form Master Mahasiswa</h3>
                <form action="MahasiswaController" method="POST">
                    <input type="hidden" name="aksi" value="simpan">
                    
                    <div class="form-group">
                        <label>NIM</label>
                        <input type="text" name="nim" class="form-control" required placeholder="Masukkan NIM">
                    </div>
                    
                    <div class="form-group">
                        <label>Nama Lengkap</label>
                        <input type="text" name="nama" class="form-control" required placeholder="Masukkan Nama">
                    </div>
                    
                    <div class="form-group">
                        <label>Semester</label>
                        <input type="number" name="semester" class="form-control" min="1" max="14" required placeholder="Contoh: 6">
                    </div>
                    
                    <div class="form-group">
                        <label>Kelas</label>
                        <input type="text" name="kelas" class="form-control" required placeholder="Contoh: 06TPLE001">
                    </div>
                    
                    <div class="form-group">
                        <label>Password Akun</label>
                        <input type="password" name="password" class="form-control" required placeholder="Masukkan Password">
                    </div>
                    
                    <button type="submit" class="btn-primary">Simpan Data</button>
                </form>
            </div>
            """;
        
        // Panggil View Master Layout untuk merender halaman [cite: 300, 362]
        MainForm mf = new MainForm();
        mf.tampilkan(konten, request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}