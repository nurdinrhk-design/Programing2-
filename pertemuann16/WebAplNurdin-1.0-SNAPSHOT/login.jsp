<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login - Web Admin Nurdin</title>
        <link rel="stylesheet" type="text/css" href="style.css?v=2.0">
    </head>
    <body class="login-page">
        <div class="login-box">
            <div class="login-logo">
                <h1>Admin Nurdin</h1>
                <p>Silahkan login ke akun Anda</p>
            </div>
            
            <% if(request.getAttribute("pesanError") != null) { %>
                <div class="alert alert-danger">
                    <i class="fa-solid fa-circle-exclamation"></i> <%= request.getAttribute("pesanError") %>
                </div>
            <% } %>

            <form action="LoginController" method="POST">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" class="form-control" placeholder="Masukkan Username" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" placeholder="Masukkan Password" required>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 10px;">
                    <i class="fa-solid fa-right-to-bracket"></i> Masuk Sistem
                </button>
            </form>
        </div>
    </body>
</html>