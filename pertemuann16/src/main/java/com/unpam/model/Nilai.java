package com.unpam.model;

public class Nilai {
    private int idNilai;
    private String nim;
    private String kodeMk;
    private String semester;
    private String kelas;
    private double tugas;
    private double uts;
    private double uas;
    private double nilaiAkhir;
    private String huruf;
    private String status;
    
    // Untuk relasi
    private Mahasiswa mahasiswa = new Mahasiswa();
    private MataKuliah mataKuliah = new MataKuliah();

    // Getters and Setters
    public int getIdNilai() { return idNilai; }
    public void setIdNilai(int idNilai) { this.idNilai = idNilai; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getKodeMk() { return kodeMk; }
    public void setKodeMk(String kodeMk) { this.kodeMk = kodeMk; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public double getTugas() { return tugas; }
    public void setTugas(double tugas) { this.tugas = tugas; }

    public double getUts() { return uts; }
    public void setUts(double uts) { this.uts = uts; }

    public double getUas() { return uas; }
    public void setUas(double uas) { this.uas = uas; }

    public double getNilaiAkhir() { return nilaiAkhir; }
    public void setNilaiAkhir(double nilaiAkhir) { this.nilaiAkhir = nilaiAkhir; }

    public String getHuruf() { return huruf; }
    public void setHuruf(String huruf) { this.huruf = huruf; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }

    public MataKuliah getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(MataKuliah mataKuliah) { this.mataKuliah = mataKuliah; }
}