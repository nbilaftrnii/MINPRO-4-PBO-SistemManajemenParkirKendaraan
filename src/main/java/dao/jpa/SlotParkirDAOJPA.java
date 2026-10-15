/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jpa;

import config.JPAUtil;
import dao.SlotParkirDAO;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.SlotParkir;
/**
 *
 * @author ASUS
 */
public class SlotParkirDAOJPA implements SlotParkirDAO {
    @Override
    public SlotParkir cariSlotKosong(String jenis) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<SlotParkir> hasil = em.createQuery(
                    "SELECT s FROM SlotParkir s "
                    + "WHERE s.jenisSlot = :jenis AND s.statusSlot = :status "
                    + "ORDER BY s.nomorSlot", SlotParkir.class)
                    .setParameter("jenis", jenis)
                    .setParameter("status", SlotParkir.KOSONG)
                    .setMaxResults(1)
                    .getResultList();
            return hasil.isEmpty() ? null : hasil.get(0);
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mencari slot kosong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public int hitungSlotKosong(String jenis) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long jumlah = em.createQuery(
                    "SELECT COUNT(s) FROM SlotParkir s "
                    + "WHERE s.jenisSlot = :jenis AND s.statusSlot = :status", Long.class)
                    .setParameter("jenis", jenis)
                    .setParameter("status", SlotParkir.KOSONG)
                    .getSingleResult();
            return jumlah.intValue();
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal menghitung slot kosong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public int hitungTotalSlot(String jenis) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long jumlah = em.createQuery(
                    "SELECT COUNT(s) FROM SlotParkir s WHERE s.jenisSlot = :jenis", Long.class)
                    .setParameter("jenis", jenis)
                    .getSingleResult();
            return jumlah.intValue();
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal menghitung total slot: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<SlotParkir> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM SlotParkir s ORDER BY s.nomorSlot", SlotParkir.class)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mengambil data slot: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}