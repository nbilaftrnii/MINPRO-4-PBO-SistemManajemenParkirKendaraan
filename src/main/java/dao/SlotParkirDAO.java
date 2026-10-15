/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.SlotParkir;

/**
 *
 * @author ASUS
 */
public interface SlotParkirDAO {
    SlotParkir cariSlotKosong(String jenis);
    int hitungSlotKosong(String jenis);
    int hitungTotalSlot(String jenis);

    List<SlotParkir> getAll();
}