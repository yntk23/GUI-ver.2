-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2023 at 04:48 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `ourname`
--

CREATE TABLE `ourname` (
  `id` double UNSIGNED NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `nickName` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ourname`
--

INSERT INTO `ourname` (`id`, `firstName`, `lastName`, `nickName`) VALUES
(6545000128, 'Nontakorn', 'Singkrajom', 'Yok'),
(6545000241, 'Nattapong', 'Sapawaha', 'Ohm'),
(6552300034, 'Oonanongwan', 'Boonrucksilp', 'Yok'),
(6552300204, 'Thanasorn', 'Sirirattanapong', 'Net');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ourname`
--
ALTER TABLE `ourname`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ourname`
--
ALTER TABLE `ourname`
  MODIFY `id` double UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6552300205;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
