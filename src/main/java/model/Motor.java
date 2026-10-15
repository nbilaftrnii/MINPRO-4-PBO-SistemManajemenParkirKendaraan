/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
/**
 *
 * @author ASUS
 */
@Entity
@DiscriminatorValue("Motor")
public class Motor extends Kendaraan {
    private static final double TARIF_PER_JAM = 2000;
    private static final double TARIF_MAKSIMAL = 20000;

    @Column(name = "kapasitas_cc")
    private int kapasitasCC;

    //Constructor kosong
    protected Motor() {
    }

    //Constructor yang digunakan program
    public Motor(String nomorPlat, String merk, String warna, int kapasitasCC) {
        super(nomorPlat, merk, warna);
        setKapasitasCC(kapasitasCC);
    }

    public int getKapasitasCC() {
        return kapasitasCC;
    }

    public void setKapasitasCC(int kapasitasCC) {
        if (kapasitasCC < 50 || kapasitasCC > 2000) {
            throw new IllegalArgumentException(
                    "Kapasitas mesin motor harus 50-2000 cc!"
            );
        }

        this.kapasitasCC = kapasitasCC;
    }

    @Override
    public String getJenisKendaraan() {
        return "Motor";
    }

    @Override
    protected double getTarifPerJam() {
        return TARIF_PER_JAM;
    }

    @Override
    protected double getTarifMaksimal() {
        return TARIF_MAKSIMAL;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("%-17s: %d cc%n", "Kapasitas CC", kapasitasCC);
    }
}