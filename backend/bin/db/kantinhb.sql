-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2023 at 05:05 PM
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
-- Database: `kantinhb`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `id_user` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`id_user`, `id_item`, `quantity`) VALUES
(4, 6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `image`) VALUES
(1, 'Food', 'https://static.thenounproject.com/png/1633691-200.png'),
(2, 'Drink', 'https://static.thenounproject.com/png/2578-200.png');

-- --------------------------------------------------------

--
-- Table structure for table `detailed_transactions`
--

CREATE TABLE `detailed_transactions` (
  `id_transaction` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detailed_transactions`
--

INSERT INTO `detailed_transactions` (`id_transaction`, `id_item`, `quantity`) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 3),
(3, 2, 1),
(3, 5, 2),
(3, 6, 1),
(4, 4, 2),
(5, 7, 3),
(6, 1, 1),
(6, 3, 2),
(7, 5, 1),
(7, 6, 1),
(7, 7, 1),
(8, 1, 2),
(8, 2, 1),
(9, 3, 5),
(9, 5, 3),
(10, 3, 8);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `price` int(11) NOT NULL,
  `description` text NOT NULL,
  `image` text NOT NULL,
  `id_category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `name`, `price`, `description`, `image`, `id_category`) VALUES
(1, 'Nasi Rempah', 15000, 'Nasi yang diolah dengan bumbu-bumbu yang khas Indonesia.', 'https://dcostseafood.id/wp-content/uploads/2023/11/Nasi-Goreng-Hejo.jpg', 1),
(2, 'Ayam Mentega', 20000, 'Daging ayam yang dipotong kecil-kecil kemudian dimasak dengan bambu.', 'https://dcostseafood.id/wp-content/uploads/2021/12/Ayam-Goreng-Mentega.jpg', 1),
(3, 'Mie Ayam', 12000, 'Mie dengan potongan ayam dan sayuran.', 'https://dcostseafood.id/wp-content/uploads/2022/04/Mie-Godog.jpg', 1),
(4, 'Nasi Uduk', 10000, 'Nasi yang dimasak dengan santan dan rempah-rempah.', 'https://dcostseafood.id/wp-content/uploads/2023/04/Nasi-Goreng-spesial-2.jpg', 1),
(5, 'Ayam Goreng', 15000, 'Potongan ayam yang digoreng hingga renyah.', 'https://dcostseafood.id/wp-content/uploads/2021/12/Ayam-Rempah.jpg', 1),
(6, 'Teh Manis', 5000, 'Minuman teh yang diberi gula.', 'https://dcostseafood.id/wp-content/uploads/2021/12/Teh-tawar-manis.jpg', 2),
(7, 'Es Jeruk', 10000, 'Minuman segar yang terbuat dari jeruk yang dicampur dengan es.', 'https://dcostseafood.id/wp-content/uploads/2021/12/ES-JERUK-murni.jpg', 2);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `status` text NOT NULL,
  `method` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `id_user`, `datetime`, `status`, `method`) VALUES
(1, 1, '2023-04-27 10:00:00', 'pending', 'OVO'),
(2, 2, '2023-04-27 11:00:00', 'completed', 'ShopeePay'),
(3, 3, '2023-04-27 12:00:00', 'cancelled', 'GoPay'),
(4, 4, '2023-04-27 13:00:00', 'completed', 'OVO'),
(5, 5, '2023-04-27 14:00:00', 'pending', 'ShopeePay'),
(6, 6, '2023-04-27 15:00:00', 'cancelled', 'GoPay'),
(7, 7, '2023-04-27 16:00:00', 'completed', 'OVO'),
(8, 1, '2023-04-27 18:30:58', 'pending', 'GoPay'),
(9, 1, '2023-04-27 18:35:07', 'pending', 'GoPay'),
(10, 1, '2023-04-27 18:42:30', 'pending', 'OVO');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `admin`) VALUES
(1, 'Agus', 'agus@example.com', 'password123', 0),
(2, 'Budi', 'budi@example.com', 'password456', 1),
(3, 'Citra', 'citra@example.com', 'password789', 1),
(4, 'Dian', 'dian@example.com', 'password123', 0),
(5, 'Eka', 'eka@example.com', 'password456', 1),
(6, 'Fajar', 'fajar@example.com', 'password789', 0),
(7, 'Gita', 'gita@example.com', 'password123', 0);

-- --------------------------------------------------------

--
-- Table structure for table `wishlist`
--

CREATE TABLE `wishlist` (
  `id_user` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wishlist`
--

INSERT INTO `wishlist` (`id_user`, `id_item`, `datetime`) VALUES
(1, 4, '2023-04-27 15:30:00'),
(1, 6, '2023-04-27 15:31:00'),
(2, 2, '2023-04-27 16:00:00'),
(2, 5, '2023-04-27 16:01:00'),
(3, 1, '2023-04-27 16:30:00'),
(3, 7, '2023-04-27 16:31:00'),
(4, 3, '2023-04-27 17:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id_user`,`id_item`),
  ADD KEY `id_item` (`id_item`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `detailed_transactions`
--
ALTER TABLE `detailed_transactions`
  ADD PRIMARY KEY (`id_transaction`,`id_item`),
  ADD KEY `id_item` (`id_item`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_category` (`id_category`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`id_user`,`id_item`),
  ADD KEY `id_item` (`id_item`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`id_item`) REFERENCES `items` (`id`);

--
-- Constraints for table `detailed_transactions`
--
ALTER TABLE `detailed_transactions`
  ADD CONSTRAINT `detailed_transactions_ibfk_1` FOREIGN KEY (`id_transaction`) REFERENCES `transactions` (`id`),
  ADD CONSTRAINT `detailed_transactions_ibfk_2` FOREIGN KEY (`id_item`) REFERENCES `items` (`id`);

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `items_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Constraints for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`id_item`) REFERENCES `items` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
