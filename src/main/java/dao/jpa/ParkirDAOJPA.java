/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jpa;

import config.JPAUtil;
import dao.ParkirDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;
import model.Parkir;
/**
 *
 * @author ASUS
 */
public class ParkirDAOJPA implements ParkirDAO {
    private void transaksi(String pesanGagal, Consumer<EntityManager> aksi) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            aksi.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(pesanGagal + ": " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void tambah(Parkir parkir) {
        transaksi("Gagal menambahkan data parkir", em -> {
            em.merge(parkir.getSlot());                
            Parkir tersimpan = em.merge(parkir);       
            em.flush();
            parkir.setIdParkir(tersimpan.getIdParkir());
        });
    }

    @Override
    public List<Parkir> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Parkir p "
                    + "JOIN FETCH p.kendaraan "
                    + "JOIN FETCH p.petugas "
                    + "JOIN FETCH p.slot "
                    + "ORDER BY p.idParkir", Parkir.class)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mengambil data parkir: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Parkir cariById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Parkir.class, id);   
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mencari data parkir: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean sedangParkir(int idKendaraan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long jumlah = em.createQuery(
                    "SELECT COUNT(p) FROM Parkir p "
                    + "WHERE p.kendaraan.idKendaraan = :id AND p.statusParkir = :status",
                    Long.class)
                    .setParameter("id", idKendaraan)
                    .setParameter("status", Parkir.MASIH_PARKIR)
                    .getSingleResult();
            return jumlah > 0;
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mengecek status parkir: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Parkir parkir) {
        transaksi("Gagal mengupdate data parkir", em -> {
            em.merge(parkir.getSlot());   
            em.merge(parkir);            
        });
    }

    @Override
    public void hapus(Parkir parkir) {
        transaksi("Gagal menghapus data parkir", em -> {
            Parkir terkelola = em.find(Parkir.class, parkir.getIdParkir());
            if (terkelola != null) {
                em.remove(terkelola);    
            }
            em.merge(parkir.getSlot());   // status slot sesuai kondisi terakhir
        });
    }
}