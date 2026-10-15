# 🚗 Sistem Manajemen Parkir Kendaraan 🚗

**Nama** : Nabila Fitriani 

**NIM**  : 2509116063  

**Kelas** : B  

---
## 📌 Deskripsi Singkat Program

Program ini merupakan aplikasi berbasis **Java** yang digunakan untuk mengelola sistem parkir kendaraan pada berbagai tempat yang menyediakan area parkir, seperti **coffee shop, mall, kampus, perkantoran, maupun tempat usaha lainnya.**

Program hanya dapat **diakses** oleh **petugas** yang melakukan **login** menggunakan akun yang telah terdaftar di dalam sistem. Dengan demikian, fitur-fitur pengelolaan parkir hanya dapat digunakan setelah petugas berhasil melakukan login.

Program membantu petugas dalam mengelola data kendaraan dan transaksi parkir. Sistem menyediakan fitur **CRUD (Create, Read, Update, Delete)** pada data parkir, mulai dari menambahkan, melihat, memperbarui, menghapus, hingga mencari data berdasarkan ID.

Program juga dapat menghitung tarif parkir berdasarkan **jenis kendaraan dan lama parkir**, **mengelola status slot**, serta **menampilkan receipt pembayaran** setelah transaksi selesai.

Semua data disimpan di **database MySQL** (**db_parkir**). Program terhubung ke database memakai **JDBC** dengan Prepared Statement dan **DAO**. Data awal (petugas, kendaraan, slot, dan data parkir) diisi melalui file db_parkir.sql, jadi pada saat menu Lihat Data Parkir dibuka datanya sudah langsung ditampilkan oleh program.

Selain itu, program ini juga telah menerapkan **ORM** (JPA / Hibernate) pada package **DAO.JPA**.

### 🔹Fitur Program

🗂️ **Pengelolaan Data**

- Mengelola data kendaraan, slot parkir, dan transaksi parkir yang disimpan di _database_.
- Melihat, mencari, mengubah, dan menghapus data parkir, ada konfirmasi sebelum menghapus.
- _Dummy data_ diisi lewat file .sql dan ID dibuat otomatis oleh _database_ (**AUTO_INCREMENT**).
  
🔐 **Login dan Validasi**

- Login petugas (nama + password, maksimal 3 kali percobaan).
- Menerapkan validasi input.

🅿️ **Operasional Parkir**

- Masuk Parkir > pendaftaran kendaraan dan check-in dalam satu langkah. Apabila kendaraan sudah pernah terdaftar, datanya akan dipakai kembali.
- Slot parkir otomatis dengan kapasitas (25 slot Motor, 10 slot Mobil) dan dibaca dari database. Jika penuh, maka kendaraan tidak bisa masuk.
- Waktu keluar otomatis dari waktu masuk ditambah lama parkir, dengan format waktu yang divalidasi.
- Menghitung tarif parkir berdasarkan jenis kendaraan dan lama parkir.

💳 **Pembayaran**

- Pembayaran Cash atau QRIS (QRIS otomatis sesuai tarif tanpa input jumlah bayar), kembalian dihitung otomatis.
- _Receipt_ dicetak setelah pembayaran berhasil.

💾 **Database**

- Koneksi ke **MySQL** menggunakan **JDBC, PreparedStatement,** dan **DAO**.
- Update data parkir dan status slot dijalankan dalam satu transaksi agar keduanya selalu sama.
- Jika terdapat error dari database, maka program akan menampilkan pesan dan tidak langsung berhenti.
  
🧩 **Konsep OOP**

- MVC, DAO, Access Modifier, Encapsulation, Inheritance, Polymorphism (Overriding dan Overloading), Abstraction, Interface, keyword final, dan setter bersyarat.

---
## 📂 Struktur Program

Program menggunakan struktur MVC (Model-View-Controller) dengan beberapa package yang memiliki fungsi berbeda.

