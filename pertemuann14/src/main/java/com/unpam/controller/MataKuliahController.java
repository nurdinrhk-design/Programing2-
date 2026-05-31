package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MataKuliahController", urlPatterns = {"/MataKuliahController"})
public class MataKuliahController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String aksi = request.getParameter("aksi");
        String statusMessage = "";
        
        if ("simpan".equals(aksi)) {
            String kode = request.getParameter("kode");
            String nama = request.getParameter("nama");
            int sks = Integer.parseInt(request.getParameter("sks"));
            
            MataKuliah mk = new MataKuliah(kode, nama, sks);
            try {
                if (mk.simpan()) {
                    statusMessage = "<div style='color: #22c55e; margin-bottom: 20px; font-weight: bold;'>✔ Mata Kuliah berhasil disimpan!</div>";
                }
            } catch (SQLException e) {
                statusMessage = "<div style='color: #ef4444; margin-bottom: 20px; font-weight: bold;'>❌ Gagal simpan: " + e.getMessage() + "</div>";
            }
        }
        
        StringBuilder html = new StringBuilder();
        html.append(statusMessage);
        html.append("""
            <div class="form-container">
                <h3 style="color: #1e3c72; margin-top: 0;">Form Master Mata Kuliah</h3>
                <form action="MataKuliahController" method="POST">
                    <input type="hidden" name="aksi" value="simpan">
                    <div class="form-group">
                        <label>Kode Mata Kuliah</label>
                        <input type="text" name="kode" class="form-control" required placeholder="Contoh: MK001">
                    </div>
                    <div class="form-group">
                        <label>Nama Mata Kuliah</label>
                        <input type="text" name="nama" class="form-control" required placeholder="Masukkan Nama MK">
                    </div>
                    <div class="form-group">
                        <label>Jumlah SKS</label>
                        <input type="number" name="sks" class="form-control" min="1" max="6" required>
                    </div>
                    <button type="submit" class="btn-primary">Simpan Mata Kuliah</button>
                </form>
            </div>
            
            <h3 style="color: #1e3c72; margin-top: 40px;">Daftar Mata Kuliah</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Kode MK</th>
                        <th>Nama Mata Kuliah</th>
                        <th>SKS</th>
                    </tr>
                </thead>
                <tbody>
            """);
        
        try {
            List<MataKuliah> list = MataKuliah.getAll();
            if (list.isEmpty()) {
                html.append("<tr><td colspan='3' style='text-align:center;'>Belum ada data mata kuliah.</td></tr>");
            } else {
                for (MataKuliah mk : list) {
                    html.append(String.format("<tr><td>%s</td><td>%s</td><td>%d</td></tr>",
                        mk.getKodeMataKuliah(), mk.getNamaMataKuliah(), mk.getJumlahSks()));
                }
            }
        } catch (SQLException e) {
            html.append("<tr><td colspan='3' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }
        
        html.append("</tbody></table>");
        
        MainForm mf = new MainForm();
        mf.tampilkan(html.toString(), request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}