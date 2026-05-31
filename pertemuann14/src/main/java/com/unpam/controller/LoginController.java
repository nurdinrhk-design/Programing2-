package com.unpam.controller;

import com.unpam.view.MainForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String aksi = request.getParameter("aksi");
        String konten = "";
        
        if ("proses_login".equals(aksi)) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            // Skenario login sederhana
            if ("admin".equals(user) && "admin123".equals(pass)) {
                HttpSession session = request.getSession(true); 
                session.setAttribute("userName", "Administrator Unpam"); 
                
                // Perbaikan: Link menu sudah diarahkan ke Controller yang tepat
                String menuSesi = """
                    <br><b>Master Data</b><br>
                    <a href="MahasiswaController">Mahasiswa</a><br>
                    <a href="MataKuliahController">Mata Kuliah</a><br><br>
                    <b>Transaksi</b><br>
                    <a href="NilaiController">Nilai</a><br><br>
                    <b>Laporan</b><br>
                    <a href="LaporanController">Nilai</a><br><br>
                    <a href="LogoutController" style="color:red; font-weight:bold;">Logout</a><br><br>
                    """;
                session.setAttribute("menu", menuSesi); 
                
                response.sendRedirect("MainForm");
                return;
            } else {
                konten += "<div style='color: red; font-weight: bold; text-align:center;'>❌ Username atau Password salah!</div>";
            }
        }
        
        // FORM LOGIN MODERN
        konten += """
            <div class="form-container" style="max-width: 350px;">
                <h3 style="margin-top:0; text-align:center; color:#1e3c72;">User Login</h3>
                <form action="LoginController" method="POST">
                    <input type="hidden" name="aksi" value="proses_login">
                    
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" class="form-control" required placeholder="Masukkan username">
                    </div>
                    
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="form-control" required placeholder="Masukkan password">
                    </div>
                    
                    <button type="submit" class="btn-primary" style="width: 100%;">Login</button>
                </form>
                <p style="font-size:12px; text-align:center; color:#7f8c8d; margin-top:15px;">Hint: admin | admin123</p>
            </div>
            """;
        
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