<img width="362" height="397" alt="image" src="https://github.com/user-attachments/assets/8768070c-21f9-42e0-8a7c-cae20e46c722" />

**Penjelasan Package**

**1. config**

  **DBConnection** menyimpan URL, user, dan password database untuk koneksi **JDBC**. Sedangkan **JPAUtil** dipakai **ORM** untuk membuat _EntityManager_.
model

**2.model**

  Berisi class yang mewakili objek di sistem, yaitu Kendaraan, Motor, Mobil, Petugas, SlotParkir, Parkir, dan interface Bertarif. Class model juga diberi **anotasi JPA** (@Entity, @Column, dll.) untuk ORM.

**3. dao**

   Berisi interface yang menjadi acuan operasi database, seperti menambahkan data, mencari data berdasarkan ID atau nomor plat, dan mengambil seluruh data. Implementasinya dipisahkan menjadi dua pendekatan, yaitu dao.jdbc dan dao.jpa.
   
   - **dao.jdbc** berisi class implementasi DAO menggunakan JDBC. Proses akses database dilakukan secara langsung menggunakan query SQL dan PreparedStatement.
   - **dao.jpa** berisi class implementasi DAO menggunakan JPA/ORM. Akses database dilakukan melalui EntityManager dan pemetaan objek Java ke tabel database, sehingga penulisan SQL manual dapat dikurangi.

Kedua implementasi tersebut mengikuti interface DAO yang sama, tetapi memiliki cara kerja berbeda dalam mengakses database.

**4. view**

  Berisi ParkirView yang menangani tampilan menu, input user, validasi input, dan pesan yang ditampilkan kepada user.

**5. controller**

  Berisi ParkirController yang mengatur alur program dan menghubungkan bagian View dengan Service.

**6. service**

  Berisi ParkirService yang menangani logika program seperti login, cari slot kosong, dan status pembayaran. Service tidak menyimpan data sendiri, semua data diambil lewat DAO.
  
**7. main**

  Berisi Main sebagai titik awal program. Class ini membuat object DAO, Service, View, dan Controller, lalu menjalankan program.

---
### ℹ️ Pengembangan Struktur 

Program dikembangkan dari versi sebelumnya yang menggunakan ArrayList sebagai penyimpanan data menjadi aplikasi yang **terhubung** dengan **database MySQL**. Pengembangan ini juga menambahkan **implementasi JDBC** dan **ORM** menggunakan Hibernate/JPA.

```
src/main/java
├── config
│   ├── DBConnection.java       (koneksi JDBC)
│   └── JPAUtil.java            (untuk ORM)
├── model
│   ├── Bertarif.java           (interface)
│   ├── Kendaraan.java          (abstract class)
│   ├── Motor.java
│   ├── Mobil.java
│   ├── Petugas.java
│   ├── SlotParkir.java
│   └── Parkir.java
├── dao
│   ├── KendaraanDAO.java       (JDBC + PreparedStatement)
│   ├── ParkirDAO.java
│   ├── PetugasDAO.java
│   ├── SlotParkirDAO.java
│   └── orm                     (nilai tambah, ORM)
│       ├── KendaraanDAOOrm.java
│       ├── ParkirDAOOrm.java
│       ├── PetugasDAOOrm.java
│       └── SlotParkirDAOOrm.java
├── view
│   └── ParkirView.java
├── controller
│   └── ParkirController.java
├── service
│   └── ParkirService.java
└── main
    └── Main.java

src/main/resources/META-INF
└── persistence.xml             (konfigurasi ORM)

sql
└── db_parkir.sql               (tabel + dummy data)
```

### ➕ Penambahan:

**1. Penyimpanan data**  
  
  Mengganti penggunaan ArrayList sebagai penyimpanan data sementara dengan database MySQL agar data kendaraan, petugas, slot parkir, dan transaksi parkir dapat tersimpan secara permanen.
  
