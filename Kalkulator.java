import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesainGUI extends JFrame {
    
    // Deklarasi Komponen sesuai dengan gambar
    private JTextField txtAngka1, txtAngka2, txtHasil;
    private JButton btnTambah, btnHapus, btnExit;

    public DesainGUI() {
        // Pengaturan dasar Frame (Jendela Aplikasi)
        setTitle("Aplikasi Pertambahan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // Agar aplikasi muncul di tengah layar
        setLayout(new BorderLayout(10, 10));

        // 1. Panel Atas: Untuk menampung JLabel dan JTextField
        // Menggunakan GridLayout (3 baris, 2 kolom)
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(3, 2, 10, 15));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        // Baris Pertama
        panelInput.add(new JLabel("Angka Pertama"));
        txtAngka1 = new JTextField();
        panelInput.add(txtAngka1);

        // Baris Kedua
        panelInput.add(new JLabel("Angka Kedua"));
        txtAngka2 = new JTextField();
        panelInput.add(txtAngka2);

        // Baris Ketiga
        panelInput.add(new JLabel("Hasil"));
        txtHasil = new JTextField();
        txtHasil.setEditable(false); // Field hasil dibuat agar tidak bisa diketik manual
        panelInput.add(txtHasil);

        // 2. Panel Bawah: Untuk menampung JButton
        // Menggunakan FlowLayout agar tombol berjajar ke samping
        JPanel panelTombol = new JPanel();
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTombol.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus");
        btnExit = new JButton("Exit");

        panelTombol.add(btnTambah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnExit);

        // Memasukkan panel ke dalam Frame utama
        add(panelInput, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        // 3. Menambahkan Logika (Action Listeners) pada Tombol

        // Logika Tombol "Tambah"
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Mengambil nilai teks, mengubahnya jadi angka, lalu menjumlahkannya
                    double angka1 = Double.parseDouble(txtAngka1.getText());
                    double angka2 = Double.parseDouble(txtAngka2.getText());
                    double hasil = angka1 + angka2;
                    txtHasil.setText(String.valueOf(hasil));
                } catch (NumberFormatException ex) {
                    // Menampilkan pesan error jika yang diinput bukan angka
                    JOptionPane.showMessageDialog(null, "Masukkan angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Logika Tombol "Hapus"
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengosongkan semua field
                txtAngka1.setText("");
                txtAngka2.setText("");
                txtHasil.setText("");
                txtAngka1.requestFocus(); // Mengembalikan kursor ke kolom pertama
            }
        });

        // Logika Tombol "Exit"
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Menutup aplikasi
            }
        });
    }

    public static void main(String[] args) {
        // Menjalankan GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DesainGUI().setVisible(true);
            }
        });
    }
}