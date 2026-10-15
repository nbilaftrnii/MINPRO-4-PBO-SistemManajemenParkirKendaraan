/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "parkir")
public class Parkir {

    public static final String MASIH_PARKIR = "Masih Parkir";
    public static final String SELESAI = "Selesai";
    public static final DateTimeFormatter FORMAT_WAKTU =
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parkir")
    private int idParkir;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kendaraan", nullable = false)
    private Kendaraan kendaraan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_petugas", nullable = false)
    private Petugas petugas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomor_slot", nullable = false)
    private SlotParkir slot;

    @Column(name = "waktu_masuk", nullable = false)
    private String waktuMasuk;

    @Column(name = "waktu_keluar")
    private String waktuKeluar;

    @Column(name = "status_parkir", nullable = false)
    private String statusParkir;

    @Column(name = "metode_pembayaran", nullable = false)
    private String metodePembayaran;

    @Column(name = "jumlah_bayar", nullable = false)
    private double jumlahBayar;

    @Column(name = "status_pembayaran", nullable = false)
    private String statusPembayaran;

    //Constructor kosong 
    protected Parkir() {
    }

    //Constructor yang digunakan program
    public Parkir(Kendaraan kendaraan, Petugas petugas,
                  SlotParkir slot, String waktuMasuk) {

        this.kendaraan = kendaraan;
        this.petugas = petugas;
        this.slot = slot;
        this.waktuMasuk = waktuMasuk;
        this.waktuKeluar = "-";
        this.statusParkir = MASIH_PARKIR;
        this.metodePembayaran = "-";
        this.jumlahBayar = 0;
        this.statusPembayaran = "Belum Bayar";
    }

    //auto increment
    public void setIdParkir(int idParkir) {
        this.idParkir = idParkir;
    }

    public int getIdParkir() {
        return idParkir;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public SlotParkir getSlot() {
        return slot;
    }

    public String getWaktuMasuk() {
        return waktuMasuk;
    }

    public String getWaktuKeluar() {
        return waktuKeluar;
    }

    public String getStatusParkir() {
        return statusParkir;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setWaktuKeluar(String waktuKeluar) {
        this.waktuKeluar = waktuKeluar;
    }

    public void setStatusParkir(String statusParkir) {
        this.statusParkir = statusParkir;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public double hitungTarif(int lamaJam) {
        return kendaraan.hitungTarif(lamaJam);
    }

    public String hitungWaktuKeluar(int lamaJam) {
        LocalDateTime waktuMasukDate =
                LocalDateTime.parse(waktuMasuk, FORMAT_WAKTU);

        LocalDateTime waktuKeluarDate =
                waktuMasukDate.plusHours(lamaJam);

        return waktuKeluarDate.format(FORMAT_WAKTU);
    }

    public void tampilkanInfo() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       🚗 Data on MY PARKIR GW 🚗");
        System.out.println("========================================");
        System.out.printf("%-20s: %d%n", "ID Parkir", idParkir);
        System.out.printf("%-20s: %s%n",
                "Nomor Plat", kendaraan.getNomorPlat());
        System.out.printf("%-20s: %s%n",
                "Jenis Kendaraan", kendaraan.getJenisKendaraan());
        System.out.printf("%-20s: %s%n",
                "Merk", kendaraan.getMerk());
        System.out.printf("%-20s: %s%n",
                "Warna", kendaraan.getWarna());
        System.out.printf("%-20s: %s%n",
                "Slot", slot.getNomorSlot());
        System.out.printf("%-20s: %s%n",
                "Petugas", petugas.getNamaPetugas());
        System.out.printf("%-20s: %s%n",
                "Waktu Masuk", waktuMasuk);
        System.out.printf("%-20s: %s%n",
                "Waktu Keluar", waktuKeluar);
        System.out.printf("%-20s: %s%n",
                "Status Parkir", statusParkir);
        System.out.printf("%-20s: %s%n",
                "Metode Pembayaran", metodePembayaran);
        System.out.printf("%-20s: Rp%.0f%n",
                "Jumlah Bayar", jumlahBayar);
        System.out.printf("%-20s: %s%n",
                "Status Pembayaran", statusPembayaran);
        System.out.println("----------------------------------------");
    }

    public void cetakStruk(int lamaJam) {
        int tarif = (int) hitungTarif(lamaJam);
        int bayar = (int) jumlahBayar;
        int kembalian = Math.max(0, bayar - tarif);
 
        System.out.println();
        System.out.println("========================================");
        System.out.println("              💰 Receipt");
        System.out.println("             MY PARKIR GW");
        System.out.println("========================================");
        System.out.println("ID Parkir        : " + idParkir);
        System.out.println("Nomor Plat       : " + kendaraan.getNomorPlat());
        System.out.println("Jenis Kendaraan  : " + kendaraan.getJenisKendaraan());
        System.out.println("Slot             : " + slot.getNomorSlot());
        System.out.println("Petugas          : " + petugas.getNamaPetugas());
        System.out.println("----------------------------------------");
        System.out.println("Waktu Masuk      : " + waktuMasuk);
        System.out.println("Waktu Keluar     : " + waktuKeluar);
        System.out.println("Lama Parkir      : " + lamaJam + " jam");
        System.out.println("----------------------------------------");
        System.out.println("Total Tarif      : Rp" + tarif);
        System.out.println("Metode Pembayaran: " + metodePembayaran);
        System.out.println("Jumlah Bayar     : Rp" + bayar);
        System.out.println("Kembalian        : Rp" + kembalian);
        System.out.println("Status Pembayaran: " + statusPembayaran);
        System.out.println("========================================");
        System.out.println("              Thank You");
        System.out.println("         HATI-HATI DI JALAN!");
        System.out.println("========================================");
    }
}