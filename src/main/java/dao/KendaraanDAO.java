/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Kendaraan;
/**
 *
 * @author ASUS
 */
public interface KendaraanDAO {
    void tambah(Kendaraan kendaraan);
    Kendaraan cariById(int id);
    Kendaraan cariByPlat(String nomorPlat);
    List<Kendaraan> getAll();
}