**2. Akses database**

  Menambahkan pola **DAO** (Data Access Object) untuk memisahkan proses akses database dari logika utama aplikasi. DAO menyediakan interface sebagai acuan operasi database, dengan implementasi **JDBC** yang menggunakan query SQL dan **PreparedStatement** untuk menjalankan operasi CRUD (Create, Read, Update, Delete).
  
**3. Implementasi ORM**

  Menambahkan alternatif implementasi DAO menggunakan **Hibernate/JPA** melalui subpackage **dao.jpa**. Implementasi ini memanfaatkan EntityManager dan pemetaan objek Java ke tabel database, sehingga operasi database dapat dilakukan melalui pendekatan ORM tanpa harus menulis query SQL secara manual untuk setiap operasi.
  
**4. Model**

  Menyesuaikan class model dengan struktur tabel database melalui anotasi JPA, seperti @Entity dan @Id, serta pemetaan atribut objek ke kolom database. Penyesuaian ini memungkinkan objek Java dikelola dan dipetakan ke database menggunakan JPA.
  
**5. Alur program**

  Menyesuaikan Service, Controller, dan Main agar proses pengelolaan data dilakukan melalui DAO. Service menangani logika aplikasi, Controller menghubungkan proses dari View dengan Service, sedangkan Main menjalankan aplikasi. Dengan alur ini, proses akses database tidak perlu ditulis langsung di setiap bagian program.
  
**6. Konfigurasi**

  Menambahkan file **persistence.xml** untuk mengatur konfigurasi JPA/ORM, termasuk unit persistence dan koneksi ke database. Selain itu, menambahkan file SQL untuk membuat struktur tabel dan memasukkan data awal ke dalam database MySQL.

---
## 🛠️ Cara Menjalankan

1. Jalankan MySQL melalui aplikasi XAMPP atau Laragon, lalu klik start.
2. Buka phpMyAdmin, import file sql/db_parkir.sql melalui menu Import.
3. Pastikan library MySQL Connector/J, Hibernate ORM 6.x, dan JDK 17 ke atas sudah tersedia dalam project.
4. Sesuaikan username dan password database pada config/DBConnection.java dan persistence.xml jika diperlukan.
5. Buka dan jalankan Main.java, kemudian login untuk mulai menggunakan program.

---
## ⚙️ Penjelasan Alur Program

Program dimulai dari login petugas. Setelah berhasil login, petugas dapat mengakses menu utama untuk mengelola data parkir. Data dibaca dan disimpan melalui DAO ke database MySQL, menggunakan implementasi JDBC atau ORM yang dipilih.

### A. Login Petugas

Saat program dijalankan, petugas memasukkan nama dan password. Sistem memeriksa data petugas yang tersimpan di database. Jika data sesuai, petugas dapat masuk ke menu utama. Jika login gagal, petugas diberikan maksimal tiga kali percobaan.

<img width="509" height="393" alt="image" src="https://github.com/user-attachments/assets/795d36f7-f453-4f1c-bf8e-3a8ce013ce2d" />

Saat program dijalankan, petugas memasukkan nama dan password. Hanya petugas terdaftar dengan password yang benar yang dapat masuk. Petugas diberi kesempatan 3 kali, setelah itu program otomatis ditutup. Petugas yang berhasil login otomatis tercatat pada setiap transaksi parkir, sehingga tidak perlu lagi memilih ID petugas.

**Akun petugas untuk login:**

- Jai 12345
- Ahmad 54321

<img width="386" height="131" alt="image" src="https://github.com/user-attachments/assets/b13fd4fa-ef37-4bbd-ac92-85a3d0c6d79f" />

---
### B. Menu Utama

Setelah login berhasil, program menampilkan menu utama yang berisi pilihan untuk masuk parkir, melihat data parkir, keluar parkir, mencari data, memperbarui data, menghapus data, dan logout.

<img width="384" height="348" alt="image" src="https://github.com/user-attachments/assets/13b33a4b-fcf7-49e3-82bb-3dd4b99e31fe" />

