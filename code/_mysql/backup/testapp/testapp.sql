-- MySQL dump 10.11
--
-- Host: localhost    Database: testapp
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

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
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `images` (
  `ID` varchar(255) NOT NULL,
  `GALLERY` varchar(255) default NULL,
  `IMAGE` tinyblob,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_sku`
--

DROP TABLE IF EXISTS `md_sku`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_sku` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `photo` tinyblob,
  `price` decimal(19,2) default NULL,
  `uom_id` varchar(32) NOT NULL,
  `vendor_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  KEY `FK874FD655B6473876` (`vendor_id`),
  KEY `FK874FD655B1DEBE9E` (`uom_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_sku`
--

LOCK TABLES `md_sku` WRITE;
/*!40000 ALTER TABLE `md_sku` DISABLE KEYS */;
/*!40000 ALTER TABLE `md_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_uom`
--

DROP TABLE IF EXISTS `md_uom`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_uom` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `category` varchar(2) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_uom`
--

LOCK TABLES `md_uom` WRITE;
/*!40000 ALTER TABLE `md_uom` DISABLE KEYS */;
/*!40000 ALTER TABLE `md_uom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_vendor`
--

DROP TABLE IF EXISTS `md_vendor`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_vendor` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `leadTimeDays` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_vendor`
--

LOCK TABLES `md_vendor` WRITE;
/*!40000 ALTER TABLE `md_vendor` DISABLE KEYS */;
/*!40000 ALTER TABLE `md_vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prd`
--

DROP TABLE IF EXISTS `po_prd`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prd` (
  `dtlid` varchar(32) NOT NULL,
  `qty` decimal(19,2) default NULL,
  `requireDate` datetime default NULL,
  `billHead_id` varchar(32) default NULL,
  `sku_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`dtlid`),
  KEY `FK8D095842AE404A5E` (`sku_id`),
  KEY `FK8D095842437CC488` (`billHead_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prd`
--

LOCK TABLES `po_prd` WRITE;
/*!40000 ALTER TABLE `po_prd` DISABLE KEYS */;
/*!40000 ALTER TABLE `po_prd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prh`
--

DROP TABLE IF EXISTS `po_prh`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prh` (
  `id` varchar(32) NOT NULL,
  `billNo` varchar(32) default NULL,
  `version` datetime default NULL,
  `applicant` varchar(64) default NULL,
  `applyDate` datetime default NULL,
  `department` varchar(64) default NULL,
  `reason` varchar(64) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `billNo` (`billNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prh`
--

LOCK TABLES `po_prh` WRITE;
/*!40000 ALTER TABLE `po_prh` DISABLE KEYS */;
/*!40000 ALTER TABLE `po_prh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prr`
--

DROP TABLE IF EXISTS `po_prr`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prr` (
  `dtlid` varchar(32) NOT NULL,
  `remark` varchar(255) default NULL,
  `type` int(11) default NULL,
  `billHead_id` varchar(32) default NULL,
  PRIMARY KEY  (`dtlid`),
  KEY `FK8D095850437CC488` (`billHead_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prr`
--

LOCK TABLES `po_prr` WRITE;
/*!40000 ALTER TABLE `po_prr` DISABLE KEYS */;
/*!40000 ALTER TABLE `po_prr` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-29 20:49:55
