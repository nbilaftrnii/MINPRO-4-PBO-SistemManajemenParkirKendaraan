/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Petugas;
/**
 *
 * @author ASUS
 */
public interface PetugasDAO {
    Petugas login(String nama, String password);
}