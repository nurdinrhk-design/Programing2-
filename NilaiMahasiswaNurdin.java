import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Mahasiswa {
    String nim;
    String nama;
    double nilai;

    public Mahasiswa(String nim, String nama, double nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

    public String getGrade() {
        if (nilai >= 85) return "A";
        else if (nilai >= 70) return "B";
        else if (nilai >= 55) return "C";
        else if (nilai >= 40) return "D";
        else return "E";
    }

    @Override
    public String toString() {
        return String.format("| %-15s | %-20s | %-6.2f | %-5s |", nim, nama, nilai, getGrade());
    }
}

public class NilaiMahasiswa {
    static List<Mahasiswa> daftarMahasiswa = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    
    // Warna untuk terminal
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String RESET = "\033[0m";

    public static void main(String[] args) {
        // Inisialisasi 5 data mahasiswa acak
        initData();

        int pilihan = -1;
        while (pilihan != 0) {
            cetakMenu();
            System.out.print(GREEN_BOLD + "Pilihan: " + RESET);
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
                eksekusiPilihan(pilihan);
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    static void initData() {
        // 5 Data Mahasiswa Acak (Format NIM Teknik Informatika)
        daftarMahasiswa.add(new Mahasiswa("231011400123", "Ahmad Fauzi", 87.5));
        daftarMahasiswa.add(new Mahasiswa("231011400456", "Siska Putri", 78.0));
        daftarMahasiswa.add(new Mahasiswa("231011400789", "Bambang Heru", 65.5));
        daftarMahasiswa.add(new Mahasiswa("231011400321", "Citra Lestari", 92.0));
        daftarMahasiswa.add(new Mahasiswa("231011400654", "Dodi Setiawan", 42.0));
    }

    static void cetakMenu() {
        System.out.println("\n========================================");
        System.out.println("      SISTEM DATA NILAI MAHASISWA       ");
        System.out.println("========================================");
        System.out.println(" 1. Tambah Data Mahasiswa");
        System.out.println(" 2. Tampilkan Semua Nilai");
        System.out.println(" 3. Tampilkan Rata-rata Kelas");
        System.out.println(" 4. Cari Mahasiswa (NIM)");
        System.out.println(" 5. Hapus Mahasiswa");
        System.out.println(" 6. Rekap Grade");
        System.out.println(" 0. Keluar");
        System.out.println("----------------------------------------");
    }

    static void eksekusiPilihan(int pil) {
        switch (pil) {
            case 1 -> tambahData();
            case 2 -> tampilkanData();
            case 3 -> hitungRataRata();
            case 4 -> cariData();
            case 5 -> hapusData();
            case 6 -> rekapGrade();
            case 0 -> System.out.println("Program selesai.");
            default -> System.out.println("Menu tidak tersedia.");
        }
    }

    static void tampilkanData() {
        System.out.println("\n+-----------------+----------------------+--------+-------+");
        System.out.println("|       NIM       |         Nama         | Nilai  | Grade |");
        System.out.println("+-----------------+----------------------+--------+-------+");
        for (Mahasiswa m : daftarMahasiswa) {
            System.out.println(m);
        }
        System.out.println("+-----------------+----------------------+--------+-------+");
    }

    static void tambahData() {
        System.out.print("NIM   : "); String nim = scanner.nextLine();
        System.out.print("Nama  : "); String nama = scanner.nextLine();
        System.out.print("Nilai : "); double nilai = Double.parseDouble(scanner.nextLine());
        daftarMahasiswa.add(new Mahasiswa(nim, nama, nilai));
        System.out.println("Data berhasil ditambahkan.");
    }

    static void hitungRataRata() {
        double total = 0;
        for (Mahasiswa m : daftarMahasiswa) total += m.nilai;
        System.out.printf("Rata-rata Nilai: %.2f\n", (total / daftarMahasiswa.size()));
    }

    static void cariData() {
        System.out.print("Masukkan NIM: ");
        String cari = scanner.nextLine();
        for (Mahasiswa m : daftarMahasiswa) {
            if (m.nim.equals(cari)) {
                System.out.println("Ditemukan: " + m);
                return;
            }
        }
        System.out.println("Data tidak ditemukan.");
    }

    static void hapusData() {
        System.out.print("Masukkan NIM yang akan dihapus: ");
        String hapus = scanner.nextLine();
        if (daftarMahasiswa.removeIf(m -> m.nim.equals(hapus))) {
            System.out.println("Data berhasil dihapus.");
        } else {
            System.out.println("NIM tidak ditemukan.");
        }
    }

    static void rekapGrade() {
        int[] counts = new int[5]; // A, B, C, D, E
        for (Mahasiswa m : daftarMahasiswa) {
            switch (m.getGrade()) {
                case "A" -> counts[0]++;
                case "B" -> counts[1]++;
                case "C" -> counts[2]++;
                case "D" -> counts[3]++;
                case "E" -> counts[4]++;
            }
        }
        System.out.println("\nREKAP GRADE:");
        System.out.println("A: " + counts[0] + " | B: " + counts[1] + " | C: " + counts[2] + " | D: " + counts[3] + " | E: " + counts[4]);
    }
}