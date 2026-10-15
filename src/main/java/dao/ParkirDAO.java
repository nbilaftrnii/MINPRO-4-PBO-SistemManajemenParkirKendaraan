/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Parkir;
/**
 *
 * @author ASUS
 */
public interface ParkirDAO {
    void tambah(Parkir parkir);
    List<Parkir> getAll();
    Parkir cariById(int id);

    boolean sedangParkir(int idKendaraan);
    void update(Parkir parkir);
    void hapus(Parkir parkir);
}