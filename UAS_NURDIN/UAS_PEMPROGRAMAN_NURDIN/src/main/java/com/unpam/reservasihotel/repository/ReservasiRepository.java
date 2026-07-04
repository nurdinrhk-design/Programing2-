package com.unpam.reservasihotel.repository;

import com.unpam.reservasihotel.model.Reservasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {
    
    // Query method untuk menghitung jumlah reservasi berdasarkan tipe kamar
    long countByTipeKamar(String tipeKamar);
    
    // Menemukan daftar reservasi berdasarkan nama tamu (optional/pencarian)
    List<Reservasi> findByNamaTamuContainingIgnoreCase(String namaTamu);
}
