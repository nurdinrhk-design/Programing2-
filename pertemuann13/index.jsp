<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='style.css' rel='stylesheet' type='text/css' />
        <title>Informasi Nilai Mahasiswa</title>
    </head>
    <body>
        <%
            // 1. MENU SEBELUM LOGIN
            String menu = "<br><b>Master Data</b><br>"
                    + "<a href=.>Mahasiswa</a><br>"
                    + "<a href=.>Mata Kuliah</a><br><br>"
                    + "<b>Transaksi</b><br>"
                    + "<a href=.>Nilai</a><br><br>"
                    + "<b>Laporan</b><br>"
                    + "<a href=.>Nilai</a><br><br>"
                    + "<a href=LoginController>Login</a><br><br>";

            // 2. NAVIGASI ATAS
            String topMenu = "<nav><ul>"
                    + "<li><a href=.>Home</a></li>"
                    + "<li><a href=#>Master Data</a>"
                    + "<ul>"
                    + "<li><a href=.>Mahasiswa</a></li>"
                    + "<li><a href=.>Mata Kuliah</a></li>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=#>Transaksi</a>"
                    + "<ul>"
                    + "<li><a href=.>Nilai</a></li>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=#>Laporan</a>"
                    + "<ul>"
                    + "<li><a href=.>Nilai</a></li>"
                    + "</ul>"
                    + "</li>"
                    + "<li><a href=LoginController>Login</a></li>"
                    + "</ul>"
                    + "</nav>";

            String konten = "<br><h1>Selamat Datang</h1>";
            String userName = "";

            // 3. MEMPROSES SESSION DINAMIS DARI SERVLET
            if (!session.isNew()) {
                try {
                    if (session.getAttribute("userName") != null) {
                        userName = session.getAttribute("userName").toString();
                    }
                } catch (Exception ex) {}

                try {
                    if (session.getAttribute("konten") != null) {
                        konten = session.getAttribute("konten").toString();
                    }
                } catch (Exception ex) {}

                if (!((userName == null) || userName.equals(""))) {
                    try {
                        if (session.getAttribute("menu") != null) {
                            menu = session.getAttribute("menu").toString();
                        }
                    } catch (Exception ex) {}

                    try {
                        if (session.getAttribute("topMenu") != null) {
                            topMenu = session.getAttribute("topMenu").toString();
                        }
                    } catch (Exception ex) {}
                }
            }
        %>

    <center>
        <table width="90%" bgcolor="#ffffff" style="border: none;">
            <tr>
                <td colspan="2" align="center" style="background: linear-gradient(135deg, #2b5876 0%, #4e4376 100%); color: white; padding: 30px 20px;">
                    <h2 style="margin: 0; font-weight: 400; font-size: 18px; letter-spacing: 1px; opacity: 0.9;">Informasi Nilai Mahasiswa</h2>
                    <h1 style="margin: 5px 0 10px 0; font-size: 28px; font-weight: 700; letter-spacing: 0.5px;">UNIVERSITAS PAMULANG</h1>
                    <h4 style="margin: 0; font-weight: 300; font-size: 13px; opacity: 0.8;">Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</h4>
                </td>
            </tr>
            
            <tr>
                <td colspan="2" bgcolor="#ffffff" style="padding: 0;">
                    <%=topMenu %>
                </td>
            </tr>
            
            <tr height="450">
                <td width="240" align="left" valign="top" bgcolor="#ffffff" style="border-right: 1px solid #e1e4e8; padding: 10px;">
                    <div id='menu'>
                        <%=menu %>
                    </div>
                </td>
                
                <td align="left" valign="top" bgcolor="#ffffff" style="padding: 40px;">
                    <div style="color: #2b5876; padding-bottom: 10px; margin-bottom: 15px;">
                        <%=konten %>
                        <% if(!userName.equals("")) { %>
                            <h3 style="color: #666; margin-top: 5px; font-weight: 400;">User Aktif: <%=userName%></h3>
                        <% } %>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td colspan="2" align="center" bgcolor="#f4f6f8" style="color: #777; padding: 20px; font-size: 13px; border-top: 1px solid #e1e4e8;">
                    <strong>Copyright &copy; 2016 Universitas Pamulang</strong><br>
                    <span style="opacity: 0.8; font-size: 12px;">Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</span>
                </td>
            </tr>
        </table>
    </center>
</body>
</html>