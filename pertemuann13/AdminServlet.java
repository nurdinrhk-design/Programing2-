package controller;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet", "/LoginController", "/LogoutController"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userPath = request.getServletPath();
        String action = request.getParameter("action");
        
        // Inisialisasi Data Master & Transaksi (Data Dummy Awal)
        if (session.getAttribute("daftarMhs") == null) {
            ArrayList<String[]> awalMhs = new ArrayList<>();
            awalMhs.add(new String[]{"201011400001", "Fikri Hidayat", "Teknik Informatika"});
            session.setAttribute("daftarMhs", awalMhs);
        }
        if (session.getAttribute("daftarMK") == null) {
            ArrayList<String[]> awalMK = new ArrayList<>();
            awalMK.add(new String[]{"INF201", "Pemrograman 2", "3"});
            session.setAttribute("daftarMK", awalMK);
        }
        if (session.getAttribute("daftarNilai") == null) {
            ArrayList<String[]> awalNilai = new ArrayList<>();
            awalNilai.add(new String[]{"201011400001", "Fikri Hidayat", "INF201", "Pemrograman 2", "85", "A"});
            session.setAttribute("daftarNilai", awalNilai);
        }

        // ==========================================================================
        // A. MANAJEMEN AUTENTIKASI
        // ==========================================================================
        if (userPath.equals("/LoginController")) {
            if (action == null) {
                session.setAttribute("konten", buildFormLogin());
            } 
            else if (action.equals("doLogin")) {
                String u = request.getParameter("txtUser");
                String p = request.getParameter("txtPass");
                if (u.equals("admin") && p.equals("admin123")) {
                    session.setAttribute("userName", "Administrator");
                    session.setAttribute("menu", buildMenuSetelahLogin());
                    session.setAttribute("konten", "<br><h1>Dashboard Utama</h1><p>Selamat datang kembali! Sistem siap digunakan.</p>");
                } else {
                    session.setAttribute("konten", "<div style='color:red; margin-bottom:10px;'>Login Gagal! Username/Password salah.</div>" + buildFormLogin());
                }
            }
            response.sendRedirect("index.jsp");
            return;
        }

        if (userPath.equals("/LogoutController")) {
            session.invalidate();
            response.sendRedirect("index.jsp");
            return;
        }

        // ==========================================================================
        // B. MANAJEMEN DATA & ROUTING HALAMAN
        // ==========================================================================
        if (userPath.equals("/AdminServlet")) {
            String page = request.getParameter("page");
            ArrayList<String[]> daftarMhs = (ArrayList<String[]>) session.getAttribute("daftarMhs");
            ArrayList<String[]> daftarMK = (ArrayList<String[]>) session.getAttribute("daftarMK");
            ArrayList<String[]> daftarNilai = (ArrayList<String[]>) session.getAttribute("daftarNilai");

            // PROSES AKSI SIMPAN (POST)
            if (action != null) {
                if (action.equals("simpanMahasiswa")) {
                    daftarMhs.add(new String[]{request.getParameter("nim"), request.getParameter("nama"), request.getParameter("jurusan")});
                    session.setAttribute("konten", buildHalamanMahasiswa(daftarMhs));
                } 
                else if (action.equals("simpanMataKuliah")) {
                    daftarMK.add(new String[]{request.getParameter("kodeMK"), request.getParameter("namaMK"), request.getParameter("sks")});
                    session.setAttribute("konten", buildHalamanMataKuliah(daftarMK));
                } 
                else if (action.equals("simpanNilai")) {
                    String nim = request.getParameter("nimPilihan");
                    String mk = request.getParameter("mkPilihan");
                    int n = Integer.parseInt(request.getParameter("nilaiAngka"));
                    String grade = (n >= 80) ? "A" : (n >= 70) ? "B" : (n >= 60) ? "C" : (n >= 50) ? "D" : "E";
                    
                    String nmMhs = "", nmMK = "";
                    for(String[] s : daftarMhs) if(s[0].equals(nim)) nmMhs = s[1];
                    for(String[] m : daftarMK) if(m[0].equals(mk)) nmMK = m[1];
                    
                    daftarNilai.add(new String[]{nim, nmMhs, mk, nmMK, String.valueOf(n), grade});
                    session.setAttribute("konten", buildHalamanNilai(daftarNilai, daftarMhs, daftarMK));
                }
                response.sendRedirect("index.jsp");
                return;
            }

            // NAVIGASI HALAMAN (GET)
            if (page != null) {
                if (page.equals("mahasiswa")) session.setAttribute("konten", buildHalamanMahasiswa(daftarMhs));
                else if (page.equals("matakuliah")) session.setAttribute("konten", buildHalamanMataKuliah(daftarMK));
                else if (page.equals("nilai")) session.setAttribute("konten", buildHalamanNilai(daftarNilai, daftarMhs, daftarMK));
                else if (page.equals("laporannilai")) {
                    String filterNIM = request.getParameter("filterNIM");
                    session.setAttribute("konten", buildHalamanLaporan(daftarNilai, daftarMhs, filterNIM));
                }
            }
        }
        response.sendRedirect("index.jsp");
    }

    // --- HELPER METHODS UNTUK INTERFACE UI ---

    private String buildFormLogin() {
        return "<h2>Sign In System</h2>"
             + "<div style='max-width: 350px; margin: 30px auto; background: #f8f9fa; padding: 30px; border-radius: 8px; border: 1px solid #e1e4e8; box-shadow: 0 4px 12px rgba(0,0,0,0.05);'>"
             + "  <form action='LoginController?action=doLogin' method='POST'>"
             + "    <div style='margin-bottom: 15px; text-align: left;'>"
             + "      <label style='display: block; margin-bottom: 5px; font-weight: 500; color: #555;'>Username</label>"
             + "      <input type='text' name='txtUser' style='width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;' required>"
             + "    </div>"
             + "    <div style='margin-bottom: 20px; text-align: left;'>"
             + "      <label style='display: block; margin-bottom: 5px; font-weight: 500; color: #555;'>Password</label>"
             + "      <input type='password' name='txtPass' style='width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;' required>"
             + "    </div>"
             + "    <button type='submit' style='width: 100%; background: linear-gradient(135deg, #2b5876 0%, #4e4376 100%); color: white; border: none; padding: 12px; border-radius: 4px; font-weight: bold; cursor: pointer;'>Masuk Aplikasi</button>"
             + "  </form>"
             + "</div>";
    }

    private String buildMenuSetelahLogin() {
        return "<br><b>Master Data</b><br><a href='AdminServlet?page=mahasiswa'>Mahasiswa</a><br><a href='AdminServlet?page=matakuliah'>Mata Kuliah</a><br><br>"
             + "<b>Transaksi</b><br><a href='AdminServlet?page=nilai'>Nilai</a><br><br>"
             + "<b>Laporan</b><br><a href='AdminServlet?page=laporannilai'>Nilai</a><br><br>"
             + "<a href='LogoutController'>Logout</a><br><br>";
    }

    private String buildHalamanMahasiswa(ArrayList<String[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Kelola Data Mahasiswa</h2>")
          .append("<form action='AdminServlet?action=simpanMahasiswa' method='POST' style='margin-bottom: 25px; background: #f8f9fa; padding: 20px; border-radius: 8px; border: 1px solid #e1e4e8;'>")
          .append("  <table cellpadding='8' style='box-shadow: none; border: none; background: transparent;'>")
          .append("    <tr><td width='120'>NIM</td><td>: <input type='text' name='nim' style='padding: 8px; width: 220px; border: 1px solid #ccc; border-radius: 4px;' required></td></tr>")
          .append("    <tr><td>Nama Lengkap</td><td>: <input type='text' name='nama' style='padding: 8px; width: 320px; border: 1px solid #ccc; border-radius: 4px;' required></td></tr>")
          .append("    <tr><td>Jurusan</td><td>: ")
          .append("        <select name='jurusan' style='padding: 8px; width: 220px; border: 1px solid #ccc; border-radius: 4px;'>")
          .append("          <option value='Teknik Informatika'>Teknik Informatika</option>")
          .append("          <option value='Sistem Informasi'>Sistem Informasi</option>")
          .append("          <option value='Manajemen Informatika'>Manajemen Informatika</option>")
          .append("        </select></td></tr>")
          .append("    <tr><td></td><td><button type='submit' style='background: #2b5876; color: white; border: none; padding: 10px 24px; border-radius: 4px; cursor: pointer; font-weight: 500;'>Simpan Data</button></td></tr>")
          .append("  </table>")
          .append("</form>")
          .append("<h3>Daftar Mahasiswa Aktif</h3>")
          .append("<table border='1' width='100%' cellpadding='10' style='border-collapse: collapse; text-align: left; border: 1px solid #e1e4e8; background: white;'>")
          .append("  <tr bgcolor='#eef2f5' style='color: #2b5876;'><th>NIM</th><th>Nama Lengkap</th><th>Jurusan</th></tr>");
        for(String[] s : list) {
            sb.append("<tr><td>").append(s[0]).append("</td><td>").append(s[1]).append("</td><td>").append(s.length > 2 ? s[2] : "Teknik Informatika").append("</td></tr>");
        }
        return sb.append("</table>").toString();
    }

    private String buildHalamanMataKuliah(ArrayList<String[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Kelola Data Mata Kuliah</h2>")
          .append("<form action='AdminServlet?action=simpanMataKuliah' method='POST' style='margin-bottom: 25px; background: #f8f9fa; padding: 20px; border-radius: 8px; border: 1px solid #e1e4e8;'>")
          .append("  <table cellpadding='8' style='box-shadow: none; border: none; background: transparent;'>")
          .append("    <tr><td width='120'>Kode MK</td><td>: <input type='text' name='kodeMK' style='padding: 8px; width: 150px; border: 1px solid #ccc; border-radius: 4px;' placeholder='Contoh: INF201' required></td></tr>")
          .append("    <tr><td>Nama Mata Kuliah</td><td>: <input type='text' name='namaMK' style='padding: 8px; width: 320px; border: 1px solid #ccc; border-radius: 4px;' required></td></tr>")
          .append("    <tr><td>Bobot SKS</td><td>: ")
          .append("        <select name='sks' style='padding: 8px; width: 120px; border: 1px solid #ccc; border-radius: 4px;'>")
          .append("          <option value='1'>1 SKS</option><option value='2'>2 SKS</option>")
          .append("          <option value='3' selected>3 SKS</option><option value='4'>4 SKS</option>")
          .append("        </select></td></tr>")
          .append("    <tr><td></td><td><button type='submit' style='background: #2b5876; color: white; border: none; padding: 10px 24px; border-radius: 4px; cursor: pointer; font-weight: 500;'>Simpan Mata Kuliah</button></td></tr>")
          .append("  </table>")
          .append("</form>")
          .append("<h3>Daftar Kurikulum Mata Kuliah</h3>")
          .append("<table border='1' width='100%' cellpadding='10' style='border-collapse: collapse; text-align: left; border: 1px solid #e1e4e8; background: white;'>")
          .append("  <tr bgcolor='#eef2f5' style='color: #2b5876;'><th>Kode MK</th><th>Nama Mata Kuliah</th><th>Bobot SKS</th></tr>");
        for(String[] s : list) {
            sb.append("<tr><td>").append(s[0]).append("</td><td>").append(s[1]).append("</td><td>").append(s[2]).append(" SKS</td></tr>");
        }
        return sb.append("</table>").toString();
    }

    private String buildHalamanNilai(ArrayList<String[]> list, ArrayList<String[]> mhs, ArrayList<String[]> mk) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Input Transaksi Nilai Ujian</h2>")
          .append("<form action='AdminServlet?action=simpanNilai' method='POST' style='margin-bottom: 25px; background: #f8f9fa; padding: 20px; border-radius: 8px; border: 1px solid #e1e4e8;'>")
          .append("  <table cellpadding='8' style='box-shadow: none; border: none; background: transparent;'>")
          .append("    <tr><td width='120'>Pilih Mahasiswa</td><td>: <select name='nimPilihan' style='padding: 8px; width: 280px; border: 1px solid #ccc; border-radius: 4px;'>");
        for(String[] s : mhs) sb.append("<option value='").append(s[0]).append("'>").append(s[0]).append(" - ").append(s[1]).append("</option>");
        sb.append("        </select></td></tr>")
          .append("    <tr><td>Pilih Mata Kuliah</td><td>: <select name='mkPilihan' style='padding: 8px; width: 280px; border: 1px solid #ccc; border-radius: 4px;'>");
        for(String[] s : mk) sb.append("<option value='").append(s[0]).append("'>").append(s[0]).append(" - ").append(s[1]).append("</option>");
        sb.append("        </select></td></tr>")
          .append("    <tr><td>Nilai Angka (0-100)</td><td>: <input type='number' name='nilaiAngka' min='0' max='100' style='padding: 8px; width: 100px; border: 1px solid #ccc; border-radius: 4px;' required></td></tr>")
          .append("    <tr><td></td><td><button type='submit' style='background: #2b5876; color: white; border: none; padding: 10px 24px; border-radius: 4px; cursor: pointer; font-weight: 500;'>Simpan Lembar Nilai</button></td></tr>")
          .append("  </table>")
          .append("</form>")
          .append("<h3>Rekapitulasi Nilai Akademik</h3>")
          .append("<table border='1' width='100%' cellpadding='10' style='border-collapse: collapse; text-align: left; border: 1px solid #e1e4e8; background: white;'>")
          .append("  <tr bgcolor='#eef2f5' style='color: #2b5876;'><th>NIM</th><th>Nama Mahasiswa</th><th>Kode MK</th><th>Mata Kuliah</th><th>Nilai Angka</th><th>Grade</th></tr>");
        for(String[] nl : list) {
            sb.append("<tr><td>").append(nl[0]).append("</td><td>").append(nl[1]).append("</td><td>").append(nl[2]).append("</td><td>").append(nl[3]).append("</td><td>").append(nl[4]).append("</td><td><b>").append(nl[5]).append("</b></td></tr>");
        }
        return sb.append("</table>").toString();
    }

    // --- HALAMAN LAPORAN RESMI (MENGGUNAKAN TRICK CSS PRINT FIX) ---
    private String buildHalamanLaporan(ArrayList<String[]> listNilai, ArrayList<String[]> listMhs, String filterNIM) {
        StringBuilder sb = new StringBuilder();
        
        // MENYUNTIKKAN CSS TRICK PRINT KE DALAM HALAMAN LAPORAN
        sb.append("<style type='text/css'>")
          .append("  @media print {")
          .append("    body { background: white; color: black; margin: 0; padding: 0; font-family: sans-serif; }")
          .append("    @page { size: auto; margin: 15mm 15mm 15mm 15mm; }") // Menghilangkan teks header url, nama file, dan tanggal browser
          .append("    td:first-child, th:first-child { display: none !important; }") // Menyembunyikan kolom sidebar kiri proyek saat diprint
          .append("    .no-print { display: none !important; }") // Kelas CSS penanda objek yang dilarang ikut tercetak
          .append("    .print-full { width: 100% !important; margin: 0 !important; padding: 0 !important; }")
          .append("  }")
          .append("</style>");

        sb.append("<div class='print-full'>")
          .append("<div class='no-print'>") // Bungkus form filter agar tidak ikut tercetak di kertas
          .append("  <h2>Laporan Nilai / KHS</h2>")
          .append("  <form action='AdminServlet' method='GET' style='margin-bottom: 25px; background: #f8f9fa; padding: 20px; border-radius: 8px; border: 1px solid #e1e4e8;'>")
          .append("    <input type='hidden' name='page' value='laporannilai'>")
          .append("    Pilih Mahasiswa : <select name='filterNIM' style='padding: 8px; width: 260px; border: 1px solid #ccc; border-radius: 4px;' onchange='this.form.submit()'>")
          .append("      <option value=''>-- Semua Mahasiswa --</option>");
        for(String[] mhs : listMhs) {
            String selected = (mhs[0].equals(filterNIM)) ? "selected" : "";
            sb.append("<option value='").append(mhs[0]).append("' ").append(selected).append(">").append(mhs[0]).append(" - ").append(mhs[1]).append("</option>");
        }
        sb.append("    </select>")
          .append("    <button type='submit' style='background: #2b5876; color: white; border: none; padding: 8px 16px; border-radius: 4px; margin-left: 10px; cursor: pointer;'>Tampilkan</button>")
          .append("  </form>")
          .append("</div>");

        // Judul Kop Surat Resmi yang hanya keluar saat dicetak atau dilihat
        sb.append("<center><h2>KARTU HASIL STUDI (KHS) MAHASISWA</h2><h3>SISTEM INFORMASI AKADEMIK</h3></center><br>")
          .append("<table border='1' width='100%' cellpadding='10' style='border-collapse: collapse; text-align: left; border: 1px solid #333; background: white;'>")
          .append("  <tr bgcolor='#eef2f5' style='color: #2b5876; border-bottom: 2px solid #333;'><th>NIM</th><th>Mahasiswa</th><th>Mata Kuliah</th><th>Angka</th><th>Grade</th></tr>");
        
        for(String[] nl : listNilai) {
            if(filterNIM == null || filterNIM.equals("") || nl[0].equals(filterNIM)) {
                sb.append("<tr><td>").append(nl[0]).append("</td><td>").append(nl[1]).append("</td><td>").append(nl[3]).append("</td><td>").append(nl[4]).append("</td><td><b>").append(nl[5]).append("</b></td></tr>");
            }
        }
        sb.append("</table><br><br>")
          .append("<div class='no-print'>") // Sembunyikan tombol cetak dari kertas dokumen
          .append("  <button onclick='window.print()' style='background: #2b5876; color: white; border: none; padding: 12px 25px; border-radius: 4px; font-weight: bold; cursor: pointer; box-shadow: 0 2px 5px rgba(0,0,0,0.2);'>Cetak Laporan Rill</button>")
          .append("</div>")
          .append("</div>");
          
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}