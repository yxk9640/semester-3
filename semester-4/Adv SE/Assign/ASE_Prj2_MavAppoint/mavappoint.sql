-- phpMyAdmin SQL Dump
-- version 4.9.11
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 05, 2023 at 02:11 AM
-- Server version: 5.7.41
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dmp4205_mavappoint`
--

-- --------------------------------------------------------

--
-- Table structure for table `advising_schedule`
--

CREATE TABLE `advising_schedule` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `studentId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `Id` int(11) NOT NULL,
  `advisor_userId` int(11) NOT NULL,
  `student_userId` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `studentId` int(11) NOT NULL,
  `student_email` varchar(50) DEFAULT NULL,
  `student_cell` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`Id`, `advisor_userId`, `student_userId`, `date`, `start`, `end`, `type`, `description`, `studentId`, `student_email`, `student_cell`) VALUES
(111, 20001, 3, '2023-04-04', '10:00:00', '12:00:00', 'graduate', 'advisinggg', 100234567, 'student.p@mavs.uta.edu', '123-456-7890'),
(12345, 20001, 2, '2023-04-06', '15:00:00', '16:30:00', 'graduate', 'graduation instructions', 1001234567, 'student.a@mavs.uta.edu', '123-456-7890');

-- --------------------------------------------------------

--
-- Table structure for table `appointment_types`
--

