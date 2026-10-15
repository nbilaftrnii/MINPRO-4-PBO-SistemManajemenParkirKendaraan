/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jpa;

import config.JPAUtil;
import dao.KendaraanDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import model.Kendaraan;
/**
 *
 * @author ASUS
 */
public class KendaraanDAOJPA implements KendaraanDAO {
    @Override
    public void tambah(Kendaraan kendaraan) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(kendaraan);   
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Gagal menambahkan kendaraan: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Kendaraan cariById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Kendaraan.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mencari kendaraan: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Kendaraan cariByPlat(String nomorPlat) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Kendaraan> hasil = em.createQuery(
                    "SELECT k FROM Kendaraan k WHERE k.nomorPlat = :plat", Kendaraan.class)
                    .setParameter("plat", nomorPlat.trim().toUpperCase())
                    .setMaxResults(1)
                    .getResultList();
            return hasil.isEmpty() ? null : hasil.get(0);
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mencari kendaraan: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Kendaraan> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT k FROM Kendaraan k ORDER BY k.idKendaraan", Kendaraan.class)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mengambil data kendaraan: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}