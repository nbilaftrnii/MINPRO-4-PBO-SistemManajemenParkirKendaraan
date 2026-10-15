/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 *
 * @author ASUS
 */
public class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("ParkirPU");

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}