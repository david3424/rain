-- MySQL dump 10.13  Distrib 5.6.20, for osx10.8 (x86_64)
--
-- Host: localhost    Database: event
-- ------------------------------------------------------
-- Server version	5.6.20-enterprise-commercial-advanced

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
-- Table structure for table `d_sys_dic`
--

DROP TABLE IF EXISTS `d_sys_dic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `d_sys_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(4) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `createtime` varchar(100) DEFAULT NULL,
  `privatekey` varchar(50) NOT NULL,
  `status` int(11) DEFAULT '0',
  `notes` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `userid` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_app` (`appid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `d_sys_dic`
--

LOCK TABLES `d_sys_dic` WRITE;
/*!40000 ALTER TABLE `d_sys_dic` DISABLE KEYS */;
INSERT INTO `d_sys_dic` VALUES (1,1000,'127.0.0.1','2015-03-05 22:27:08','Nk0qY3NJdE1cUjkp\n',-1,'测试','战三国',1),(3,1001,'127.0.0.1','2015-03-05 22:28:41','YUp6eSpkSFNmJkdv\n',-1,'测试again','Q游记',1),(4,1003,'127.0.0.1','2015-03-05 22:29:42','Rnc3LTJIbW5BU2hQ\n',-1,'测试again and again','热舞touch',1);
/*!40000 ALTER TABLE `d_sys_dic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hd_lottery_baseinfo`
--

DROP TABLE IF EXISTS `hd_lottery_baseinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hd_lottery_baseinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prizetable` varchar(50) NOT NULL,
  `accuracy` int(11) DEFAULT NULL,
  `description` text,
  `status` int(2) DEFAULT NULL,
  `level` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hd_lottery_baseinfo`
--

LOCK TABLES `hd_lottery_baseinfo` WRITE;
/*!40000 ALTER TABLE `hd_lottery_baseinfo` DISABLE KEYS */;
INSERT INTO `hd_lottery_baseinfo` VALUES (1,'lottery_demo_prize',100,'基本配置',0,0);
/*!40000 ALTER TABLE `hd_lottery_baseinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hd_lottery_prizesinfo`
--

DROP TABLE IF EXISTS `hd_lottery_prizesinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hd_lottery_prizesinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outputid` int(11) DEFAULT '0',
  `prizeid` int(11) NOT NULL,
  `prizename` varchar(50) NOT NULL,
  `prizetable` varchar(50) DEFAULT NULL,
  `totallimit` int(11) DEFAULT '0',
  `steplimit` int(11) DEFAULT '0',
  `todaylimit` int(11) DEFAULT '0',
  `probability` int(11) DEFAULT '0',
  `description` varchar(50) DEFAULT NULL,
  `createtime` varchar(30) DEFAULT NULL,
  `defaultstatus` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hd_lottery_prizesinfo`
--

LOCK TABLES `hd_lottery_prizesinfo` WRITE;
/*!40000 ALTER TABLE `hd_lottery_prizesinfo` DISABLE KEYS */;
INSERT INTO `hd_lottery_prizesinfo` VALUES (1,0,10000,'测试礼包1','lottery_demo_prize',10,10,2,10,'','',0),(2,0,10001,'测试礼包2','lottery_demo_prize',10,10,2,10,'','',0),(3,0,10002,'测试礼包3','lottery_demo_prize',10,10,2,30,'','',0),(4,0,10003,'测试礼包4','lottery_demo_prize',10,10,2,50,'','',0);
/*!40000 ALTER TABLE `hd_lottery_prizesinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_demo_exchange_prize`
--

DROP TABLE IF EXISTS `lottery_demo_exchange_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_demo_exchange_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT '-1',
  `username` varchar(50) NOT NULL,
  `server` int(11) DEFAULT '0',
  `servername` varchar(30) NOT NULL,
  `prizepool` varchar(20) DEFAULT NULL,
  `roleid` varchar(30) NOT NULL,
  `rolename` varchar(50) NOT NULL,
  `prizename` varchar(50) NOT NULL,
  `prize` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `date` varchar(20) DEFAULT '',
  `createtime` varchar(30) DEFAULT '',
  `ip` varchar(30) DEFAULT '',
  `status` int(11) DEFAULT '11',
  `gid` varchar(250) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_username_prize` (`username`,`prize`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_demo_exchange_prize`
--

LOCK TABLES `lottery_demo_exchange_prize` WRITE;
/*!40000 ALTER TABLE `lottery_demo_exchange_prize` DISABLE KEYS */;
/*!40000 ALTER TABLE `lottery_demo_exchange_prize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_demo_lotterytimes`
--

DROP TABLE IF EXISTS `lottery_demo_lotterytimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_demo_lotterytimes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `signtimes` int(11) DEFAULT '0',
  `consumetimes` int(11) DEFAULT '0',
  `signdate` varchar(20) DEFAULT NULL,
  `status` int(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_username` (`username`),
  KEY `signdate` (`signdate`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_demo_lotterytimes`
--

LOCK TABLES `lottery_demo_lotterytimes` WRITE;
/*!40000 ALTER TABLE `lottery_demo_lotterytimes` DISABLE KEYS */;
INSERT INTO `lottery_demo_lotterytimes` VALUES (1,'david3424',3,3,'2015-10-20',0);
/*!40000 ALTER TABLE `lottery_demo_lotterytimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_demo_prize`
--

DROP TABLE IF EXISTS `lottery_demo_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_demo_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT '-1',
  `username` varchar(50) NOT NULL,
  `server` int(11) DEFAULT '0',
  `servername` varchar(30) NOT NULL,
  `prizepool` varchar(20) NOT NULL,
  `roleid` varchar(30) NOT NULL,
  `rolename` varchar(50) NOT NULL,
  `prizename` varchar(50) NOT NULL,
  `prize` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `date` varchar(20) DEFAULT '',
  `createtime` varchar(30) DEFAULT '',
  `ip` varchar(30) DEFAULT '',
  `status` int(11) DEFAULT '11',
  `gid` varchar(250) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_demo_prize`
--

LOCK TABLES `lottery_demo_prize` WRITE;
/*!40000 ALTER TABLE `lottery_demo_prize` DISABLE KEYS */;
INSERT INTO `lottery_demo_prize` VALUES (5,1111,'cindy3424',3305,'风叶','defaultPool','1990980375','dddd','测试礼包1',10000,0,'2016-03-21','2016-03-21 17:58:24','127.0.0.1',0,''),(6,1111,'cindy3424',3305,'风叶','defaultPool','1990980375','dddd','测试礼包1',10000,0,'2016-03-21','2016-03-21 18:01:59','127.0.0.1',0,''),(7,1111,'cindy3424',3305,'风叶','defaultPool','1990980375','dddd','果实',8707,0,'2016-03-21','2016-03-21 18:19:32','127.0.0.1',0,''),(8,1111,'cindy3424',3305,'风叶','defaultPool','1990980375','dddd','测试礼包3',10002,0,'2016-03-22','2016-03-22 14:33:58','127.0.0.1',0,''),(9,1111,'cindy3424',3305,'风叶','defaultPool','1990980375','dddd','测试礼包3',10002,0,'2016-03-22','2016-03-22 14:36:17','127.0.0.1',0,''),(10,1111,'david3425',3305,'风叶','defaultPool','1990980375','dddd','测试礼包4',10003,0,'2016-03-22','2016-03-22 14:36:29','127.0.0.1',0,''),(11,1111,'david3425',3305,'风叶','defaultPool','1990980375','dddd','测试礼包4',10003,0,'2016-03-22','2016-03-22 14:36:40','127.0.0.1',0,''),(12,1111,'david3425',3305,'风叶','defaultPool','1990980375','dddd','奇花',9898,0,'2016-03-22','2016-03-22 14:38:35','127.0.0.1',0,''),(13,1111,'david3425',3305,'风叶','defaultPool','1990980375','dddd','果实',8707,0,'2016-03-22','2016-03-22 14:39:20','127.0.0.1',0,''),(14,1111,'david3425',3305,'风叶','defaultPool','1990980375','dddd','果实',8707,0,'2016-03-22','2016-03-22 14:49:55','127.0.0.1',0,'');
/*!40000 ALTER TABLE `lottery_demo_prize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_demo_signlog`
--

DROP TABLE IF EXISTS `lottery_demo_signlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_demo_signlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` varchar(30) DEFAULT '',
  `signcode` int(11) DEFAULT '0',
  `ip` varchar(30) DEFAULT '',
  `phase` int(11) DEFAULT '0',
  `status` int(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_username_date` (`username`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_demo_signlog`
--

LOCK TABLES `lottery_demo_signlog` WRITE;
/*!40000 ALTER TABLE `lottery_demo_signlog` DISABLE KEYS */;
INSERT INTO `lottery_demo_signlog` VALUES (2,'david3424','2015-10-20','2015-10-23 15:59:40',0,'127.0.0.1',0,0),(3,'david3424','2015-10-21','2015-10-23 16:00:32',1,'127.0.0.1',0,0),(4,'david3424','2016-03-21','2016-03-21 18:34:09',4,'127.0.0.1',0,0);
/*!40000 ALTER TABLE `lottery_demo_signlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_demo_user`
--

DROP TABLE IF EXISTS `lottery_demo_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_demo_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `server` int(11) DEFAULT '0',
  `servername` varchar(30) NOT NULL,
  `roleid` varchar(30) NOT NULL,
  `rolename` varchar(50) NOT NULL,
  `ip` varchar(50) DEFAULT '',
  `status` int(11) DEFAULT '0',
  `createtime` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_demo_user`
--

LOCK TABLES `lottery_demo_user` WRITE;
/*!40000 ALTER TABLE `lottery_demo_user` DISABLE KEYS */;
INSERT INTO `lottery_demo_user` VALUES (10,'david3424',3305,'风叶','1990980375','dddd',NULL,0,'2015-10-16 15:35:06'),(11,'david3425',3305,'风叶','1990980375','dddd',NULL,0,'2015-10-16 15:35:06'),(12,'david3426',3305,'风叶','1990980375','dddd',NULL,0,'2015-10-16 15:35:06'),(13,'david3427',3305,'风叶','1990980375','dddd',NULL,0,'2015-10-16 15:35:06'),(14,'david3428',3305,'风叶','1990980375','dddd',NULL,0,'2015-10-16 15:35:06');
/*!40000 ALTER TABLE `lottery_demo_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sendprize_local_logs`
--

DROP TABLE IF EXISTS `sendprize_local_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sendprize_local_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(150) DEFAULT NULL,
  `tablename` varchar(150) DEFAULT NULL,
  `server` int(11) DEFAULT '0',
  `roleid` bigint(20) DEFAULT NULL,
  `flag` int(4) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `pid` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_username` (`username`(3)),
  KEY `index_pid` (`pid`),
  KEY `pid` (`pid`,`gid`),
  KEY `pid_2` (`pid`,`tablename`(40))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sendprize_local_logs`
--

LOCK TABLES `sendprize_local_logs` WRITE;
/*!40000 ALTER TABLE `sendprize_local_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `sendprize_local_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_task`
--

DROP TABLE IF EXISTS `ss_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_task`
--

LOCK TABLES `ss_task` WRITE;
/*!40000 ALTER TABLE `ss_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `ss_task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-13 14:48:35
