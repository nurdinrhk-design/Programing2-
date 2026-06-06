<%@page import="com.unpam.model.MataKuliahDAO"%>
<%@page import="com.unpam.model.MataKuliah"%>
<%@page import="com.unpam.model.MahasiswaDAO"%>
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
        <title>Dashboard - Input Nilai</title>
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
                        <a href="index.jsp" class="active"><i class="fa-solid fa-pen-to-square"></i> Input Nilai</a>
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
                    <h3>Input Transaksi Nilai Mahasiswa</h3>
                    
                    <% if(request.getAttribute("pesan") != null) { %>
                        <div class="alert alert-success">
                            <i class="fa-solid fa-circle-check"></i> <%= request.getAttribute("pesan") %>
                        </div>
                    <% } %>

                    <div class="form-container" style="max-width: 600px;">
                        <form action="NilaiController" method="POST">
                            <input type="hidden" name="action" value="Simpan">
                            
                            <div class="form-group">
                                <label>Pilih Mahasiswa</label>
                                <select name="nim" class="form-control" required>
                                    <option value="">-- Pilih Mahasiswa --</option>
                                    <%
                                        MahasiswaDAO mDao = new MahasiswaDAO();
                                        List<Mahasiswa> listM = mDao.tampilSemua();
                                        for(Mahasiswa m : listM) {
                                    %>
                                        <option value="<%= m.getNim() %>"><%= m.getNim() %> - <%= m.getNama() %></option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Pilih Mata Kuliah</label>
                                <select name="kode_mk" class="form-control" required>
                                    <option value="">-- Pilih Mata Kuliah --</option>
                                    <%
                                        MataKuliahDAO mkDao = new MataKuliahDAO();
                                        List<MataKuliah> listMk = mkDao.tampilSemua();
                                        for(MataKuliah mk : listMk) {
                                    %>
                                        <option value="<%= mk.getKodeMk() %>"><%= mk.getKodeMk() %> - <%= mk.getNamaMk() %> (<%= mk.getSks() %> SKS)</option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <div style="display:flex; gap:15px;">
                                <div class="form-group" style="flex:1;">
                                    <label>Semester</label>
                                    <input type="text" name="semester" class="form-control" placeholder="Contoh: 6" required>
                                </div>
                                <div class="form-group" style="flex:1;">
                                    <label>Kelas</label>
                                    <input type="text" name="kelas" class="form-control" placeholder="Contoh: 06TPLM001" required>
                                </div>
                            </div>
                            
                            <div style="display:flex; gap:15px;">
                                <div class="form-group" style="flex:1;">
                                    <label>Nilai Tugas</label>
                                    <input type="number" step="0.01" name="tugas" class="form-control" value="0" required>
                                </div>
                                <div class="form-group" style="flex:1;">
                                    <label>Nilai UTS</label>
                                    <input type="number" step="0.01" name="uts" class="form-control" value="0" required>
                                </div>
                                <div class="form-group" style="flex:1;">
                                    <label>Nilai UAS</label>
                                    <input type="number" step="0.01" name="uas" class="form-control" value="0" required>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary"><i class="fa-solid fa-save"></i> Simpan Nilai</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="footer">
                Copyright &copy; 2026 Universitas Pamulang | Admin Dashboard WebAplNurdin
            </div>
        </div>
    </body>
</html>