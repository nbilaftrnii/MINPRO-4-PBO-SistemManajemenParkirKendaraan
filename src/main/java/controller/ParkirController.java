/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Kendaraan;
import model.Motor;
import model.Mobil;
import model.Petugas;
import model.SlotParkir;
import model.Parkir;
import service.ParkirService;
import view.ParkirView;
/**
 *
 * @author ASUS
 */
public class ParkirController {
    private static final int MAKS_PERCOBAAN_LOGIN = 3;
    private final ParkirService service;
    private final ParkirView view;
    private Petugas petugasAktif;

    public ParkirController(ParkirService service, ParkirView view) {
        this.service = service;
        this.view = view;
    }

    public void jalankan() {
        petugasAktif = login();
        if (petugasAktif == null) {
            view.pesan("Login gagal " + MAKS_PERCOBAAN_LOGIN + " kali. Program ditutup.");
            return;
        }

        boolean berjalan = true;
        while (berjalan) {
            view.tampilkanMenu(petugasAktif.getNamaPetugas());
            tampilkanRingkasanSlot();

            int menu = view.inputIntPositif("Pilih menu: ");
            try {
                switch (menu) {
                    case 1:
                        masukParkir();
                        break;
                    case 2:
                        view.tampilkanParkir(service.getAllParkir());
                        break;
                    case 3:
                        keluarParkir();
                        break;
                    case 4:
                        hapusParkir();
                        break;
                    case 5:
                        cariParkir();
                        break;
                    case 6:
                        berjalan = false;
                        view.pesan("Sampai jumpa, " + petugasAktif.getNamaPetugas() + "!");
                        break;
                    default:
                        view.pesan("Menu hanya 1 sampai 6!");
                }
            } catch (RuntimeException e) {
                view.pesan("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    private Petugas login() {
        view.tampilkanHeaderLogin();
        for (int percobaan = 1; percobaan <= MAKS_PERCOBAAN_LOGIN; percobaan++) {
            String nama = view.inputNamaPetugas();
            String password = view.inputPassword("Masukkan Password: ");

            Petugas petugas = service.login(nama, password);
            if (petugas != null) {
                view.pesan("Login berhasil. Selamat bertugas, " + petugas.getNamaPetugas() + "!");
                return petugas;
            }
            view.pesan("Nama atau password salah! (percobaan " + percobaan + "/" + MAKS_PERCOBAAN_LOGIN + ")");
        }
        return null;
    }

    private void tampilkanRingkasanSlot() {
        view.tampilkanRingkasanSlot(
                service.hitungSlotKosong("Motor"), service.hitungTotalSlot("Motor"),
                service.hitungSlotKosong("Mobil"), service.hitungTotalSlot("Mobil"));
    }

    private void masukParkir() {
        System.out.println("\n=== MASUK PARKIR ===");
        String plat = view.inputTidakKosong("Nomor Plat: ");

        Kendaraan kendaraan = service.cariKendaraan(plat); // overloading, cari berdasarkan plat
        boolean kendaraanBaru = (kendaraan == null);
        String jenis;

        if (kendaraanBaru) {
            jenis = view.inputJenisKendaraan();
        } else {
            if (service.sedangParkir(kendaraan)) {
                view.pesan("Kendaraan dengan plat ini masih parkir!");
                return;
            }
            jenis = kendaraan.getJenisKendaraan();
            view.pesan("Kendaraan sudah terdaftar (" + jenis + " " + kendaraan.getMerk()
                    + "), data lama dipakai.");
        }

        //Mengecek Slot
        SlotParkir slot = service.cariSlotKosong(jenis);
        if (slot == null) {
            view.pesan("Maaf, slot " + jenis + " sudah penuh!");
            return;
        }

        if (kendaraanBaru) {
            kendaraan = buatKendaraan(plat, jenis);
        }

        String waktuMasuk = view.inputWaktu("Waktu Masuk");

        if (kendaraanBaru) {
            service.tambahKendaraan(kendaraan);
        }

        Parkir parkir = new Parkir(kendaraan, petugasAktif, slot, waktuMasuk);
        service.tambahParkir(parkir);           
        view.pesan("Berhasil! Arahkan kendaraan ke slot " + slot.getNomorSlot()
                + ". ID Parkir: " + parkir.getIdParkir());
    }

    private Kendaraan buatKendaraan(String plat, String jenis) {
        String merk = view.inputMerk();
        String warna = view.inputWarna();

        if (jenis.equals("Motor")) {
            int cc = view.inputIntRentang("Kapasitas mesin (cc, 50-2000): ", 50, 2000);
            return new Motor(plat, merk, warna, cc);
        }
        int pintu = view.inputIntRentang("Jumlah pintu (2-6): ", 2, 6);
        return new Mobil(plat, merk, warna, pintu);
    }

    //Keluar Parkir (update)
    private void keluarParkir() {
        System.out.println("\n=== KELUAR PARKIR ===");
        if (view.tampilkanParkirAktif(service.getAllParkir()) == 0) {
            view.pesan("Tidak ada kendaraan yang sedang parkir.");
            return;
        }

        Parkir parkir = service.cariById(view.inputIntPositif("ID Parkir yang keluar: "));
        if (parkir == null) {
            view.pesan("Data parkir tidak ditemukan!");
            return;
        }
        if (parkir.getStatusParkir().equals(Parkir.SELESAI)) {
            view.pesan("Kendaraan ini sudah keluar dan selesai dibayar.");
            return;
        }

        view.pesan("Waktu masuk  : " + parkir.getWaktuMasuk());
        int lamaJam = view.inputIntPositif("Lama Parkir (jam): ");

        String waktuKeluar = parkir.hitungWaktuKeluar(lamaJam);
        view.pesan("Waktu keluar : " + waktuKeluar + " (otomatis)");

        String metode = view.inputMetodePembayaran();
        int tarif = (int) parkir.hitungTarif(lamaJam);
        view.pesan("Total tarif  : Rp" + tarif);

        int jumlahBayar;
        if (metode.equals("QRIS")) {
            jumlahBayar = tarif;
            view.pesan("Pembayaran QRIS sesuai tarif: Rp" + tarif);
        } else {
            jumlahBayar = view.inputIntPositif("Jumlah Bayar: Rp");
            if (jumlahBayar < tarif) {
                view.pesan("Pembayaran kurang! Data belum dapat diselesaikan.");
                return;
            }
        }

        service.updateParkir(parkir, waktuKeluar, metode, jumlahBayar, tarif);
        view.pesan("Kendaraan berhasil keluar.");
        parkir.cetakStruk(lamaJam);
    }

    //Hapus
    private void hapusParkir() {
        System.out.println("\n=== HAPUS DATA PARKIR ===");
        view.tampilkanParkir(service.getAllParkir());

        Parkir parkir = service.cariById(view.inputIntPositif("ID Parkir yang dihapus: "));
        if (parkir == null) {
            view.pesan("Data parkir tidak ditemukan!");
            return;
        }

        System.out.println("\nData yang akan dihapus:");
        parkir.tampilkanInfo();

        if (view.konfirmasiHapus()) {
            service.hapusParkir(parkir);
            view.pesan("Data parkir berhasil dihapus.");
        } else {
            view.pesan("Penghapusan dibatalkan.");
        }
    }

    //Cari
    private void cariParkir() {
        System.out.println("\n=== CARI DATA PARKIR ===");
        Parkir parkir = service.cariById(view.inputIntPositif("ID Parkir: "));
        if (parkir == null) {
            view.pesan("Data parkir tidak ditemukan!");
            return;
        }
        parkir.tampilkanInfo();
    }
}