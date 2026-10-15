/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jdbc;

import config.DBConnection;
import dao.KendaraanDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Kendaraan;
import model.Mobil;
import model.Motor;
/**
 *
 * @author ASUS
 */
public class KendaraanDAOJDBC implements KendaraanDAO {
    @Override
    public void tambah(Kendaraan kendaraan) {
        String sql = "INSERT INTO kendaraan "
                + "(nomor_plat, merk, warna, jenis_kendaraan, kapasitas_cc, jumlah_pintu) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, kendaraan.getNomorPlat());
            ps.setString(2, kendaraan.getMerk());
            ps.setString(3, kendaraan.getWarna());
            ps.setString(4, kendaraan.getJenisKendaraan());

            if (kendaraan instanceof Motor) {
                ps.setInt(5, ((Motor) kendaraan).getKapasitasCC());
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setInt(6, ((Mobil) kendaraan).getJumlahPintu());
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    kendaraan.setIdKendaraan(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambahkan kendaraan: " + e.getMessage(), e);
        }
    }

    @Override
    public Kendaraan cariById(int id) {
        String sql = "SELECT * FROM kendaraan WHERE id_kendaraan = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatKendaraan(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari kendaraan: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Kendaraan cariByPlat(String nomorPlat) {
        String sql = "SELECT * FROM kendaraan WHERE nomor_plat = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomorPlat.trim().toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatKendaraan(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari kendaraan: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<Kendaraan> getAll() {
        List<Kendaraan> daftar = new ArrayList<>();
        String sql = "SELECT * FROM kendaraan ORDER BY id_kendaraan";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(buatKendaraan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data kendaraan: " + e.getMessage(), e);
        }

        return daftar;
    }

    private Kendaraan buatKendaraan(ResultSet rs) throws SQLException {
        Kendaraan kendaraan;

        if (rs.getString("jenis_kendaraan").equalsIgnoreCase("Motor")) {
            kendaraan = new Motor(
                    rs.getString("nomor_plat"),
                    rs.getString("merk"),
                    rs.getString("warna"),
                    rs.getInt("kapasitas_cc"));
        } else {
            kendaraan = new Mobil(
                    rs.getString("nomor_plat"),
                    rs.getString("merk"),
                    rs.getString("warna"),
                    rs.getInt("jumlah_pintu"));
        }
        kendaraan.setIdKendaraan(rs.getInt("id_kendaraan"));
        return kendaraan;
    }
}