Selain itu, menu juga menampilkan informasi ketersediaan slot parkir berdasarkan data yang tersimpan di database.

---
### C. Masuk Parkir

Menu ini digunakan untuk mencatat kendaraan yang masuk ke area parkir.

<img width="573" height="194" alt="image" src="https://github.com/user-attachments/assets/90d50422-0fb4-4ec6-9625-c575460e6a8a" />

1. Petugas memilih jenis kendaraan, kemudian memasukkan nomor plat.
2. Sistem mengecek slot kosong untuk jenis tersebut. Jika penuh, proses dihentikan sebelum petugas mengisi data lain.
3. Selanjutnya, petugas mengisi merk, warna, serta atribut pembeda: kapasitas mesin (cc) untuk Motor atau jumlah pintu untuk Mobil.
4. Petugas memasukkan waktu masuk dengan format DD-MM-YYYY HH:MM (contoh 24-09-2026 17:30).
5. Sistem menyimpan data kendaraan dan transaksi parkir melalui DAO ke database MySQL.
6. Data parkir tersimpan dengan status Masih Parkir, slot dipilih otomatis (Motor: A01-A25, Mobil: B01-B10) dan berubah menjadi Terisi.

Jika slot penuh, maka proses masuk parkir tidak dapat dilanjutkan.

---
### D. Lihat Data Parkir

Program mengambil data parkir dari database melalui DAO, kemudian menampilkannya kepada petugas.

<img width="303" height="351" alt="image" src="https://github.com/user-attachments/assets/4af517d9-a442-4b69-8eba-c817c061afad" /><br>
<img width="294" height="592" alt="image" src="https://github.com/user-attachments/assets/7f5b3640-0bd8-4753-89ee-58cbdf845863" />

Program menampilkan seluruh data parkir yang telah tersimpan. Informasi yang ditampilkan meliputi kendaraan, slot, petugas, waktu masuk, waktu keluar, status parkir, dan status pembayaran.

---
### E. Keluar Parkir

Menu ini digunakan untuk menyelesaikan transaksi parkir dan melakukan pembayaran.

1. Petugas memilih data kendaraan yang masih parkir.
2. Petugas memasukkan lama parkir. Sistem menghitung waktu keluar dan tarif berdasarkan jenis kendaraan serta lama parkir.
3. Petugas memilih metode pembayaran
   - **Metode Pembayaran Cash**
     Petugas memasukkan jumlah bayar. Jika kurang dari tarif, transaksi ditolak dan data tidak berubah. Jika cukup, kembalian dihitung otomatis.

     <img width="380" height="293" alt="image" src="https://github.com/user-attachments/assets/f0f98985-d644-464d-9fa3-2c75543e5cdb" /><br>

     <img width="293" height="397" alt="image" src="https://github.com/user-attachments/assets/8deb6221-1925-4e85-b893-642267d14665" />

  - **Metode Pembayaran Qris**
    Jumlah bayar otomatis sama dengan tarif (tanpa input dan tanpa kembalian).

    <img width="378" height="297" alt="image" src="https://github.com/user-attachments/assets/6e194050-f0c7-4ae5-8d47-a3606f4a1659" /><br>

    <img width="292" height="397" alt="image" src="https://github.com/user-attachments/assets/5e2fea82-3014-48f8-976e-7c273184db3d" />

4. Jika pembayaran berhasil, sistem memperbarui data transaksi di database. Status parkir menjadi Selesai, status pembayaran menjadi Lunas, dan slot parkir kembali Kosong.
5. Program menampilkan receipt pembayaran.

**Tarif Kendaraan per Jam**

- Motor: Rp2.000 per jam, maksimal Rp20.000
- Mobil: Rp5.000 per jam, maksimal Rp50.000
  
Sistem hanya menampilkan kendaraan yang masih parkir. Jika tidak ada, maka akan kembali ke menu utama.

