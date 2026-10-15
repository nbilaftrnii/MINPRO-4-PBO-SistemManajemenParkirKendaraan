/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "kendaraan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "jenis_kendaraan")
public abstract class Kendaraan implements Bertarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kendaraan")
    private int idKendaraan;

    @Column(name = "nomor_plat", nullable = false, unique = true)
    private String nomorPlat;

    @Column(name = "merk", nullable = false)
    private String merk;

    @Column(name = "warna", nullable = false)
    private String warna;

    //Constructor kosong 
    protected Kendaraan() {
    }

    //Constructor yang dipakai program
    public Kendaraan(String nomorPlat, String merk, String warna) {
        setNomorPlat(nomorPlat);
        setMerk(merk);
        setWarna(warna);
    }

    public int getIdKendaraan() {
        return idKendaraan;
    }

    //auto increment
    public void setIdKendaraan(int idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public String getNomorPlat() {
        return nomorPlat;
    }

    public String getMerk() {
        return merk;
    }

    public String getWarna() {
        return warna;
    }

    public void setNomorPlat(String nomorPlat) {
        this.nomorPlat = wajibIsi(nomorPlat, "Nomor plat").toUpperCase();
    }

    public void setMerk(String merk) {
        this.merk = wajibIsi(merk, "Merk");
    }

    public void setWarna(String warna) {
        this.warna = wajibIsi(warna, "Warna");
    }

    private static String wajibIsi(String nilai, String namaData) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaData + " tidak boleh kosong!");
        }

        return nilai.trim();
    }

    public abstract String getJenisKendaraan();

    protected abstract double getTarifPerJam();

    protected abstract double getTarifMaksimal();

    @Override
    public double hitungTarif(int lamaJam) {
        return Math.min(
                getTarifPerJam() * lamaJam,
                getTarifMaksimal()
        );
    }

    public void tampilkanInfo() {
        System.out.printf("%-17s: %d%n", "ID Kendaraan", idKendaraan);
        System.out.printf("%-17s: %s%n", "Nomor Plat", nomorPlat);
        System.out.printf("%-17s: %s%n", "Jenis Kendaraan", getJenisKendaraan());
        System.out.printf("%-17s: %s%n", "Merk", merk);
        System.out.printf("%-17s: %s%n", "Warna", warna);
    }
}