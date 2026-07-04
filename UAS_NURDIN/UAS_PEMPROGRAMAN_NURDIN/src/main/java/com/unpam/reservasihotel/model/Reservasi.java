package com.unpam.reservasihotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaTamu;

    private String tipeKamar;

    private LocalDate tanggalCheckIn;

    private LocalDate tanggalCheckOut;

    // Konstruktor Default (diperlukan oleh JPA)
    public Reservasi() {
    }

    // Konstruktor dengan Parameter (OOP)
    public Reservasi(String namaTamu, String tipeKamar, LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.namaTamu = namaTamu;
        this.tipeKamar = tipeKamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
    }

    // Getter dan Setter (OOP Encapsulation)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public LocalDate getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public void setTanggalCheckIn(LocalDate tanggalCheckIn) {
        this.tanggalCheckIn = tanggalCheckIn;
    }

    public LocalDate getTanggalCheckOut() {
        return tanggalCheckOut;
    }

    public void setTanggalCheckOut(LocalDate tanggalCheckOut) {
        this.tanggalCheckOut = tanggalCheckOut;
    }

    @Override
    public String toString() {
        return "Reservasi{" +
                "id=" + id +
                ", namaTamu='" + namaTamu + '\'' +
                ", tipeKamar='" + tipeKamar + '\'' +
                ", tanggalCheckIn=" + tanggalCheckIn +
                ", tanggalCheckOut=" + tanggalCheckOut +
                '}';
    }
}
