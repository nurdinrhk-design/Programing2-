<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menghitung Harga</title>
        <style>
            body {
                font-family: 'Inter', sans-serif;
                background: linear-gradient(135deg, #0f172a, #1e293b);
                color: #f8fafc;
                min-height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .container {
                background: rgba(30, 41, 59, 0.7);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 4px 30px rgba(0, 0, 0, 0.5);
                width: 400px;
            }
            h2 {
                text-align: center;
                margin-bottom: 20px;
                background: linear-gradient(to right, #38bdf8, #818cf8);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            td {
                padding: 10px 0;
            }
            input[type="text"] {
                width: 95%;
                padding: 8px;
                border-radius: 6px;
                border: 1px solid #475569;
                background-color: #0f172a;
                color: #f8fafc;
                font-size: 14px;
            }
            input[type="submit"] {
                width: 100%;
                padding: 10px;
                border-radius: 6px;
                border: none;
                background: linear-gradient(to right, #3b82f6, #8b5cf6);
                color: white;
                font-weight: bold;
                cursor: pointer;
                transition: transform 0.2s, opacity 0.2s;
            }
            input[type="submit"]:hover {
                transform: scale(1.02);
                opacity: 0.9;
            }
            .info {
                font-size: 12px;
                color: #94a3b8;
                text-align: center;
                margin-top: 15px;
                line-height: 1.5;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Form Memasukkan Nilai</h2>
            <form action="HitungHarga" method="post">
                <table>
                    <tr>
                        <td>Nama Barang</td>
                        <td><input type="text" name="namaBarang" required/></td>
                    </tr>
                    <tr>
                        <td>Harga Satuan</td>
                        <td><input type="text" name="hargaSatuan" required/></td>
                    </tr>
                    <tr>
                        <td>Jumlah</td>
                        <td><input type="text" name="jumlah" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="padding-top: 15px;">
                            <input type="submit" value="Hitung"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div class="info">
                Diskon diberikan sebesar 5% jika jumlah >= 100 dan total harga sebelum diskon >= 1.000.000
            </div>
        </div>
    </body>
</html>