<img width="377" height="148" alt="image" src="https://github.com/user-attachments/assets/8c7bbb65-58ca-49e8-9dac-96dcc088300b" />

---
### F. Hapus Data Parkir

Petugas memasukkan ID parkir yang ingin dicari. Program meminta DAO mengambil data berdasarkan ID tersebut dari database.

**1. Batal Menghapus Data**

  <img width="307" height="382" alt="image" src="https://github.com/user-attachments/assets/369ede08-7bbb-434a-abab-530aeaee2e88" /><br>

  <img width="300" height="399" alt="image" src="https://github.com/user-attachments/assets/01a06863-2624-406b-be30-cb0a29ad9e4d" />

**2. Berhasil Menghapus Data**

  <img width="308" height="378" alt="image" src="https://github.com/user-attachments/assets/c91e0707-f582-4a9d-a78b-189fc452fea7" /><br>

  <img width="301" height="399" alt="image" src="https://github.com/user-attachments/assets/5dffce1a-c445-46ec-be9a-e2738a7c82b2" />

Petugas memasukkan ID parkir yang ingin dihapus. Jika data ditemukan, sistem menampilkan data parkir dan meminta konfirmasi penghapusan.

Petugas dapat memilih Y untuk menghapus atau T untuk membatalkan. Jika penghapusan dikonfirmasi, data dihapus dan status slot berubah menjadi Kosong sehingga dapat digunakan kembali.

---
### G. Lihat Data Parkir

Petugas memasukkan ID parkir yang ingin dicari. Program akan mencari data berdasarkan ID tersebut.

Jika data ditemukan, program menampilkan informasi data parkir seperti kendaraan, slot, petugas, waktu masuk, waktu keluar, status parkir, dan status pembayaran.

<img width="327" height="393" alt="image" src="https://github.com/user-attachments/assets/010ed713-c98c-459b-aa0b-92ccad2a17ca" />

Jika data tidak ditemukan, program menampilkan pesan bahwa data parkir dengan ID tersebut tidak ditemukan.

<img width="316" height="110" alt="image" src="https://github.com/user-attachments/assets/bdaa50c8-345b-46fc-90ab-dd9499f2ed4d" />

Perubahan disimpan melalui DAO ke database MySQL sehingga data terbaru dapat ditampilkan kembali saat program dijalankan.

---
### H. Logout

<img width="515" height="104" alt="image" src="https://github.com/user-attachments/assets/ca5619fb-c96e-4452-af50-23d5c39331ee" />

Pengguna memilih menu Logout untuk keluar dari program. Setelah itu sistem menampilkan pesan "Sampai jumpa!" dan program selesai.

---
## 🔹 Access Modifier

Penerapan Access Modifier pada program ini terdapat pada atribut-atribut di dalam class model seperti Kendaraan, Petugas, SlotParkir, dan Parkir.

Atribut dibuat menggunakan access modifier private, sehingga tidak dapat diakses secara langsung dari luar class.

Contohnya pada class Parkir terdapat atribut:

<img width="503" height="167" alt="image" src="https://github.com/user-attachments/assets/c8ff0588-d4c2-47ad-831b-58cc62e1c1b8" /><br>

<img width="439" height="517" alt="image" src="https://github.com/user-attachments/assets/84ab064f-b1ff-4ad2-a92e-3938ff1b2c91" />

Dengan penggunaan **public** memungkinkan method tersebut dipanggil dari class lain yang dapat mengakses class Parkir. Sedangkan penggunaan **private**, akses terhadap atribut dapat dibatasi sehingga data di dalam object menjadi lebih terkontrol.

---
## 🔹 Encapsulation

Penerapan Encapsulation dilakukan dengan membungkus data atau atribut di dalam class dan menyediakan getter dan setter untuk mengakses atau mengubah data tersebut.

Pada class SlotParkir, getter digunakan untuk mengambil nilai atribut, sedangkan setter digunakan untuk mengubah nilai tertentu.

Contohnya:

