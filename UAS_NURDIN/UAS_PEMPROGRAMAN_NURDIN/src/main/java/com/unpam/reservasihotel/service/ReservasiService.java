package com.unpam.reservasihotel.service;

import com.unpam.reservasihotel.exception.InvalidReservationException;
import com.unpam.reservasihotel.exception.KamarPenuhException;
import com.unpam.reservasihotel.model.Reservasi;
import com.unpam.reservasihotel.repository.ReservasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservasiService {

    private final ReservasiRepository reservasiRepository;

    // Batasan kuota kamar berdasarkan tipenya (OOP constant/map)
    private static final Map<String, Integer> KOTA_KAMAR_LIMITS = new HashMap<>();

    static {
        KOTA_KAMAR_LIMITS.put("Single", 5);  // Maksimal 5 kamar Single
        KOTA_KAMAR_LIMITS.put("Double", 3);  // Maksimal 3 kamar Double
        KOTA_KAMAR_LIMITS.put("Suite", 2);   // Maksimal 2 kamar Suite
    }

    @Autowired
    public ReservasiService(ReservasiRepository reservasiRepository) {
        this.reservasiRepository = reservasiRepository;
    }

    /**
     * Mengambil seluruh data reservasi.
     */
    public List<Reservasi> getAllReservations() {
        return reservasiRepository.findAll();
    }

    /**
     * Menyimpan reservasi baru dengan validasi tanggal dan kapasitas kamar.
     */
    @Transactional
    public Reservasi saveReservation(Reservasi reservasi) {
        // 1. Validasi Input Dasar
        if (reservasi.getNamaTamu() == null || reservasi.getNamaTamu().trim().isEmpty()) {
            throw new InvalidReservationException("Nama tamu tidak boleh kosong!");
        }
        if (reservasi.getTipeKamar() == null || reservasi.getTipeKamar().isEmpty()) {
            throw new InvalidReservationException("Tipe kamar harus dipilih!");
        }
        if (reservasi.getTanggalCheckIn() == null || reservasi.getTanggalCheckOut() == null) {
            throw new InvalidReservationException("Tanggal Check-In dan Check-Out harus diisi!");
        }

        // 2. Validasi Logika Tanggal (Check-Out harus setelah Check-In)
        if (reservasi.getTanggalCheckOut().isBefore(reservasi.getTanggalCheckIn())) {
            throw new InvalidReservationException("Tanggal Check-Out tidak boleh mendahului tanggal Check-In!");
        }
        
        if (reservasi.getTanggalCheckOut().isEqual(reservasi.getTanggalCheckIn())) {
            throw new InvalidReservationException("Reservasi minimal harus 1 malam (tanggal Check-In dan Check-Out tidak boleh sama)!");
        }

        // 3. Validasi Kapasitas Kamar (Exception Handling untuk Kamar Penuh)
        String tipe = reservasi.getTipeKamar();
        int maxLimit = KOTA_KAMAR_LIMITS.getOrDefault(tipe, 5); // Default limit 5 jika tidak terdefinisi
        long activeReservations = reservasiRepository.countByTipeKamar(tipe);

        if (activeReservations >= maxLimit) {
            throw new KamarPenuhException("Kamar bertipe '" + tipe + "' sudah penuh! Kapasitas maksimal hanya " + maxLimit + " unit.");
        }

        // 4. Simpan ke database jika lolos semua validasi
        return reservasiRepository.save(reservasi);
    }

    /**
     * Mendapatkan kuota maksimal untuk masing-masing tipe kamar.
     */
    public Map<String, Integer> getRoomLimits() {
        return KOTA_KAMAR_LIMITS;
    }
}
