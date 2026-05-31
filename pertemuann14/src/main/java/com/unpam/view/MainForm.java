package com.unpam.view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    public void tampilkan(String konten, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        
        // 1. Definisikan Menu Navigasi Samping (Sidebar)
        String menu = """
            <br><b>Master Data</b>
            <a href="MahasiswaController">Mahasiswa</a>
            <a href="MataKuliahController">Mata Kuliah</a>
            
            <b>Transaksi</b>
            <a href="NilaiController">Nilai</a>
            
            <b>Laporan</b>
            <a href="LaporanController">Nilai</a>
            
            <a href="LoginController" style="color:#ef4444; font-weight:bold; margin-top:20px;">Login</a>
            """;
        
        // 2. Definisikan Menu Navigasi Atas (Top Menu)
        String topMenu = """
            <nav>
                <ul>
                    <li><a href="MainForm">Home</a></li>
                    <li><a href="MahasiswaController">Mahasiswa</a></li>
                    <li><a href="MataKuliahController">Mata Kuliah</a></li>
                    <li><a href="NilaiController">Transaksi</a></li>
                    <li><a href="LaporanController">Laporan</a></li>
                </ul>
            </nav>
            """;
        
        // 3. Logika Session & Nama User
        String userName = "";
        if (!session.isNew()) {
            try {
                if (session.getAttribute("userName") != null) {
                    userName = session.getAttribute("userName").toString();
                }
            } catch (Exception ex) {}
            
            if (userName != null && !userName.trim().isEmpty()) {
                // Tampilan default jika konten kosong
                if (konten == null || konten.trim().isEmpty()) {
                    konten = "<div style='padding:40px; text-align:center;'>"
                           + "<h1 style='font-size:48px; color:#1e293b; margin-bottom:10px;'>Selamat Datang</h1>"
                           + "<h2 style='font-weight:400; color:#64748b;'>Halo, " + userName + "</h2>"
                           + "<p style='margin-top:20px; color:#94a3b8;'>Gunakan menu di samping untuk mengelola data akademik.</p>"
                           + "</div>";
                }
                
                // Ambil menu spesifik session jika ada
                try {
                    if (session.getAttribute("menu") != null) {
                        menu = session.getAttribute("menu").toString();
                    }
                } catch (Exception ex) {}
            }
        }
        
        // 4. Render Keseluruhan HTML
        try (PrintWriter out = response.getWriter()) {
            out.println("""
                <!DOCTYPE html>
                <html lang="id">
                <head>
                    <meta charset="UTF-8">
                    <link href="style.css" rel="stylesheet" type="text/css" />
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
                    <title>SIAKAD - Universitas Pamulang</title>
                </head>
                <body>
                <center>
                    <table cellspacing="0" cellpadding="0" border="0">
                        <tr>
                            <td colspan="2" bgcolor="#dddddd">
                                <h2>Sistem Informasi Akademik</h2>
                                <h1>UNIVERSITAS PAMULANG</h1>
                                <h4>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</h4>
                            </td>
                        </tr>
                        
                        <tr>
                            <td valign="top" bgcolor="#eeffee">
                                <div id="menu">
                                    """ + menu + """
                                </div>
                            </td>
                            
                            <td valign="top" bgcolor="#ffffff">
                                """ + topMenu + """
                                <div id="konten_utama">
                                    """ + konten + """
                                </div>
                            </td>
                        </tr>
                        
                        <tr>
                            <td colspan="2" align="center" bgcolor="#eeeeff">
                                <small>
                                    <strong>SIAKAD UNPAM</strong> &bull; Developed with Jakarta EE 10 MVC Architecture<br>
                                    Copyright &copy; 2026 Universitas Pamulang. All rights reserved.
                                </small>
                            </td>
                        </tr>
                    </table>
                </center>
                </body>
                </html>
                """);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilkan("", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilkan("", request, response);
    }
}