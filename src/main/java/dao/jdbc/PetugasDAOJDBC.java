/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jdbc;

import config.DBConnection;
import dao.PetugasDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Petugas;
/**
 *
 * @author ASUS
 */
public class PetugasDAOJDBC implements PetugasDAO {

    @Override
    public Petugas login(String nama, String password) {
        String sql = "SELECT id_petugas, nama_petugas, password FROM petugas "
                + "WHERE nama_petugas = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Petugas(
                            rs.getInt("id_petugas"),
                            rs.getString("nama_petugas"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data petugas: " + e.getMessage(), e);
        }

        return null;
    }
}