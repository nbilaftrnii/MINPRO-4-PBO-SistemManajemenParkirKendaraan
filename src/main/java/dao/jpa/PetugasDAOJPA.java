/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.jpa;

import config.JPAUtil;
import dao.PetugasDAO;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.Petugas;
/**
 *
 * @author ASUS
 */
public class PetugasDAOJPA implements PetugasDAO {
    @Override
    public Petugas login(String nama, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Petugas> hasil = em.createQuery(
                    "SELECT p FROM Petugas p "
                    + "WHERE p.namaPetugas = :nama AND p.password = :pw", Petugas.class)
                    .setParameter("nama", nama.trim())
                    .setParameter("pw", password)
                    .setMaxResults(1)
                    .getResultList();
            return hasil.isEmpty() ? null : hasil.get(0);
        } catch (RuntimeException e) {
            throw new RuntimeException("Gagal mengambil data petugas: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}