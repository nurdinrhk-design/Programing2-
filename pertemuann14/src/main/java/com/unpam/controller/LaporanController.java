package com.unpam.controller;

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

@WebServlet(name = "LaporanController", urlPatterns = {"/LaporanController"})
public class LaporanController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        StringBuilder html = new StringBuilder();
        
        // HEADER LAPORAN & BUKA TABEL
        html.append("""
            <div class="form-container" style="max-width: 100%;">
                <h3 style="color: #1e3c72; margin-top: 0; margin-bottom: 5px;">Laporan Nilai Akademik Mahasiswa</h3>
                <p style="color: #64748b; margin-bottom: 25px; font-size: 14px;">Menampilkan rangkuman nilai akhir dan grade mahasiswa terintegrasi.</p>
                
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>NIM</th>
                            <th>Nama Mahasiswa</th>
                            <th>Mata Kuliah</th>
                            <th>Nilai Akhir</th>
                            <th>Grade</th>
                        </tr>
                    </thead>
                    <tbody>
            """);
        
        // AMBIL DATA DARI MODEL (FUNGSI JOIN TABLE)
        try {
            List<String[]> listLaporan = Nilai.getLaporan();
            
            if (listLaporan.isEmpty()) {
                html.append("<tr><td colspan='5' style='text-align:center; padding: 20px;'>Belum ada transaksi nilai akademik yang di-input.</td></tr>");
            } else {
                for (String[] row : listLaporan) {
                    html.append(String.format("""
                        <tr>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td style='font-weight: bold; color: #2563eb;'>%s</td>
                            <td><span style='background: #f1f5f9; padding: 4px 10px; border-radius: 4px; font-weight: bold; border: 1px solid #cbd5e1;'>%s</span></td>
                        </tr>
                        """, row[0], row[1], row[2], row[3], row[4]));
                }
            }
        } catch (SQLException e) {
            html.append("<tr><td colspan='5' style='color:red; text-align:center;'>Gagal memuat laporan: ").append(e.getMessage()).append("</td></tr>");
        }
        
        // TUTUP TABEL
        html.append("""
                    </tbody>
                </table>
            </div>
            """);
        
        // KIRIM KE MAINFORM UNTUK DI-RENDER
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