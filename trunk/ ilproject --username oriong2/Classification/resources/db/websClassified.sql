CREATE DATABASE  IF NOT EXISTS `websclassified` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `webclassified`;
-- MySQL dump 10.13  Distrib 5.1.40, for Win32 (ia32)
--
-- Host: localhost    Database: websclassified
-- ------------------------------------------------------
-- Server version	5.5.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `scored_intermedied`
--

DROP TABLE IF EXISTS `scored_intermedied`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scored_intermedied` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2000) DEFAULT NULL,
  `indextype` int(11) DEFAULT NULL,
  `category` varchar(300) DEFAULT NULL,
  `score` mediumtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idscored_intermedied_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2648 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `urls_classified`
--

DROP TABLE IF EXISTS `urls_classified`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urls_classified` (
  `id` int(11) NOT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `category` varchar(300) DEFAULT NULL,
  `score` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `urls_rastreated`
--

DROP TABLE IF EXISTS `urls_rastreated`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urls_rastreated` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `URL` text,
  `CATEGORYTYPE` int(11) DEFAULT NULL,
  `STATE` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idurls_classified_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2148 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-01-30  1:26:20
