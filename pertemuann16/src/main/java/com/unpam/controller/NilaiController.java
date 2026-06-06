package com.unpam.controller;

import com.unpam.model.Nilai;
import com.unpam.model.NilaiDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        NilaiDAO dao = new NilaiDAO();

        try {
            if (action.equals("Simpan")) {
                Nilai nilai = new Nilai();
                nilai.setNim(request.getParameter("nim"));
                nilai.setKodeMk(request.getParameter("kode_mk"));
                nilai.setSemester(request.getParameter("semester"));
                nilai.setKelas(request.getParameter("kelas"));
                
                String strTugas = request.getParameter("tugas");
                String strUts = request.getParameter("uts");
                String strUas = request.getParameter("uas");
                
                if (strTugas != null && !strTugas.isEmpty()) nilai.setTugas(Double.parseDouble(strTugas));
                if (strUts != null && !strUts.isEmpty()) nilai.setUts(Double.parseDouble(strUts));
                if (strUas != null && !strUas.isEmpty()) nilai.setUas(Double.parseDouble(strUas));

                boolean sukses = dao.simpan(nilai);
                if (sukses) {
                    request.setAttribute("pesan", "Data Nilai Berhasil Disimpan!");
                } else {
                    request.setAttribute("pesan", "Gagal menyimpan data nilai. Pastikan NIM dan Kode MK valid/belum ada.");
                }
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } else if (action.equals("Hapus")) {
                String nim = request.getParameter("nim");
                String kodeMk = request.getParameter("kode_mk");
                
                boolean sukses = dao.hapus(nim, kodeMk);
                if (sukses) {
                    request.setAttribute("pesan", "Data berhasil dihapus!");
                } else {
                    request.setAttribute("pesan", "Data gagal dihapus!");
                }
                request.getRequestDispatcher("RekapController").forward(request, response);
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("pesan", "Terjadi kesalahan sistem: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
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