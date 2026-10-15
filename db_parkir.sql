-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 12:21 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_parkir`
--

-- --------------------------------------------------------

--
-- Table structure for table `kendaraan`
--

CREATE TABLE `kendaraan` (
  `id_kendaraan` int NOT NULL,
  `nomor_plat` varchar(20) NOT NULL,
  `merk` varchar(100) NOT NULL,
  `warna` varchar(50) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL,
  `kapasitas_cc` int DEFAULT NULL,
  `jumlah_pintu` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kendaraan`
--

INSERT INTO `kendaraan` (`id_kendaraan`, `nomor_plat`, `merk`, `warna`, `jenis_kendaraan`, `kapasitas_cc`, `jumlah_pintu`) VALUES
(1, 'KT 1234 AB', 'Honda Vario', 'Hitam', 'Motor', 125, NULL),
(2, 'KT 5678 CD', 'Toyota Avanza', 'Putih', 'Mobil', NULL, 4);

-- --------------------------------------------------------

--
-- Table structure for table `parkir`
--

CREATE TABLE `parkir` (
  `id_parkir` int NOT NULL,
  `id_kendaraan` int NOT NULL,
  `id_petugas` int NOT NULL,
  `nomor_slot` varchar(10) NOT NULL,
  `waktu_masuk` varchar(30) NOT NULL,
  `waktu_keluar` varchar(30) DEFAULT NULL,
  `status_parkir` varchar(30) NOT NULL,
  `metode_pembayaran` varchar(20) NOT NULL,
  `jumlah_bayar` double NOT NULL DEFAULT '0',
  `status_pembayaran` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `parkir`
--

INSERT INTO `parkir` (`id_parkir`, `id_kendaraan`, `id_petugas`, `nomor_slot`, `waktu_masuk`, `waktu_keluar`, `status_parkir`, `metode_pembayaran`, `jumlah_bayar`, `status_pembayaran`) VALUES
(1, 1, 1, 'A01', '15-10-2026 08:00', NULL, 'Masih Parkir', '-', 0, 'Belum Bayar');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` int NOT NULL,
  `nama_petugas` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama_petugas`, `password`) VALUES
(1, 'Jai', '12345'),
(2, 'Ahmad', '54321');

-- --------------------------------------------------------

--
-- Table structure for table `slot_parkir`
--

CREATE TABLE `slot_parkir` (
  `nomor_slot` varchar(10) NOT NULL,
  `jenis_slot` varchar(20) NOT NULL,
  `status_slot` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `slot_parkir`
--

INSERT INTO `slot_parkir` (`nomor_slot`, `jenis_slot`, `status_slot`) VALUES
('A01', 'Motor', 'Terisi'),
('A02', 'Motor', 'Kosong'),
('A03', 'Motor', 'Kosong'),
('A04', 'Motor', 'Kosong'),
('A05', 'Motor', 'Kosong'),
('A06', 'Motor', 'Kosong'),
('A07', 'Motor', 'Kosong'),
('A08', 'Motor', 'Kosong'),
('A09', 'Motor', 'Kosong'),
('A10', 'Motor', 'Kosong'),
('A11', 'Motor', 'Kosong'),
('A12', 'Motor', 'Kosong'),
('A13', 'Motor', 'Kosong'),
('A14', 'Motor', 'Kosong'),
('A15', 'Motor', 'Kosong'),
('A16', 'Motor', 'Kosong'),
('A17', 'Motor', 'Kosong'),
('A18', 'Motor', 'Kosong'),
('A19', 'Motor', 'Kosong'),
('A20', 'Motor', 'Kosong'),
('A21', 'Motor', 'Kosong'),
('A22', 'Motor', 'Kosong'),
('A23', 'Motor', 'Kosong'),
('A24', 'Motor', 'Kosong'),
('A25', 'Motor', 'Kosong'),
('B01', 'Mobil', 'Kosong'),
('B02', 'Mobil', 'Kosong'),
('B03', 'Mobil', 'Kosong'),
('B04', 'Mobil', 'Kosong'),
('B05', 'Mobil', 'Kosong'),
('B06', 'Mobil', 'Kosong'),
('B07', 'Mobil', 'Kosong'),
('B08', 'Mobil', 'Kosong'),
('B09', 'Mobil', 'Kosong'),
('B10', 'Mobil', 'Kosong');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`id_kendaraan`),
  ADD UNIQUE KEY `nomor_plat` (`nomor_plat`);

--
-- Indexes for table `parkir`
--
ALTER TABLE `parkir`
  ADD PRIMARY KEY (`id_parkir`),
  ADD KEY `fk_parkir_kendaraan` (`id_kendaraan`),
  ADD KEY `fk_parkir_petugas` (`id_petugas`),
  ADD KEY `fk_parkir_slot` (`nomor_slot`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indexes for table `slot_parkir`
--
ALTER TABLE `slot_parkir`
  ADD PRIMARY KEY (`nomor_slot`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kendaraan`
--
ALTER TABLE `kendaraan`
  MODIFY `id_kendaraan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `parkir`
--
ALTER TABLE `parkir`
  MODIFY `id_parkir` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `petugas`
--
ALTER TABLE `petugas`
  MODIFY `id_petugas` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parkir`
--
ALTER TABLE `parkir`
  ADD CONSTRAINT `fk_parkir_kendaraan` FOREIGN KEY (`id_kendaraan`) REFERENCES `kendaraan` (`id_kendaraan`),
  ADD CONSTRAINT `fk_parkir_petugas` FOREIGN KEY (`id_petugas`) REFERENCES `petugas` (`id_petugas`),
  ADD CONSTRAINT `fk_parkir_slot` FOREIGN KEY (`nomor_slot`) REFERENCES `slot_parkir` (`nomor_slot`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
