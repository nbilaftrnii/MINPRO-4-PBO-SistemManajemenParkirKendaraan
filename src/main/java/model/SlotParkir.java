/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 *
 * @author ASUS
 */

@Entity
@Table(name = "slot_parkir")
public class SlotParkir {

    public static final String KOSONG = "Kosong";
    public static final String TERISI = "Terisi";

    @Id
    @Column(name = "nomor_slot")
    private String nomorSlot;

    @Column(name = "jenis_slot", nullable = false)
    private String jenisSlot;

    @Column(name = "status_slot", nullable = false)
    private String statusSlot;

    // Constructor kosong
    protected SlotParkir() {
    }

    // Constructor yang digunakan program
    public SlotParkir(String nomorSlot, String jenisSlot) {
        this.nomorSlot = nomorSlot;
        this.jenisSlot = jenisSlot;
        this.statusSlot = KOSONG;
    }

    public String getNomorSlot() {
        return nomorSlot;
    }

    public String getJenisSlot() {
        return jenisSlot;
    }

    public String getStatusSlot() {
        return statusSlot;
    }

    public boolean isKosong() {
        return KOSONG.equals(statusSlot);
    }

    public void isi() {
        statusSlot = TERISI;
    }

    public void kosongkan() {
        statusSlot = KOSONG;
    }

    public void setStatusSlot(String statusSlot) {
        if (!KOSONG.equals(statusSlot) && !TERISI.equals(statusSlot)) {
            throw new IllegalArgumentException(
                    "Status slot hanya boleh Kosong atau Terisi!"
            );
        }

        this.statusSlot = statusSlot;
    }
}