CREATE TABLE `appointment_types` (
  `userId` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `appointment_types`
--

INSERT INTO `appointment_types` (`userId`, `type`, `duration`) VALUES
(20001, 'completion of program', 30),
(20001, 'graduate', 60),
(20002, 'undergrad', 30);

-- --------------------------------------------------------

--
-- Table structure for table `degree_type`
--

CREATE TABLE `degree_type` (
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degree_type`
--

INSERT INTO `degree_type` (`name`) VALUES
('master');

-- --------------------------------------------------------

--
-- Table structure for table `degree_type_user`
--

CREATE TABLE `degree_type_user` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degree_type_user`
--

INSERT INTO `degree_type_user` (`name`, `userId`) VALUES
('master', 1);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`name`) VALUES
('business'),
('engg');

-- --------------------------------------------------------

--
-- Table structure for table `department_user`
--

CREATE TABLE `department_user` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department_user`
--

INSERT INTO `department_user` (`name`, `userId`) VALUES
('business', 20002),
('engg', 1),
('engg', 2),
('engg', 3),
('engg', 10),
('engg', 9999),
('engg', 20001),
('engg', 20003),
('engg', 20004);

-- --------------------------------------------------------

--
-- Table structure for table `major`
--

CREATE TABLE `major` (
  `name` varchar(45) NOT NULL,
  `dep_name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `major`
--

INSERT INTO `major` (`name`, `dep_name`) VALUES
('mba', 'business'),
('cse', 'engg');

-- --------------------------------------------------------

--
-- Table structure for table `Major_User`
--

CREATE TABLE `Major_User` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Major_User`
--

INSERT INTO `Major_User` (`name`, `userId`) VALUES
('cse', 1),
('cse', 2),
('cse', 3),
('cse', 10),
('cse', 20002),
('cse', 20003),
('cse', 20004),
('mba', 3);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `userId` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(32) DEFAULT NULL,
  `validated` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`userId`, `email`, `password`, `role`, `validated`) VALUES
(1, 'student@mavs.uta.edu', 'Student@123', 'student', 1),
(2, 'student.a@mavs.uta.edu', 'Student@123', 'student', 1),
(3, 'student.p@mavs.uta.edu', 'password', 'student', 0),
(10, 'student.d@mavs.uta.edu', 'Student@123', 'student', 1),
(9999, 'admin@mavappoint.edu', 'Admin123', 'admin', 1),
(20001, 'divya@abc.com', 'Advisor123', 'advisor', 1),
(20002, 'advisor2@uta.edu', 'Advisor123', 'advisor', 1),
(20003, 'divya@xyz.com', 'Divya123', 'student', 1),
(20004, 'advisor3@uta.edu', '#9ym3wqb!c', 'advisor', 0);

-- --------------------------------------------------------

--
-- Table structure for table `User_Advisor`
--

CREATE TABLE `User_Advisor` (
  `userId` int(11) NOT NULL,
  `pName` varchar(32) NOT NULL,
  `notification` varchar(45) NOT NULL,
  `name_low` varchar(2) NOT NULL,
  `name_high` varchar(2) NOT NULL,
  `degree_types` int(11) NOT NULL,
  `cutOffTime` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User_Advisor`
--

INSERT INTO `User_Advisor` (`userId`, `pName`, `notification`, `name_low`, `name_high`, `degree_types`, `cutOffTime`) VALUES
(20001, 'Advisor1', 'yes', 'A', 'Z', 1, '4'),
(20002, 'Advisor2', 'yes', 'A', 'Z', 2, '4'),
(20004, 'adv3', 'yes', 'A', 'P', 2, '0');

-- --------------------------------------------------------

--
-- Table structure for table `User_Student`
--

CREATE TABLE `User_Student` (
  `userId` int(11) NOT NULL,
  `student_Id` int(11) NOT NULL,
  `degree_type` int(11) NOT NULL,
  `phone_num` varchar(45) NOT NULL,
  `last_name_initial` varchar(2) NOT NULL,
  `notification` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User_Student`
--

INSERT INTO `User_Student` (`userId`, `student_Id`, `degree_type`, `phone_num`, `last_name_initial`, `notification`) VALUES
(1, 10001, 1, '1002003004', 'P', NULL),
(2, 1001234567, 1, '123-456-7890', 'A', 'yes'),
(3, 1001234567, 2, '123-456-7890', 'P', 'yes'),
(10, 1009384756, 2, '123-456-7890', 'P', 'yes'),
(20003, 1002024567, 2, '123-456-7890', 'P', 'yes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advising_schedule`
--
ALTER TABLE `advising_schedule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userId` (`userId`,`date`,`start`);

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `advisor_userId` (`advisor_userId`,`type`),
  ADD KEY `student_userId` (`student_userId`);

--
-- Indexes for table `appointment_types`
--
ALTER TABLE `appointment_types`
  ADD PRIMARY KEY (`userId`,`type`),
  ADD UNIQUE KEY `userId` (`userId`,`type`);

--
-- Indexes for table `degree_type`
--
ALTER TABLE `degree_type`
  ADD PRIMARY KEY (`name`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `degree_type_user`
--
ALTER TABLE `degree_type_user`
  ADD PRIMARY KEY (`name`,`userId`),
  ADD UNIQUE KEY `userId` (`userId`,`name`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`name`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `department_user`
--
ALTER TABLE `department_user`
  ADD PRIMARY KEY (`name`,`userId`),
  ADD UNIQUE KEY `userId` (`userId`,`name`);

--
-- Indexes for table `major`
--
ALTER TABLE `major`
  ADD PRIMARY KEY (`name`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `dep_name` (`dep_name`);

--
-- Indexes for table `Major_User`
--
ALTER TABLE `Major_User`
  ADD PRIMARY KEY (`name`,`userId`),
  ADD UNIQUE KEY `userId` (`userId`,`name`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `User_Advisor`
--
ALTER TABLE `User_Advisor`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `userId` (`userId`);

--
-- Indexes for table `User_Student`
--
ALTER TABLE `User_Student`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `userId` (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `advising_schedule`
--
ALTER TABLE `advising_schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=175;

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12346;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20005;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `advising_schedule`
--
ALTER TABLE `advising_schedule`
  ADD CONSTRAINT `advising_schedule_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User_Advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`advisor_userId`) REFERENCES `User_Advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`advisor_userId`,`type`) REFERENCES `appointment_types` (`userId`, `type`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`student_userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `appointment_types`
--
ALTER TABLE `appointment_types`
  ADD CONSTRAINT `appointment_types_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User_Advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `degree_type_user`
--
ALTER TABLE `degree_type_user`
  ADD CONSTRAINT `degree_type_user_ibfk_1` FOREIGN KEY (`name`) REFERENCES `degree_type` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `degree_type_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `department_user`
--
ALTER TABLE `department_user`
  ADD CONSTRAINT `department_user_ibfk_1` FOREIGN KEY (`name`) REFERENCES `department` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `department_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `major`
--
ALTER TABLE `major`
  ADD CONSTRAINT `major_ibfk_1` FOREIGN KEY (`dep_name`) REFERENCES `department` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Major_User`
--
ALTER TABLE `Major_User`
  ADD CONSTRAINT `Major_User_ibfk_1` FOREIGN KEY (`name`) REFERENCES `major` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Major_User_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `User_Advisor`
--
ALTER TABLE `User_Advisor`
  ADD CONSTRAINT `User_Advisor_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `User_Student`
--
ALTER TABLE `User_Student`
  ADD CONSTRAINT `User_Student_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
