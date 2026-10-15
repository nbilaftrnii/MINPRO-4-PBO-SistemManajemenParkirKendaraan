/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jdbc;

import config.DBConnection;
import dao.ParkirDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Kendaraan;
import model.Mobil;
import model.Motor;
import model.Parkir;
import model.Petugas;
import model.SlotParkir;
/**
 *
 * @author ASUS
 */
public class ParkirDAOJDBC implements ParkirDAO {
    private static final String SELECT_PARKIR =
            "SELECT p.*, "
            + "k.nomor_plat, k.merk, k.warna, k.jenis_kendaraan, "
            + "k.kapasitas_cc, k.jumlah_pintu, "
            + "pt.nama_petugas, pt.password, "
            + "s.jenis_slot, s.status_slot "
            + "FROM parkir p "
            + "JOIN kendaraan k ON p.id_kendaraan = k.id_kendaraan "
            + "JOIN petugas pt ON p.id_petugas = pt.id_petugas "
            + "JOIN slot_parkir s ON p.nomor_slot = s.nomor_slot ";

    @FunctionalInterface
    private interface Aksi {
        void jalankan(Connection conn) throws SQLException;
    }

    private void transaksi(String pesanGagal, Aksi aksi) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                aksi.jalankan(conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(pesanGagal + ": " + e.getMessage(), e);
        }
    }

    private void sinkronStatusSlot(Connection conn, SlotParkir slot) throws SQLException {
        String sql = "UPDATE slot_parkir SET status_slot = ? WHERE nomor_slot = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slot.getStatusSlot());
            ps.setString(2, slot.getNomorSlot());
            ps.executeUpdate();
        }
    }

    @Override
    public void tambah(Parkir parkir) {
        String sql = "INSERT INTO parkir "
                + "(id_kendaraan, id_petugas, nomor_slot, waktu_masuk, waktu_keluar, "
                + "status_parkir, metode_pembayaran, jumlah_bayar, status_pembayaran) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        transaksi("Gagal menambahkan data parkir", conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, parkir.getKendaraan().getIdKendaraan());
                ps.setInt(2, parkir.getPetugas().getIdPetugas());
                ps.setString(3, parkir.getSlot().getNomorSlot());
                ps.setString(4, parkir.getWaktuMasuk());
                ps.setString(5, parkir.getWaktuKeluar());
                ps.setString(6, parkir.getStatusParkir());
                ps.setString(7, parkir.getMetodePembayaran());
                ps.setDouble(8, parkir.getJumlahBayar());
                ps.setString(9, parkir.getStatusPembayaran());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        parkir.setIdParkir(keys.getInt(1));
                    }
                }
            }
            sinkronStatusSlot(conn, parkir.getSlot());
        });
    }

    @Override
    public List<Parkir> getAll() {
        List<Parkir> daftar = new ArrayList<>();
        String sql = SELECT_PARKIR + "ORDER BY p.id_parkir";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(buatParkir(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data parkir: " + e.getMessage(), e);
        }

        return daftar;
    }

    @Override
    public Parkir cariById(int id) {
        String sql = SELECT_PARKIR + "WHERE p.id_parkir = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatParkir(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari data parkir: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public boolean sedangParkir(int idKendaraan) {
        String sql = "SELECT COUNT(*) AS jumlah FROM parkir "
                + "WHERE id_kendaraan = ? AND status_parkir = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKendaraan);
            ps.setString(2, Parkir.MASIH_PARKIR);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("jumlah") > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengecek status parkir: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Parkir parkir) {
        String sql = "UPDATE parkir SET waktu_keluar = ?, status_parkir = ?, "
                + "metode_pembayaran = ?, jumlah_bayar = ?, status_pembayaran = ? "
                + "WHERE id_parkir = ?";

        transaksi("Gagal mengupdate data parkir", conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, parkir.getWaktuKeluar());
                ps.setString(2, parkir.getStatusParkir());
                ps.setString(3, parkir.getMetodePembayaran());
                ps.setDouble(4, parkir.getJumlahBayar());
                ps.setString(5, parkir.getStatusPembayaran());
                ps.setInt(6, parkir.getIdParkir());
                ps.executeUpdate();
            }
            sinkronStatusSlot(conn, parkir.getSlot());
        });
    }

    @Override
    public void hapus(Parkir parkir) {
        String sql = "DELETE FROM parkir WHERE id_parkir = ?";

        transaksi("Gagal menghapus data parkir", conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, parkir.getIdParkir());
                ps.executeUpdate();
            }
            sinkronStatusSlot(conn, parkir.getSlot());
        });
    }

    private Parkir buatParkir(ResultSet rs) throws SQLException {
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

        Petugas petugas = new Petugas(
                rs.getInt("id_petugas"),
                rs.getString("nama_petugas"),
                rs.getString("password"));

        SlotParkir slot = new SlotParkir(
                rs.getString("nomor_slot"),
                rs.getString("jenis_slot"));
        slot.setStatusSlot(rs.getString("status_slot"));

        Parkir parkir = new Parkir(kendaraan, petugas, slot, rs.getString("waktu_masuk"));
        parkir.setIdParkir(rs.getInt("id_parkir"));

        String waktuKeluar = rs.getString("waktu_keluar");
        parkir.setWaktuKeluar(waktuKeluar == null ? "-" : waktuKeluar);
        parkir.setStatusParkir(rs.getString("status_parkir"));
        parkir.setMetodePembayaran(rs.getString("metode_pembayaran"));
        parkir.setJumlahBayar(rs.getDouble("jumlah_bayar"));
        parkir.setStatusPembayaran(rs.getString("status_pembayaran"));

        return parkir;
    }
}