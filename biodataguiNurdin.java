import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BiodataGUI extends JFrame {

    // Deklarasi Komponen
    private JTextField txtNim, txtNama, txtProdi, txtFakultas;
    private JButton btnSimpan, btnKeluar;

    public BiodataGUI() {
        // Pengaturan Jendela Utama (Frame)
        setTitle("Aplikasi Biodata Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null); // Tampil di tengah layar
        setLayout(new BorderLayout());

        // --- 1. PANEL HEADER (Bagian Atas) ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(41, 128, 185)); // Warna Biru Elegan
        panelHeader.setBorder(new EmptyBorder(15, 10, 15, 10)); // Padding
        
        JLabel lblJudul = new JLabel("BIODATA MAHASISWA");
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelHeader.add(lblJudul);

        // --- 2. PANEL BODY (Bagian Tengah/Form) ---
        JPanel panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(4, 2, 10, 15)); // 4 Baris, 2 Kolom, jarak 10x15
        panelBody.setBackground(new Color(236, 240, 241)); // Warna Abu-abu sangat terang
        panelBody.setBorder(new EmptyBorder(25, 30, 25, 30)); // Margin kiri kanan atas bawah

        // Mengatur Font untuk Label dan Textfield
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fontText = new Font("Segoe UI", Font.PLAIN, 14);

        // Baris 1: NIM
        JLabel lblNim = new JLabel("Nomor Induk Mahasiswa");
        lblNim.setFont(fontLabel);
        txtNim = new JTextField("231011401935"); // Data NIM dimasukkan di sini
        txtNim.setFont(fontText);

        // Baris 2: Nama
        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setFont(fontLabel);
        txtNama = new JTextField("Muhamad Nurdin"); // Data Nama dimasukkan di sini
        txtNama.setFont(fontText);

        // Baris 3: Program Studi
        JLabel lblProdi = new JLabel("Program Studi");
        lblProdi.setFont(fontLabel);
        txtProdi = new JTextField("Teknik Informatika"); 
        txtProdi.setFont(fontText);

        // Baris 4: Fakultas
        JLabel lblFakultas = new JLabel("Fakultas");
        lblFakultas.setFont(fontLabel);
        txtFakultas = new JTextField("Ilmu Komputer");
        txtFakultas.setFont(fontText);

        // Memasukkan komponen ke dalam Panel Body
        panelBody.add(lblNim);      panelBody.add(txtNim);
        panelBody.add(lblNama);     panelBody.add(txtNama);
        panelBody.add(lblProdi);    panelBody.add(txtProdi);
        panelBody.add(lblFakultas); panelBody.add(txtFakultas);

        // --- 3. PANEL FOOTER (Bagian Bawah/Tombol) ---
        JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelFooter.setBackground(new Color(236, 240, 241));

        btnSimpan = new JButton("Simpan Data");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpan.setBackground(new Color(39, 174, 96)); // Warna Hijau
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false); // Menghilangkan kotak fokus saat diklik

        btnKeluar = new JButton("Keluar Aplikasi");
        btnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnKeluar.setBackground(new Color(192, 57, 43)); // Warna Merah
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFocusPainted(false);

        panelFooter.add(btnSimpan);
        panelFooter.add(btnKeluar);

        // --- MENGGABUNGKAN SEMUA PANEL KE JENDELA UTAMA ---
        add(panelHeader, BorderLayout.NORTH);
        add(panelBody, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);

        // --- 4. LOGIKA TOMBOL (ACTION LISTENER) ---
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menampilkan pop-up pesan berhasil
                JOptionPane.showMessageDialog(null, 
                    "Data Biodata " + txtNama.getText() + " Berhasil Disimpan!", 
                    "Informasi", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menampilkan konfirmasi sebelum keluar
                int konfirmasi = JOptionPane.showConfirmDialog(null, 
                    "Apakah Anda yakin ingin keluar?", 
                    "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        // Mengubah tema aplikasi agar terlihat menyerupai desain OS modern (opsional tapi disarankan)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Menjalankan aplikasi
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BiodataGUI().setVisible(true);
            }
        });
    }
}