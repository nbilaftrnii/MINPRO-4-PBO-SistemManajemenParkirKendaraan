/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import config.JPAUtil;
import controller.ParkirController;
import dao.KendaraanDAO;
import dao.ParkirDAO;
import dao.PetugasDAO;
import dao.SlotParkirDAO;
import dao.jdbc.KendaraanDAOJDBC;
import dao.jdbc.ParkirDAOJDBC;
import dao.jdbc.PetugasDAOJDBC;
import dao.jdbc.SlotParkirDAOJDBC;
import dao.jpa.KendaraanDAOJPA;
import dao.jpa.ParkirDAOJPA;
import dao.jpa.PetugasDAOJPA;
import dao.jpa.SlotParkirDAOJPA;
import java.util.Scanner;
import service.ParkirService;
import view.ParkirView;

/**
 *
 * @author ASUS
 */
public class Main {
    private static final String MODE = "JDBC";
    private static ParkirService buatService() {
        if (MODE.equals("JPA")) {
            return new ParkirService(new PetugasDAOJPA(), new KendaraanDAOJPA(),
                    new SlotParkirDAOJPA(), new ParkirDAOJPA());
        }
        return new ParkirService(new PetugasDAOJDBC(), new KendaraanDAOJDBC(),
                new SlotParkirDAOJDBC(), new ParkirDAOJDBC());
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            ParkirService service = buatService();
            ParkirView view = new ParkirView(input);
            ParkirController controller = new ParkirController(service, view);
            
            controller.jalankan();
        } catch (RuntimeException | ExceptionInInitializerError e) {
            System.out.println("Program berhenti karena error database: " + e.getMessage());
            System.out.println("Pastikan MySQL menyala dan db_parkir.sql sudah di-import.");
        } finally {
            input.close();
            if (MODE.equals("JPA")) {
                try {
                    JPAUtil.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }
}