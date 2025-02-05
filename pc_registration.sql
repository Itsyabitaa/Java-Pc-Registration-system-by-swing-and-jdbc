-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2025 at 10:03 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pc_registration`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `studentName` varchar(100) DEFAULT NULL,
  `userID` varchar(100) DEFAULT NULL,
  `pcID` varchar(100) DEFAULT NULL,
  `message` text DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`studentName`, `userID`, `pcID`, `message`) VALUES
('teshx', 'STD123', '233yy', 'gugviuqgvqivv'),
('', 'STD124', '', ''),
('alex', '12', '123', 'my pc is lost'),
('', '12', '', ''),
('ale', '12', '12', 'my pc is avaliable');

-- --------------------------------------------------------

--
-- Table structure for table `pcinformation`
--

CREATE TABLE `pcinformation` (
  `pcID` varchar(100) NOT NULL,
  `pcName` varchar(100) DEFAULT NULL,
  `userID` varchar(100) DEFAULT NULL,
  `pcModel` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pcinformation`
--

INSERT INTO `pcinformation` (`pcID`, `pcName`, `userID`, `pcModel`, `status`) VALUES
('php123', 'gaming', 'STD', '343', 'lost'),
('sdfg', 'hp elite book', 'hp user', 'sdfg', 'Available'),
('yab123', 'apple', 'ddu140', 'book', 'Available'),
('geda123', 'hp', 'ddu14015', 'g50', 'Available'),
('123', 'hp alex', '12', 'sdf', 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(100) DEFAULT NULL,
  `userID` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` enum('student','staff') DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `userID`, `password`, `role`) VALUES
('staff 1', '56', '1234', 'staff'),
('Admin Staff', 'STAFF001', 'admin123', 'staff'),
('test', 'new', '1234', 'student'),
('alex', '12', 'alex12', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `pcinformation`
--
ALTER TABLE `pcinformation`
  ADD PRIMARY KEY (`pcID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `userID` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