<img width="266" height="209" alt="image" src="https://github.com/user-attachments/assets/24caccb4-1f57-4a6b-ab87-6ba25afeed3c" />

Dengan adanya encapsulation, atribut yang bersifat private tidak diakses secara langsung dari luar class, tetapi melalui method yang telah disediakan.

Selain itu, terdapat pula setter dengan kondisi. Setter pada data yang krusial diberi kondisi agar nilai yang salah ditolak, contohnya status slot hanya boleh Kosong atau Terisi.

<img width="563" height="114" alt="image" src="https://github.com/user-attachments/assets/1a64c510-e8d3-4027-be43-c70413dc2724" />

Adapun beberapa contoh penerapan encapsulation pada class lainnya,sebagai berikut.

<img width="522" height="164" alt="image" src="https://github.com/user-attachments/assets/4b9c3f1e-8285-44ed-a147-f79ea7d9de46" /><br>

<img width="479" height="165" alt="image" src="https://github.com/user-attachments/assets/669881f3-d62c-40ef-82d3-ddbf4d7bd347" />

---
## 🔹 Keyword final

Keyword final digunakan agar nilai yang tidak boleh berubah terlindungi. Berikut salah satu contoh penerapannya dalam program.

<img width="456" height="77" alt="image" src="https://github.com/user-attachments/assets/800169e3-845f-474d-b534-e549cd778bd5" />

---
## 🔹 Validasi Input

Penerapan Validasi Input terdapat pada class **ParkirView.java.**

Program memiliki beberapa method khusus untuk memastikan input yang diberikan user sesuai dengan ketentuan, berikut beberapa contohnya.

<img width="554" height="274" alt="image" src="https://github.com/user-attachments/assets/c681d6e3-65ff-4b54-848c-9ba11602f80a" /><br>

<img width="669" height="173" alt="image" src="https://github.com/user-attachments/assets/69eee0bc-bb11-4d91-9147-ff81c0a8e1b6" />

Validasi input angka juga menggunakan **try-catch**, sehingga program tidak langsung crash ketika user memasukkan input dengan tipe data yang salah.

---
## 🔹 Inheritance
Penerapan Inheritance terdapat pada class Motor dan Mobil yang mewarisi class Kendaraan sebagai superclass.

Struktur inheritance pada program:

```
Kendaraan
  /    \
Motor  Mobil
```

**</> Kode SubClass**

**1. Subclass Motor**

  <img width="654" height="288" alt="image" src="https://github.com/user-attachments/assets/0ec0e3f2-22dd-4d98-9da9-cb9274aaeedc" />

**2. Subclass Mobil**

  <img width="651" height="310" alt="image" src="https://github.com/user-attachments/assets/5b6e90ab-856a-4efb-8c82-a10ffc2f71a0" />

Dengan **inheritance**, class **Motor** dan **Mobil** dapat menggunakan atribut dan method yang terdapat pada class Kendaraan tanpa harus membuatnya kembali.

---
## 🔹 Polymorphism

Penerapan Polymorphism pada program ini menggunakan method **overriding** dan **overloading**. Method **tampilkanInfo()** yang terdapat pada class Kendaraan **dioverride** pada class Motor dan Mobil.

**1. Pada class Motor:**

  <img width="598" height="390" alt="image" src="https://github.com/user-attachments/assets/8bdfb316-ce33-4cba-9d82-ac6782fd2f48" />

**2. Pada class Mobil:**

  <img width="585" height="391" alt="image" src="https://github.com/user-attachments/assets/d3a3dc07-dc94-4376-9d09-5ca271f8217d" />

Dengan overriding tersebut, method **tampilkanInfo()** dapat menghasilkan tampilan yang berbeda sesuai dengan object yang digunakan.

Selain tampilkanInfo(), method getJenisKendaraan(), getTarifPerJam(), dan getTarifMaksimal() juga di-_override_ pada Motor dan Mobil, sehingga tarif berbeda sesuai jenis kendaraan (Motor Rp2.000/jam, Mobil Rp5.000/jam). 

