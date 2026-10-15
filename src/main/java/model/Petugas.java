/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 *
 * @author ASUS
 */

//Hanya petugas yang boleh login ke sistem
@Entity
@Table(name = "petugas")
public class Petugas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_petugas")
    private int idPetugas;

    @Column(name = "nama_petugas", nullable = false)
    private String namaPetugas;

    @Column(name = "password", nullable = false)
    private String password;

    protected Petugas() {
    }

    public Petugas(String namaPetugas, String password) {
        setNamaPetugas(namaPetugas);
        setPassword(password);
    }

    public Petugas(int idPetugas, String namaPetugas, String password) {
        this.idPetugas = idPetugas;
        setNamaPetugas(namaPetugas);
        setPassword(password);
    }

    public int getIdPetugas() {
        return idPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        if (namaPetugas == null || namaPetugas.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nama petugas tidak boleh kosong!"
            );
        }

        this.namaPetugas = namaPetugas.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Password tidak boleh kosong!"
            );
        }

        this.password = password;
    }

    public boolean cekLogin(String nama, String password) {
        return namaPetugas.equals(nama.trim())
                && this.password.equals(password);
    }
}