/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jdbc;

import config.DBConnection;
import dao.SlotParkirDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SlotParkir;
/**
 *
 * @author ASUS
 */
public class SlotParkirDAOJDBC implements SlotParkirDAO {
    @Override
    public SlotParkir cariSlotKosong(String jenis) {
        String sql = "SELECT * FROM slot_parkir "
                + "WHERE jenis_slot = ? AND status_slot = ? "
                + "ORDER BY nomor_slot LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jenis);
            ps.setString(2, SlotParkir.KOSONG);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatSlot(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari slot kosong: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public int hitungSlotKosong(String jenis) {
        String sql = "SELECT COUNT(*) AS jumlah FROM slot_parkir "
                + "WHERE jenis_slot = ? AND status_slot = ?";
        return hitung(sql, jenis, SlotParkir.KOSONG);
    }

    @Override
    public int hitungTotalSlot(String jenis) {
        String sql = "SELECT COUNT(*) AS jumlah FROM slot_parkir WHERE jenis_slot = ?";
        return hitung(sql, jenis, null);
    }

    private int hitung(String sql, String jenis, String status) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jenis);
            if (status != null) {
                ps.setString(2, status);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("jumlah");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghitung slot: " + e.getMessage(), e);
        }

        return 0;
    }

    @Override
    public List<SlotParkir> getAll() {
        List<SlotParkir> daftar = new ArrayList<>();
        String sql = "SELECT * FROM slot_parkir ORDER BY nomor_slot";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(buatSlot(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data slot: " + e.getMessage(), e);
        }

        return daftar;
    }

    private SlotParkir buatSlot(ResultSet rs) throws SQLException {
        SlotParkir slot = new SlotParkir(
                rs.getString("nomor_slot"),
                rs.getString("jenis_slot"));
        slot.setStatusSlot(rs.getString("status_slot"));
        return slot;
    }
}