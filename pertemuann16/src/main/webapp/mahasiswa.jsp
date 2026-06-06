<%@page import="com.unpam.model.Mahasiswa"%>
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
        <title>Data Mahasiswa - Web Admin Nurdin</title>
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
                        <a href="MahasiswaController" class="active"><i class="fa-solid fa-users"></i> Mahasiswa</a>
                        <a href="MataKuliahController"><i class="fa-solid fa-book"></i> Mata Kuliah</a>
                    </div>
                    <div class="menu-group">
                        <h4>Transaksi</h4>
                        <a href="index.jsp"><i class="fa-solid fa-pen-to-square"></i> Input Nilai</a>
                    </div>
                    <div class="menu-group">
                        <h4>Laporan</h4>
                        <a href="RekapController"><i class="fa-solid fa-chart-bar"></i> Rekap Nilai</a>
                    </div>
                    <div class="menu-group">
                        <h4>Sistem</h4>
                        <a href="LogoutController" class="logout"><i class="fa-solid fa-right-from-bracket"></i> Keluar</a>
                    </div>
                </div>

                <div class="content">
                    <h3>Data Master Mahasiswa</h3>

                    <div class="tabel-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>NIM</th>
                                    <th>Nama Mahasiswa</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Mahasiswa> daftar = (List<Mahasiswa>) request.getAttribute("daftarMahasiswa");
                                    if(daftar != null && !daftar.isEmpty()){
                                        int no = 1;
                                        for(Mahasiswa m : daftar){
                                %>
                                <tr>
                                    <td><%= no++ %></td>
                                    <td><%= m.getNim() %></td>
                                    <td><%= m.getNama() %></td>
                                </tr>
                                <% 
                                        }
                                    } else { 
                                %>
                                <tr>
                                    <td colspan="3" style="text-align: center; padding: 20px;">Belum ada data mahasiswa.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="footer">
                Copyright &copy; 2026 Universitas Pamulang | Admin Dashboard WebAplNurdin
            </div>
        </div>
    </body>
</html>