import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HitungNilai", urlPatterns = {"/HitungNilai"})
public class HitungNilai extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String hadir = request.getParameter("hadir");
        String pertemuan = request.getParameter("pertemuan");
        String tugas = request.getParameter("tugas");
        String uts = request.getParameter("uts");
        String uas = request.getParameter("uas");
        
        boolean hasSubmitted = (hadir != null && pertemuan != null);
        
        if (hadir == null) hadir = "";
        if (pertemuan == null) pertemuan = "";
        if (tugas == null) tugas = "";
        if (uts == null) uts = "";
        if (uas == null) uas = "";
        
        int jumlahHadir = 0;
        int jumlahPertemuan = 0;
        double nilaiTugas = 0;
        double nilaiUts = 0;
        double nilaiUas = 0;
        
        try {
            if (!hadir.isEmpty()) {
                jumlahHadir = Integer.parseInt(hadir);
            }
        } catch (NumberFormatException ex) {}
        
        try {
            if (!pertemuan.isEmpty()) {
                jumlahPertemuan = Integer.parseInt(pertemuan);
            }
        } catch (NumberFormatException ex) {}
        
        try {
            if (!tugas.isEmpty()) {
                nilaiTugas = Double.parseDouble(tugas);
            }
        } catch (NumberFormatException ex) {}
        
        try {
            if (!uts.isEmpty()) {
                nilaiUts = Double.parseDouble(uts);
            }
        } catch (NumberFormatException ex) {}
        
        try {
            if (!uas.isEmpty()) {
                nilaiUas = Double.parseDouble(uas);
            }
        } catch (NumberFormatException ex) {}
        
        double nilaiAkhir = 0;
        String grade = "";
        String status = "";
        
        if (hasSubmitted) {
            if (jumlahPertemuan > 0) {
                nilaiAkhir = (((double) jumlahHadir / (double) jumlahPertemuan) * 10) + (0.2 * nilaiTugas) + (0.3 * nilaiUts) + (0.4 * nilaiUas);
            } else {
                nilaiAkhir = Double.NaN;
            }
            
            if (Double.isNaN(nilaiAkhir)) {
                grade = "";
                status = "";
            } else {
                if (nilaiAkhir >= 80 && nilaiAkhir <= 100) {
                    grade = "A";
                    status = "Lulus";
                } else if (nilaiAkhir >= 70) {
                    grade = "B";
                    status = "Lulus";
                } else if (nilaiAkhir >= 60) {
                    grade = "C";
                    status = "Lulus";
                } else if (nilaiAkhir >= 50) {
                    grade = "D";
                    status = "Tidak Lulus";
                } else {
                    grade = "E";
                    status = "Tidak Lulus";
                }
            }
        }
        
        String nilaiAkhirStr = "";
        if (hasSubmitted) {
            if (Double.isNaN(nilaiAkhir)) {
                nilaiAkhirStr = "NaN";
            } else {
                nilaiAkhirStr = String.format("%.2f", nilaiAkhir);
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hitung Nilai (Servlet)</title>");
            out.println("<style>");
            out.println("body { font-family: 'Inter', sans-serif; background: linear-gradient(135deg, #0f172a, #1e293b); color: #f8fafc; min-height: 100vh; margin: 0; display: flex; align-items: center; justify-content: center; }");
            out.println(".container { background: rgba(30, 41, 59, 0.7); backdrop-filter: blur(10px); border: 1px solid rgba(255, 255, 255, 0.1); border-radius: 12px; padding: 30px; box-shadow: 0 4px 30px rgba(0, 0, 0, 0.5); width: 450px; }");
            out.println("h2 { text-align: center; margin-bottom: 20px; background: linear-gradient(to right, #38bdf8, #818cf8); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
            out.println("td { padding: 8px 0; }");
            out.println("td:first-child { font-weight: 500; color: #94a3b8; width: 45%; }");
            out.println("input[type='text'] { width: 90%; padding: 8px; border-radius: 6px; border: 1px solid #475569; background-color: #0f172a; color: #f8fafc; font-size: 14px; }");
            out.println("input[readonly] { background-color: #1e293b; color: #cbd5e1; border-color: #334155; font-weight: bold; }");
            out.println("input[type='submit'] { width: 100%; padding: 10px; border-radius: 6px; border: none; background: linear-gradient(to right, #3b82f6, #8b5cf6); color: white; font-weight: bold; cursor: pointer; transition: transform 0.2s, opacity 0.2s; }");
            out.println("input[type='submit']:hover { transform: scale(1.02); opacity: 0.9; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Menghitung Nilai</h2>");
            out.println("<form action='HitungNilai' method='post'>");
            out.println("<table>");
            
            out.println("<tr><td>Jumlah hadir</td><td><input type='text' name='hadir' value='" + hadir + "' required/></td></tr>");
            out.println("<tr><td>Jumlah pertemuan</td><td><input type='text' name='pertemuan' value='" + pertemuan + "' required/></td></tr>");
            out.println("<tr><td>Nilai tugas</td><td><input type='text' name='tugas' value='" + tugas + "' required/></td></tr>");
            out.println("<tr><td>Nilai UTS</td><td><input type='text' name='uts' value='" + uts + "' required/></td></tr>");
            out.println("<tr><td>Nilai UAS</td><td><input type='text' name='uas' value='" + uas + "' required/></td></tr>");
            
            if (hasSubmitted) {
                out.println("<tr><td>Nilai Akhir</td><td><input type='text' value='" + nilaiAkhirStr + "' readonly/></td></tr>");
                out.println("<tr><td>Grade</td><td><input type='text' value='" + grade + "' readonly/></td></tr>");
                out.println("<tr><td>Status</td><td><input type='text' value='" + status + "' readonly/></td></tr>");
            } else {
                out.println("<tr><td>Nilai Akhir</td><td><input type='text' value='' readonly/></td></tr>");
                out.println("<tr><td>Grade</td><td><input type='text' value='' readonly/></td></tr>");
                out.println("<tr><td>Status</td><td><input type='text' value='' readonly/></td></tr>");
            }
            
            out.println("<tr><td colspan='2' style='padding-top: 15px;'><input type='submit' value='Hitung'/></td></tr>");
            out.println("</table>");
            out.println("</form>");
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
