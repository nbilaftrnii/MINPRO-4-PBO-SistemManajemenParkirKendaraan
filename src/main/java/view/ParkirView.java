/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.Parkir;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class ParkirView {
    private static final int LEBAR_MENU = 51;
    private final Scanner input;
    public ParkirView(Scanner input) {
        this.input = input;
    }

    //Validasi input
    public int inputIntPositif(String pesan) {
        while (true) {
            try {
                System.out.print(pesan);
                int angka = Integer.parseInt(input.nextLine().trim());
                if (angka > 0) {
                    return angka;
                }
                System.out.println("Input harus lebih dari 0!");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    public int inputIntRentang(String pesan, int min, int max) {
        while (true) {
            int angka = inputIntPositif(pesan);
            if (angka >= min && angka <= max) {
                return angka;
            }
            System.out.println("Input harus antara " + min + " sampai " + max + "!");
        }
    }

    public String inputTidakKosong(String pesan) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine().trim();
            if (!data.isEmpty()) {
                return data;
            }
            System.out.println("Input tidak boleh kosong!");
        }
    }

    private String inputDenganPola(String pesan, String pola, String pesanSalah) {
        while (true) {
            String data = inputTidakKosong(pesan);
            if (data.matches(pola)) {
                return data;
            }
            System.out.println(pesanSalah);
        }
    }

    public String inputPilihan(String label, String... pilihan) {
        String daftar = String.join("/", pilihan);
        while (true) {
            String data = inputTidakKosong(label + " (" + daftar + "): ");
            for (String p : pilihan) {
                if (p.equalsIgnoreCase(data)) {
                    return p;
                }
            }
            System.out.println("Pilihan hanya " + daftar + "!");
        }
    }

    public String inputMerk() {
        return inputDenganPola("Merk: ", ".*[a-zA-Z].*", "Merk harus mengandung huruf!");
    }

    public String inputWarna() {
        return inputDenganPola("Warna: ", "[a-zA-Z ]+", "Warna hanya boleh berisi huruf!");
    }

    public String inputNamaPetugas() {
        return inputDenganPola("Nama Petugas: ", "[a-zA-Z ]+", "Nama hanya boleh berisi huruf!");
    }

    public String inputJenisKendaraan() {
        return inputPilihan("Jenis Kendaraan", "Motor", "Mobil");
    }

    public String inputMetodePembayaran() {
        return inputPilihan("Metode Pembayaran", "Cash", "QRIS");
    }

    public boolean konfirmasiHapus() {
        return inputPilihan("Yakin ingin menghapus data?", "Y", "T").equals("Y");
    }

     public String inputPassword(String pesan) {
        java.io.Console konsol = System.console();
        while (true) {
            String data;
            if (konsol != null) {
                char[] karakter = konsol.readPassword(pesan);
                data = (karakter == null) ? "" : new String(karakter).trim();
            } else {
                System.out.print(pesan);
                data = input.nextLine().trim();
            }
            if (!data.isEmpty()) {
                return data;
            }
            System.out.println("Input tidak boleh kosong!");
        }
    }

    public String inputWaktu(String label) {
        while (true) {
            String waktu = inputTidakKosong(label + " [format DD-MM-YYYY HH:MM, contoh 24-09-2026 17:30]: ");
            try {
                LocalDateTime.parse(waktu, Parkir.FORMAT_WAKTU);
                return waktu;
            } catch (DateTimeParseException e) {
                System.out.println("Format/tanggal salah! Gunakan DD-MM-YYYY HH:MM.");
            }
        }
    }

    //Tampilan
    public void tampilkanHeaderLogin() {
        System.out.println();
        System.out.println("|=========================|");
        System.out.println("|    🚗  MY PARKIR GW 🚗    |");
        System.out.println("|                         |");
        System.out.println("|  --- Petugas Login ---  |");
        System.out.println("|=========================|");
    }

    public void tampilkanMenu(String namaPetugas) {
        System.out.println();
        System.out.println("|===================================================|");
        System.out.println("|                                                   |");
        System.out.println("|                🚗  MY PARKIR GW 🚗                  |");
        System.out.println("|         SISTEM MANAJEMEN PARKIR KENDARAAN         |");
        System.out.println("|                                                   |");
        System.out.println("|           ------- Smart Parking  -------          |");
        System.out.println("|                                                   |");
        System.out.println("|===================================================|");
        System.out.println("------------------- MENU UTAMA ----------------------");
        System.out.println("|                                                   |");
        System.out.println("|   [1]  Masuk Parkir                               |");
        System.out.println("|   [2]  Lihat Data Parkir                          |");
        System.out.println("|   [3]  Keluar Parkir                              |");
        System.out.println("|   [4]  Hapus Data Parkir                          |");
        System.out.println("|   [5]  Cari Data Parkir                           |");
        System.out.println("|   [6]  Logout                                     |");
        System.out.println("|                                                   |");
        System.out.println("-----------------------------------------------------");
    }

    public void tampilkanRingkasanSlot(int motorKosong, int motorTotal,
                                       int mobilKosong, int mobilTotal) {
        System.out.println("Slot kosong -> Motor: " + motorKosong + "/" + motorTotal
                + " | Mobil: " + mobilKosong + "/" + mobilTotal);
    }

    public int tampilkanParkirAktif(List<Parkir> daftar) {
        int jumlah = 0;
        System.out.println("\n=========== KENDARAAN YANG SEDANG PARKIR ===========");
        System.out.printf("%-9s %-12s %-6s %-16s%n", "ID Parkir", "Plat", "Slot", "Waktu Masuk");
        for (Parkir parkir : daftar) {
            if (parkir.getStatusParkir().equals(Parkir.MASIH_PARKIR)) {
                System.out.printf("%-9d %-12s %-6s %-16s%n", parkir.getIdParkir(),
                        parkir.getKendaraan().getNomorPlat(), parkir.getSlot().getNomorSlot(),
                        parkir.getWaktuMasuk());
                jumlah++;
            }
        }
        return jumlah;
    }

    public void tampilkanParkir(List<Parkir> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("\nBelum ada data parkir.");
            return;
        }
        for (Parkir parkir : daftar) {
            parkir.tampilkanInfo();
        }
    }

    public void pesan(String pesan) {
        System.out.println(pesan);
    }
}