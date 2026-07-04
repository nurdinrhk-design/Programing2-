package com.unpam.reservasihotel.controller;

import com.unpam.reservasihotel.exception.InvalidReservationException;
import com.unpam.reservasihotel.exception.KamarPenuhException;
import com.unpam.reservasihotel.model.Reservasi;
import com.unpam.reservasihotel.service.ReservasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservasiController {

    private final ReservasiService reservasiService;

    @Autowired
    public ReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    /**
     * Halaman Dashboard Utama.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Aplikasi Reservasi Hotel - Dashboard");
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("totalReservasi", reservasiService.getAllReservations().size());
        model.addAttribute("limits", reservasiService.getRoomLimits());
        return "index";
    }

    /**
     * Halaman Form Input Reservasi.
     */
    @GetMapping("/reservasi/baru")
    public String showForm(Model model) {
        model.addAttribute("title", "Form Reservasi Baru");
        model.addAttribute("activePage", "baru");
        model.addAttribute("reservasi", new Reservasi());
        return "form";
    }

    /**
     * Memproses Penyimpanan Reservasi dengan Exception Handling.
     */
    @PostMapping("/reservasi/simpan")
    public String saveReservation(@ModelAttribute("reservasi") Reservasi reservasi, Model model) {
        try {
            // Panggil service untuk memproses bisnis logic dan menyimpannya ke database
            reservasiService.saveReservation(reservasi);
            
            // Jika sukses, arahkan ke daftar reservasi dengan flash success
            return "redirect:/reservasi/daftar?success=true";
        } catch (KamarPenuhException | InvalidReservationException e) {
            // Tangkap exception kustom untuk ditampilkan ke user
            model.addAttribute("title", "Form Reservasi Baru");
            model.addAttribute("activePage", "baru");
            model.addAttribute("error", e.getMessage());
            // Kembalikan form dengan data yang sebelumnya diisi agar tidak terhapus
            return "form";
        } catch (Exception e) {
            // Tangkap exception umum database atau sistem
            model.addAttribute("title", "Form Reservasi Baru");
            model.addAttribute("activePage", "baru");
            model.addAttribute("error", "Terjadi kesalahan internal: " + e.getMessage());
            return "form";
        }
    }

    /**
     * Halaman Hasilkan List Output Reservasi.
     */
    @GetMapping("/reservasi/daftar")
    public String showList(Model model) {
        model.addAttribute("title", "Daftar Reservasi Hotel");
        model.addAttribute("activePage", "daftar");
        model.addAttribute("daftarReservasi", reservasiService.getAllReservations());
        return "list";
    }
}
