-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 22, 2022 at 05:57 PM
-- Server version: 5.7.24
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE techno;
USE techno;

-- --------------------------------------------------------

--
-- Table structure for table `deliveries`
--

CREATE TABLE `deliveries` (
  `delivery_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `delivery_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `deliveries`
--

INSERT INTO `deliveries` (`delivery_id`, `product_id`, `order_id`, `employee_id`, `quantity`, `delivery_time`) VALUES
(116, 114, 1, 3, 1, '2021-12-28 02:15:07'),
(117, 113, 2, 3, 1, '2021-12-28 02:19:25'),
(118, 113, 3, 3, 1, '2021-12-28 02:22:52'),
(119, 111, 4, 3, 1, '2021-12-28 10:51:01'),
(120, 113, 5, 3, 1, '2021-12-28 14:11:22'),
(121, 113, 2, 3, 1, '2022-03-11 13:33:42'),
(122, 122, 6, 3, 1, '2022-03-11 13:48:12');

-- --------------------------------------------------------

--
-- Table structure for table `deliveryinprogress`
--

CREATE TABLE `deliveryinprogress` (
  `delivery_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `position` varchar(20) NOT NULL,
  `salary` decimal(8,2) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `image` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `name`, `surname`, `address`, `birthdate`, `position`, `salary`, `username`, `password`, `image`) VALUES
(1, 'Mark', 'Jones', '68, Mesa Grande', '1990-01-01', 'director', '50000.00', 'dir', '123', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\pic2.jpg'),
(2, 'Maria', 'Thomp', '24, Wale Road', '1997-07-01', 'worker', '30000.00', 'worker', 'w123', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\pic4.jpg'),
(3, 'Garrett', 'Benso', '75, High Road', '1992-08-02', 'deliveryman', '30000.00', 'del', 'd123', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\pic3.jpg'),
(10, 'Nazima', 'bEKJANOVA', 'dJAL', '2001-02-02', 'Employee', '40000.00', 'log', 'pass', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\defaultavatar.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `customer_name` varchar(15) DEFAULT NULL,
  `phonenumber` int(11) DEFAULT NULL,
  `mail` varchar(60) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `product_id`, `customer_name`, `phonenumber`, `mail`, `address`, `quantity`, `order_time`) VALUES
(1, 114, 'John Jones', 500400800, 'john@gmail.com', '34, strasse', 0, '2021-12-28 02:14:23'),
(2, 113, 'John Jones', 500400600, 'john@gmail.com', '54, Strasse', 0, '2021-12-28 02:18:18'),
(3, 113, 'Mark Wilson', 500400800, 'mark@gmail.com', '34, Strasse', 0, '2021-12-28 02:21:19'),
(4, 111, 'John Jones', 500444555, 'john@gmail.com', '67, Greenstrasse', 0, '2021-12-28 10:50:08'),
(5, 113, 'John', 500555666, 'john@gmail.com', '54, street', 0, '2021-12-28 14:10:00'),
(6, 122, 'John Jones', 555444555, 'john@mail.com', '45, Maldybaeva', 0, '2022-03-10 14:42:00'),
(7, 122, 'John Jones', 555123555, 'john@gmail.com', 'Sovetskaya 45', 0, '2022-03-11 12:53:23'),
(8, 118, 'John Jones', 555444555, 'john@gmail.com', 'Pravda 45', 1, '2022-03-11 13:45:48');

-- --------------------------------------------------------

--
-- Table structure for table `price_change`
--

CREATE TABLE `price_change` (
  `product_id` int(11) NOT NULL,
  `change_date` date DEFAULT NULL,
  `new_price` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `model` varchar(60) NOT NULL,
  `factory` varchar(40) NOT NULL,
  `category` varchar(60) NOT NULL,
  `year` year(4) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `description` text,
  `image` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `name`, `model`, `factory`, `category`, `year`, `quantity`, `price`, `discount`, `description`, `image`) VALUES
(111, 'Teekanne', 'KFN 13923 DE edt/cs', 'Miele', 'Kitchen Appliances', 2020, 0, '55000.00', 0, 'Color: Black\nWeight, kg: 9.5\nTimer: No\nDisplay: Yes\nType of coffee: Ground and grain\nHeight, cm: 44\nWidth, cm: 24\nDepth, cm: 36\nPower, W: 1450', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\thumb_39hq.jpg'),
(112, 'Washing machine', 'IWUB 4105 (CIS)', 'Indesit ', 'Clothe Care', 2021, 1, '50000.00', 5, 'Installation: free-standing removable cover for embedding\r\nLoading Type: Frontal\r\nMaximum load of laundry: 4 kg\r\nDrying: No\r\nControl: electronic (intelligent)\r\nDimensions (WxDxH): 60 x 33 x 85 cm\r\nColor: White', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\stiral_ny_e_mashiny__avtomat.jpg'),
(113, 'Mixer', 'ECAM-350.55B', 'Delonghi', 'Kitchen Applinces', 2021, 5, '30000.00', 5, 'Color: Black\nWeight, kg: 9.5\nTimer: No\nDisplay: Yes\nType of coffee: Ground and grain\nHeight, cm: 44\nWidth, cm: 24\nDepth, cm: 36\nPower, W: 1450', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\thumb_mikser-sinbo-smx-2724-2.jpg'),
(114, 'Fridge-freezer', 'KFN 13923 DE edt/cs', 'Miele', 'Kitchen Appliances', 2020, 1, '55000.00', 0, 'color: white / blue \nWeight: 25kg \nWash weight: 7kg \nSpeed: 1200 rpm \nHeight: 120cm \nWidth: 50cm', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\thumb_176white.jpg'),
(118, 'Washing machine', 'WW70J52E02WDLD', 'SAMSUNG', 'Clothe care', 2020, 3, '38998.00', 3, 'color: white / blue \nWeight: 25kg \nWash weight: 7kg \nSpeed: 1200 rpm \nHeight: 120cm \nWidth: 50cm', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\sushil_ny_e_mashiny__avtomat.jpg'),
(120, 'name', 'model', 'factory', 'categort', 2021, 10, '3999.00', 10, '', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png'),
(121, 'washing machine', 'djfd', 'samsung', 'kitchen appliance ', 2014, 4, '3000.00', 5, 'Great appliance', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png'),
(122, 'mIXER', 'JKHBXJSBC', 'eCAM', '', 2021, 8, '1000.00', 10, '', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png'),
(123, 'Name', 'model', 'factory', 'categoty', 2020, 4, '10000.00', 0, '', 'D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `customer_name` varchar(15) DEFAULT NULL,
  `employee_id` int(11) NOT NULL,
  `delivery` tinyint(1) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double(9,2) NOT NULL,
  `sale_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`sale_id`, `product_id`, `order_id`, `customer_name`, `employee_id`, `delivery`, `quantity`, `total_price`, `sale_time`) VALUES
(1, 114, NULL, 'Guest', 2, 0, 1, 55000.00, '2021-12-28 02:09:05'),
(3, 113, 2, 'John Jones', 3, 1, 1, 30000.00, '2021-12-28 02:19:25'),
(4, 113, 3, 'Mark Wilson', 3, 1, 1, 30000.00, '2021-12-28 02:22:52'),
(5, 111, 4, 'John Jones', 3, 1, 1, 55000.00, '2021-12-28 10:51:01'),
(6, 118, NULL, 'Guest', 2, 0, 1, 38998.00, '2021-12-28 14:08:44'),
(7, 113, 5, 'John', 3, 1, 1, 30000.00, '2021-12-28 14:11:22'),
(8, 111, NULL, 'Guest', 2, 0, 1, 55000.00, '2022-03-10 14:39:57'),
(9, 121, NULL, 'Guest', 2, 0, 1, 3000.00, '2022-03-11 12:46:57'),
(10, 113, 2, 'John Jones', 3, 1, 1, 30000.00, '2022-03-11 13:33:42'),
(11, 123, NULL, 'Guest', 2, 0, 5, 50000.00, '2022-03-11 13:44:32'),
(12, 122, 6, 'John Jones', 3, 1, 1, 1000.00, '2022-03-11 13:48:12'),
(13, 123, NULL, 'Guest', 2, 0, 1, 10000.00, '2022-03-22 23:45:24');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `deliveries`
--
ALTER TABLE `deliveries`
  ADD PRIMARY KEY (`delivery_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `deliveryinprogress`
--
ALTER TABLE `deliveryinprogress`
  ADD PRIMARY KEY (`delivery_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `ORDPRODFK` (`product_id`);

--
-- Indexes for table `price_change`
--
ALTER TABLE `price_change`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`sale_id`),
  ADD KEY `SALEPRODFK` (`product_id`),
  ADD KEY `SALEEMPFK` (`employee_id`),
  ADD KEY `SALEORDFK` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `deliveries`
--
ALTER TABLE `deliveries`
  MODIFY `delivery_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- AUTO_INCREMENT for table `deliveryinprogress`
--
ALTER TABLE `deliveryinprogress`
  MODIFY `delivery_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `price_change`
--
ALTER TABLE `price_change`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `sale_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `deliveryinprogress`
--
ALTER TABLE `deliveryinprogress`
  ADD CONSTRAINT `deliveryinprogress_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `deliveryinprogress_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `ORDPRODFK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `price_change`
--
ALTER TABLE `price_change`
  ADD CONSTRAINT `price_change_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION;

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `SALEEMPFK` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE NO ACTION,
  ADD CONSTRAINT `SALEORDFK` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION,
  ADD CONSTRAINT `SALEPRODFK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
