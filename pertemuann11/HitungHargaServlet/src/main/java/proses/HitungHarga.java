package proses;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HitungHarga", urlPatterns = {"/HitungHarga"})
public class HitungHarga extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String namaBarang = request.getParameter("namaBarang");
        String hargaSatuan = request.getParameter("hargaSatuan");
        String jumlah = request.getParameter("jumlah");
        
        int harga = 0;
        int jumlahBarang = 0;
        int diskon = 0;
        int totalBeforeDiscount = 0;
        int total = 0;
        
        try {
            if (hargaSatuan != null) {
                harga = Integer.parseInt(hargaSatuan);
            }
        } catch (NumberFormatException ex) {
            // Log or ignore
        }
        
        try {
            if (jumlah != null) {
                jumlahBarang = Integer.parseInt(jumlah);
            }
        } catch (NumberFormatException ex) {
            // Log or ignore
        }
        
        totalBeforeDiscount = jumlahBarang * harga;
        total = totalBeforeDiscount;
        
        if ((jumlahBarang >= 100) && (totalBeforeDiscount >= 1000000)) {
            diskon = (int) (totalBeforeDiscount * 0.05);
            total = totalBeforeDiscount - diskon;
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Menghitung Harga (Servlet)</title>");
            out.println("<style>");
            out.println("body { font-family: 'Inter', sans-serif; background: linear-gradient(135deg, #0f172a, #1e293b); color: #f8fafc; min-height: 100vh; margin: 0; display: flex; align-items: center; justify-content: center; }");
            out.println(".container { background: rgba(30, 41, 59, 0.7); backdrop-filter: blur(10px); border: 1px solid rgba(255, 255, 255, 0.1); border-radius: 12px; padding: 30px; box-shadow: 0 4px 30px rgba(0, 0, 0, 0.5); width: 400px; }");
            out.println("h2 { text-align: center; margin-bottom: 20px; background: linear-gradient(to right, #38bdf8, #818cf8); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
            out.println("td { padding: 10px 0; border-bottom: 1px solid rgba(255, 255, 255, 0.05); }");
            out.println("td:first-child { font-weight: 500; color: #94a3b8; }");
            out.println("td:last-child { text-align: right; font-weight: bold; }");
            out.println(".btn { display: block; text-align: center; width: calc(100% - 20px); padding: 10px; border-radius: 6px; text-decoration: none; background: linear-gradient(to right, #3b82f6, #8b5cf6); color: white; font-weight: bold; transition: transform 0.2s, opacity 0.2s; margin: 0 auto; }");
            out.println(".btn:hover { transform: scale(1.02); opacity: 0.9; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Hasil Penghitungan Harga</h2>");
            out.println("<table>");
            out.println("<tr><td>Nama Barang</td><td>" + (namaBarang != null ? namaBarang : "") + "</td></tr>");
            out.println("<tr><td>Harga Satuan</td><td>Rp " + String.format("%,d", harga) + "</td></tr>");
            out.println("<tr><td>Jumlah</td><td>" + jumlahBarang + "</td></tr>");
            out.println("<tr><td>Diskon</td><td>Rp " + String.format("%,d", diskon) + "</td></tr>");
            out.println("<tr><td>Total</td><td>Rp " + String.format("%,d", total) + "</td></tr>");
            out.println("</table>");
            out.println("<a class='btn' href='index.jsp'>Kembali</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