Class Parkir cukup memanggil kendaraan.hitungTarif(lamaJam) dan kendaraan.tampilkanInfo() tanpa perlu tahu apakah objeknya Motor atau Mobil.

Sedangkan untuk **Overloading**, terdapat pada **Method cariKendaraan()** dalam ParkirService yang memiliki dua versi dengan parameter berbeda.

<img width="414" height="170" alt="image" src="https://github.com/user-attachments/assets/20ead61f-c00b-4724-82ac-f6b277da8ae0" />

---
## 🔹 Abstraction

Class **Kendaraan** dibuat sebagai **abstract** class sehingga tidak dapat dibuat objek langsung, hanya melalui Motor atau Mobil. 

Class ini memiliki abstract method yang wajib diisi oleh subclass.

**1. Abstract Class**

  <img width="549" height="510" alt="image" src="https://github.com/user-attachments/assets/df591dd0-c283-411a-bbf1-791b69e6c73c" />

**2. abstract Method**

  <img width="379" height="97" alt="image" src="https://github.com/user-attachments/assets/6131a188-517f-493e-9750-f4433bbfd39e" />

---
## 🔹 Interface

Interface digunakan dalam program ini dengan nama **Bertarif** berada di package model (**Bertarif.java**) dan menjadi **kontrak** untuk objek yang memiliki tarif parkir. 

Interface ini diterapkan oleh Kendaraan. Rumus tarif ditulis satu kali di Kendaraan, sedangkan angka tarif diambil dari subclass masing-masing.

**1. Model Interface**

  <img width="301" height="58" alt="image" src="https://github.com/user-attachments/assets/75672acc-51eb-4065-bd2c-9b16356c8c38" />

**2. Penerapan**

  <img width="347" height="140" alt="image" src="https://github.com/user-attachments/assets/ebe9dbd0-f67c-4060-aa00-e3b120e8ac79" />

---
## 🔹 Struktur MVC

Program menggunakan pola MVC yang dilengkapi DAO untuk memisahkan pengelolaan data dan akses database.

<img width="356" height="595" alt="image" src="https://github.com/user-attachments/assets/25df6587-34a0-4ca5-82a2-72355b8dbc94" />

Struktur package tersebut membuat setiap bagian program memiliki tugas yang berbeda sehingga kode lebih terorganisir dan mudah dikembangkan.

---
## 🔹 Dummy Data

Pada versi sebelumnya, data awal program dikelola melalui ParkirService menggunakan ArrayList. Pada versi terbaru, data dikelola melalui database MySQL dan diakses menggunakan DAO.

Data awal dapat disiapkan melalui file sql/db_parkir.sql, kemudian digunakan oleh program ketika dijalankan.

**1. Slot Parkir**

  Data slot parkir digunakan untuk menyimpan informasi nomor slot dan status ketersediaannya, seperti Kosong atau Terisi.

  <img width="474" height="523" alt="image" src="https://github.com/user-attachments/assets/e7afbf36-053c-4dc6-833d-bf12af3d6b79" /><br>

  <img width="479" height="332" alt="image" src="https://github.com/user-attachments/assets/fafc660a-ff80-48b3-9245-e8eeb3dd928f" />

**2. Petugas**

  Data petugas digunakan untuk menyimpan informasi akun yang diperlukan dalam proses login dan pencatatan transaksi parkir.

  <img width="821" height="356" alt="image" src="https://github.com/user-attachments/assets/3a10f232-e719-44ef-906c-d8866f88129d" />

