/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.KendaraanDAO;
import dao.ParkirDAO;
import dao.PetugasDAO;
import dao.SlotParkirDAO;
import java.util.List;
import model.Kendaraan;
import model.Parkir;
import model.Petugas;
import model.SlotParkir;

/**
 *
 * @author ASUS
 */
public class ParkirService {
    private final PetugasDAO petugasDAO;
    private final KendaraanDAO kendaraanDAO;
    private final SlotParkirDAO slotDAO;
    private final ParkirDAO parkirDAO;

    public ParkirService(PetugasDAO petugasDAO, KendaraanDAO kendaraanDAO,
                         SlotParkirDAO slotDAO, ParkirDAO parkirDAO) {
        this.petugasDAO = petugasDAO;
        this.kendaraanDAO = kendaraanDAO;
        this.slotDAO = slotDAO;
        this.parkirDAO = parkirDAO;
    }

    public Petugas login(String nama, String password) {
        return petugasDAO.login(nama, password);
    }

    //Kendaraan
    public void tambahKendaraan(Kendaraan kendaraan) {
        kendaraanDAO.tambah(kendaraan);
    }

    // Overloading, cari berdasarkan ID
    public Kendaraan cariKendaraan(int id) {
        return kendaraanDAO.cariById(id);
    }

    // Overloading, cari berdasarkan nomor plat
    public Kendaraan cariKendaraan(String nomorPlat) {
        return kendaraanDAO.cariByPlat(nomorPlat);
    }

    //Slot
    public SlotParkir cariSlotKosong(String jenis) {
        return slotDAO.cariSlotKosong(jenis);
    }

    public int hitungSlotKosong(String jenis) {
        return slotDAO.hitungSlotKosong(jenis);
    }

    public int hitungTotalSlot(String jenis) {
        return slotDAO.hitungTotalSlot(jenis);
    }

    //Parkir
    public boolean sedangParkir(Kendaraan kendaraan) {
        return parkirDAO.sedangParkir(kendaraan.getIdKendaraan());
    }

    public void tambahParkir(Parkir parkir) {
        parkir.getSlot().isi();
        parkirDAO.tambah(parkir);
    }

    public List<Parkir> getAllParkir() {
        return parkirDAO.getAll();
    }

    public Parkir cariById(int id) {
        return parkirDAO.cariById(id);
    }

    public void updateParkir(Parkir parkir, String waktuKeluar, String metodePembayaran,
                             int jumlahBayar, int tarif) {
        parkir.setWaktuKeluar(waktuKeluar);
        parkir.setMetodePembayaran(metodePembayaran);
        parkir.setJumlahBayar(jumlahBayar);
        parkir.setStatusParkir(Parkir.SELESAI);
        parkir.setStatusPembayaran(jumlahBayar >= tarif ? "Lunas" : "Belum Lunas");

        //Kendaraan keluar >> slot kosong lagi
        parkir.getSlot().kosongkan();
        parkirDAO.update(parkir);
    }

    public void hapusParkir(Parkir parkir) {
        //Slot dikosongkan hanya jika kendaraan masih parkir
        if (parkir.getStatusParkir().equals(Parkir.MASIH_PARKIR)) {
            parkir.getSlot().kosongkan();
        }
        parkirDAO.hapus(parkir);
    }
}