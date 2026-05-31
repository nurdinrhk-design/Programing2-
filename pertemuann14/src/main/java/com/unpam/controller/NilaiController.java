package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.model.MataKuliah;
import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String aksi = request.getParameter("aksi");
        String statusMessage = "";
        
        // LOGIKA SIMPAN DATA TRANSAKSI
        if ("simpan".equals(aksi)) {
            String nim = request.getParameter("nim");
            String kodeMk = request.getParameter("kodeMk");
            double tugas = Double.parseDouble(request.getParameter("tugas"));
            double uts = Double.parseDouble(request.getParameter("uts"));
            double uas = Double.parseDouble(request.getParameter("uas"));
            
            Nilai n = new Nilai(nim, kodeMk, tugas, uts, uas);
            try {
                if (n.simpan()) {
                    statusMessage = "<div style='color: #22c55e; margin-bottom: 20px; font-weight: bold;'>✔ Nilai Akademik berhasil disimpan!</div>";
                }
            } catch (SQLException e) {
                statusMessage = "<div style='color: #ef4444; margin-bottom: 20px; font-weight: bold;'>❌ Gagal simpan nilai: " + e.getMessage() + "</div>";
            }
        }
        
        StringBuilder html = new StringBuilder();
        html.append(statusMessage);
        
        // HEADER FORM
        html.append("""
            <div class="form-container">
                <h3 style="color: #1e3c72; margin-top: 0;">Input Transaksi Nilai</h3>
                <form action="NilaiController" method="POST">
                    <input type="hidden" name="aksi" value="simpan">
                    
                    <div class="form-group">
                        <label>Pilih Mahasiswa</label>
                        <select name="nim" class="form-control" required>
                            <option value="">-- Pilih Mahasiswa --</option>
            """);
        
        // DROPDOWN MAHASISWA
        try {
            List<Mahasiswa> listMhs = Mahasiswa.getAll();
            for (Mahasiswa m : listMhs) {
                html.append(String.format("<option value='%s'>%s - %s</option>", m.getNim(), m.getNim(), m.getNama()));
            }
        } catch (SQLException e) {
            html.append("<option value=''>Gagal memuat data mahasiswa</option>");
        }
        
        html.append("""
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Pilih Mata Kuliah</label>
                        <select name="kodeMk" class="form-control" required>
                            <option value="">-- Pilih Mata Kuliah --</option>
            """);
        
        // DROPDOWN MATA KULIAH
        try {
            List<MataKuliah> listMk = MataKuliah.getAll();
            for (MataKuliah mk : listMk) {
                html.append(String.format("<option value='%s'>%s - %s</option>", mk.getKodeMataKuliah(), mk.getKodeMataKuliah(), mk.getNamaMataKuliah()));
            }
        } catch (SQLException e) {
            html.append("<option value=''>Gagal memuat data mata kuliah</option>");
        }
        
        // SISA FORM (Tugas, UTS, UAS)
        html.append("""
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Nilai Tugas (30%)</label>
                        <input type="number" name="tugas" class="form-control" min="0" max="100" step="0.01" required>
                    </div>
                    <div class="form-group">
                        <label>Nilai UTS (30%)</label>
                        <input type="number" name="uts" class="form-control" min="0" max="100" step="0.01" required>
                    </div>
                    <div class="form-group">
                        <label>Nilai UAS (40%)</label>
                        <input type="number" name="uas" class="form-control" min="0" max="100" step="0.01" required>
                    </div>
                    <button type="submit" class="btn-primary">Submit Nilai</button>
                </form>
            </div>
            """);
        
        // Kirim ke MainForm
        MainForm mf = new MainForm();
        mf.tampilkan(html.toString(), request, response);
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