**3. Data Kendaraan dan Parkir**

  Data kendaraan menyimpan informasi kendaraan yang terdaftar, sedangkan data parkir menyimpan informasi transaksi, seperti kendaraan yang parkir, petugas yang bertugas, slot yang digunakan, waktu masuk, waktu keluar, dan status pembayaran.

  <img width="912" height="356" alt="image" src="https://github.com/user-attachments/assets/c38fa27f-ec92-40b6-9fd0-6c1e8285146c" /><br>

  <img width="1083" height="412" alt="image" src="https://github.com/user-attachments/assets/ca269598-5ffb-4659-bf86-7a710a7d1ae5" />

  Dengan menggunakan database, data dapat disimpan secara persisten sehingga tidak hilang ketika program ditutup. Data yang telah tersimpan juga dapat diambil kembali saat program dijalankan.

---
## 🔹Penerapan DAO (Data Access Object)

DAO digunakan untuk **memisahkan** proses akses database dari logika utama aplikasi. Pada program ini, package dao berisi **interface** sebagai acuan operasi database, sedangkan **implementasinya** dibagi menjadi dao.jdbc dan dao.jpa. Keduanya menyediakan operasi yang sama, tetapi menggunakan **cara berbeda** untuk mengakses database.

 Contoh penerapannya pada KendaraanDAO.java:

 <img width="365" height="267" alt="image" src="https://github.com/user-attachments/assets/df116398-aa30-43bf-9289-d392fa88497f" />

---
## 🔹 Penerapan JDBC

**JDBC** (Java Database Connectivity) digunakan untuk menghubungkan aplikasi dengan database MySQL. Pada program ini, koneksi diatur melalui config/DBConnection.java, sedangkan operasi database dijalankan oleh class DAO di dao.jdbc menggunakan query SQL dan PreparedStatement.

Contoh penerapan JDBC pada DAO:

<img width="795" height="420" alt="image" src="https://github.com/user-attachments/assets/ba4a0c61-500b-4f8b-a33f-176f6755917d" /><br>

<img width="720" height="173" alt="image" src="https://github.com/user-attachments/assets/057d3116-48c6-47ae-b191-b10ec298a7f6" />

Method tambah() digunakan untuk menyimpan data kendaraan ke database menggunakan PreparedStatement. Data motor dan mobil dibedakan berdasarkan jenisnya, lalu getGeneratedKeys() mengambil ID yang otomatis dibuat database dan menyimpannya ke objek kendaraan.

---
## 🔹 Penerapan ORM (Hibernate/JPA)

**ORM** (Object-Relational Mapping) digunakan sebagai **alternatif JDBC** untuk mengakses database melalui objek Java. Pada program ini, implementasinya berada di dao.jpa, menggunakan JPA dengan Hibernate sebagai provider ORM. config/JPAUtil.java menyediakan EntityManager, sedangkan persistence.xml mengatur konfigurasi ORM.

- **KendaraanDAOJPA**
  
  <img width="747" height="329" alt="image" src="https://github.com/user-attachments/assets/3cfdaf75-78ef-4a40-8655-ca1122450e5d" />

  Berbeda dengan JDBC yang menggunakan query SQL dan PreparedStatement, ORM menggunakan operasi seperti persist() untuk menyimpan objek, find() untuk mencari data berdasarkan ID, serta JPQL untuk mengambil data. 

- **Model Kendaraan**

  <img width="547" height="353" alt="image" src="https://github.com/user-attachments/assets/0083c85c-b24a-4e68-a58f-b63d671c45e3" />

  Anotasi JPA seperti @Entity, @Id, dan @GeneratedValue jika digunakan, untuk menunjukkan pemetaan objek ke database.

- **persistence.xml**

  <img width="840" height="504" alt="image" src="https://github.com/user-attachments/assets/f63ce2b4-b5b8-4497-af35-1e3cec289b96" />

  Dilakukan konfigurasi persistence unit, provider Hibernate, dan koneksi database.

Berbeda dengan JDBC yang menjalankan query SQL secara langsung menggunakan PreparedStatement, ORM memungkinkan operasi database dilakukan **melalui objek Java dan fitur JPA**. Keduanya tetap mengikuti interface DAO yang sama, tetapi menggunakan pendekatan akses database yang berbeda.
