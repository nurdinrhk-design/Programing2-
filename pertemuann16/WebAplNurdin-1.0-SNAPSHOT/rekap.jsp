<%@page import="com.unpam.model.Nilai"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("adminLog") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rekap Nilai - Web Admin Nurdin</title>
        <link rel="stylesheet" type="text/css" href="style.css?v=2.0">
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="header-left">
                    <h1>Web Admin - Manajemen Nilai Nurdin</h1>
                    <p>Welcome, <%= session.getAttribute("namaAdmin") %> (<%= session.getAttribute("levelAdmin") %>)</p>
                </div>
            </div>

            <div class="main-content">
                <div class="sidebar">
                    <div class="menu-group">
                        <h4>Master Data</h4>
                        <a href="MahasiswaController"><i class="fa-solid fa-users"></i> Mahasiswa</a>
                        <a href="MataKuliahController"><i class="fa-solid fa-book"></i> Mata Kuliah</a>
                    </div>
                    <div class="menu-group">
                        <h4>Transaksi</h4>
                        <a href="index.jsp"><i class="fa-solid fa-pen-to-square"></i> Input Nilai</a>
                    </div>
                    <div class="menu-group">
                        <h4>Laporan</h4>
                        <a href="RekapController" class="active"><i class="fa-solid fa-chart-bar"></i> Rekap Nilai</a>
                    </div>
                    <div class="menu-group">
                        <h4>Sistem</h4>
                        <a href="LogoutController" class="logout"><i class="fa-solid fa-right-from-bracket"></i> Keluar</a>
                    </div>
                </div>

                <div class="content">
                    <h3>Data Rekapitulasi Nilai Mahasiswa</h3>
                    
                    <% if(request.getAttribute("pesan") != null) { %>
                        <div class="alert alert-success">
                            <i class="fa-solid fa-circle-check"></i> <%= request.getAttribute("pesan") %>
                        </div>
                    <% } %>

                    <div class="tabel-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>NIM</th>
                                    <th>Nama Mahasiswa</th>
                                    <th>Mata Kuliah</th>
                                    <th>Semester</th>
                                    <th>Kelas</th>
                                    <th>Tugas</th>
                                    <th>UTS</th>
                                    <th>UAS</th>
                                    <th>Nilai Akhir</th>
                                    <th>Grade</th>
                                    <th>Status</th>
                                    <th>Aksi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Nilai> daftar = (List<Nilai>) request.getAttribute("daftarNilai");
                                    if(daftar != null && !daftar.isEmpty()){
                                        int no = 1;
                                        for(Nilai n : daftar){
                                %>
                                <tr>
                                    <td><%= no++ %></td>
                                    <td><%= n.getNim() %></td>
                                    <td><%= n.getMahasiswa().getNama() %></td>
                                    <td><%= n.getMataKuliah().getNamaMk() %></td>
                                    <td><%= n.getSemester() %></td>
                                    <td><%= n.getKelas() %></td>
                                    <td><%= n.getTugas() %></td>
                                    <td><%= n.getUts() %></td>
                                    <td><%= n.getUas() %></td>
                                    <td style="font-weight: bold; color: #3c8dbc;"><%= String.format("%.2f", n.getNilaiAkhir()) %></td>
                                    <td style="font-weight: bold;"><%= n.getHuruf() %></td>
                                    <td>
                                        <% if(n.getStatus().equals("Lulus")) { %>
                                            <span class="status-badge status-lulus">Lulus</span>
                                        <% } else { %>
                                            <span class="status-badge status-tidak-lulus">Tidak Lulus</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <form action="NilaiController" method="POST" style="display:inline;" onsubmit="return confirm('Yakin ingin menghapus?');">
                                            <input type="hidden" name="action" value="Hapus">
                                            <input type="hidden" name="nim" value="<%= n.getNim() %>">
                                            <input type="hidden" name="kode_mk" value="<%= n.getKodeMk() %>">
                                            <button type="submit" class="btn btn-danger" style="padding: 6px 12px; font-size: 13px;"><i class="fa-solid fa-trash"></i> Hapus</button>
                                        </form>
                                    </td>
                                </tr>
                                <% 
                                        }
                                    } else { 
                                %>
                                <tr>
                                    <td colspan="13" style="text-align: center; padding: 20px;">Belum ada data nilai.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <div style="margin-top: 25px; text-align: right;">
                        <a href="LaporanNilaiController" target="_blank" class="btn btn-warning"><i class="fa-solid fa-print"></i> Cetak PDF Report</a>
                    </div>

                </div>
            </div>

            <div class="footer">
                Copyright &copy; 2026 Universitas Pamulang | Admin Dashboard WebAplNurdin
            </div>
        </div>
    </body>
</html>