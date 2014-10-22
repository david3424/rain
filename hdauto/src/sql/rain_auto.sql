-- MySQL dump 10.13  Distrib 5.1.41, for Win32 (ia32)
--
-- Host: localhost    Database: prizesend
-- ------------------------------------------------------
-- Server version	5.1.41-community-log

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
-- Table structure for table `auto_game_prize_roleid_newlogs_new`
--

DROP TABLE IF EXISTS `auto_game_prize_roleid_newlogs_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_game_prize_roleid_newlogs_new` (
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
-- Dumping data for table `auto_game_prize_roleid_newlogs_new`
--

LOCK TABLES `auto_game_prize_roleid_newlogs_new` WRITE;
/*!40000 ALTER TABLE `auto_game_prize_roleid_newlogs_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `auto_game_prize_roleid_newlogs_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_send_prize_properties`
--

DROP TABLE IF EXISTS `event_send_prize_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_send_prize_properties` (
  `table_name` varchar(100) NOT NULL DEFAULT '',
  `datasource` varchar(16) NOT NULL,
  `role_id_type` int(11) NOT NULL,
  `send_type` int(11) NOT NULL COMMENT '1:∆’Õ®∑¢Ω±£¨2£∫Õ≈π∫∑¢Ω±',
  `send_interface` int(11) NOT NULL,
  `send_check` int(11) NOT NULL,
  `prizememo` varchar(128) DEFAULT NULL,
  `create_time` varchar(20) NOT NULL,
  `status` int(11) NOT NULL,
  `consume_type` int(11) DEFAULT '2',
  PRIMARY KEY (`table_name`,`datasource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_send_prize_properties`
--

LOCK TABLES `event_send_prize_properties` WRITE;
/*!40000 ALTER TABLE `event_send_prize_properties` DISABLE KEYS */;
INSERT INTO `event_send_prize_properties` VALUES ('kefu_sendprize_temp_1','huodong161',2,1,1,0,NULL,'2013-10-30 15:07:16',1,1),('kefu_sendprize_temp_2','huodong161',2,1,1,0,NULL,'2013-10-30 15:07:31',1,1),('kefu_sendprize_temp_3','huodong161',2,1,1,0,NULL,'2013-10-30 15:07:43',1,1),('kefu_sendprize_temp_4','huodong161',2,1,1,0,NULL,'2013-11-21 16:54:17',1,1),('kefu_sendprize_temp_5','huodong161',2,1,1,0,NULL,'2013-10-30 15:08:11',1,1),('kefu_sendprize_temp_6','huodong161',1,1,1,0,NULL,'2013-10-30 15:08:30',1,1),('kefu_sendprize_temp_7','huodong161',1,1,1,0,NULL,'2013-10-30 15:08:40',1,1);
/*!40000 ALTER TABLE `event_send_prize_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hd_sendprize_user`
--

DROP TABLE IF EXISTS `hd_sendprize_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hd_sendprize_user` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `actname` varchar(50) DEFAULT NULL,
  `managername` varchar(50) DEFAULT NULL,
  `prizelist` text,
  `serverlist` varchar(255) DEFAULT NULL,
  `gamename` varchar(50) DEFAULT NULL,
  `totalnum` int(4) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `tablename` varchar(100) DEFAULT NULL,
  `baktablename` varchar(100) DEFAULT NULL,
  `ordertype` varchar(50) DEFAULT NULL,
  `status` int(4) DEFAULT '0',
  `type` int(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hd_sendprize_user`
--

LOCK TABLES `hd_sendprize_user` WRITE;
/*!40000 ALTER TABLE `hd_sendprize_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `hd_sendprize_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp`
--

LOCK TABLES `kefu_sendprize_temp` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_1`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_1`
--

LOCK TABLES `kefu_sendprize_temp_1` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_1_20141011153256`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_1_20141011153256`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_1_20141011153256` (
  `id` int(11) NOT NULL DEFAULT '0',
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_1_20141011153256`
--

LOCK TABLES `kefu_sendprize_temp_1_20141011153256` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1_20141011153256` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1_20141011153256` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_1_20141011153409`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_1_20141011153409`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_1_20141011153409` (
  `id` int(11) NOT NULL DEFAULT '0',
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_1_20141011153409`
--

LOCK TABLES `kefu_sendprize_temp_1_20141011153409` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1_20141011153409` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_1_20141011153409` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_2`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_2`
--

LOCK TABLES `kefu_sendprize_temp_2` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_2` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_3`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_3`
--

LOCK TABLES `kefu_sendprize_temp_3` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_3` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_4`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_4`
--

LOCK TABLES `kefu_sendprize_temp_4` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_4` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_4` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_5`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_5` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_5`
--

LOCK TABLES `kefu_sendprize_temp_5` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_5` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_5` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_6`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_6` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_6`
--

LOCK TABLES `kefu_sendprize_temp_6` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_6` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_6` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kefu_sendprize_temp_7`
--

DROP TABLE IF EXISTS `kefu_sendprize_temp_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kefu_sendprize_temp_7` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server` int(11) DEFAULT '0',
  `username` varchar(150) DEFAULT NULL,
  `rolename` varchar(150) DEFAULT NULL,
  `prize` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `createdate` datetime DEFAULT NULL,
  `ip` varchar(120) DEFAULT NULL,
  `gid` varchar(100) DEFAULT NULL,
  `rid` int(4) DEFAULT '0',
  `roleid` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kefu_sendprize_temp_7`
--

LOCK TABLES `kefu_sendprize_temp_7` WRITE;
/*!40000 ALTER TABLE `kefu_sendprize_temp_7` DISABLE KEYS */;
/*!40000 ALTER TABLE `kefu_sendprize_temp_7` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `FK_Reference_25` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `TRIGGER_NAME` varchar(100) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('kefu_sendprize_temp_1','huodong161|2','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_2','huodong161|2','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_3','huodong161|2','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_5','huodong161|2','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_6','huodong161|1','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_7','huodong161|1','0/60 * * * * ?','Asia/Shanghai'),('kefu_sendprize_temp_4','huodong161|2','0/30 * * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_STATEFUL` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ENTRY_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `IS_STATEFUL` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('jobDetail','DEFAULT',NULL,'com.wanmei.service.quartz.MyQuartzJobBean','0','0','0','0','¨Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇË√˚≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapÊ.≠(v\nŒ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xp\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0x\0');
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_listeners`
--

DROP TABLE IF EXISTS `qrtz_job_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_listeners` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `JOB_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`,`JOB_LISTENER`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_listeners`
--

LOCK TABLES `qrtz_job_listeners` WRITE;
/*!40000 ALTER TABLE `qrtz_job_listeners` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_listeners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`LOCK_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(7) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_trigger_listeners`
--

DROP TABLE IF EXISTS `qrtz_trigger_listeners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_trigger_listeners` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `TRIGGER_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_LISTENER`),
  CONSTRAINT `FK_Reference_26` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_trigger_listeners`
--

LOCK TABLES `qrtz_trigger_listeners` WRITE;
/*!40000 ALTER TABLE `qrtz_trigger_listeners` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_trigger_listeners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `TRIGGER_NAME` varchar(100) NOT NULL,
  `TRIGGER_GROUP` varchar(50) NOT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_GROUP` varchar(50) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) NOT NULL DEFAULT '1',
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `FK_Reference_22` (`JOB_NAME`,`JOB_GROUP`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('kefu_sendprize_temp_1','huodong161|2','jobDetail','DEFAULT','0',NULL,1413011220000,1413011160000,5,'PAUSED','CRON',1383116836000,0,NULL,0,''),('kefu_sendprize_temp_2','huodong161|2','jobDetail','DEFAULT','0',NULL,1412840760000,1412840700000,5,'PAUSED','CRON',1383116851000,0,NULL,0,''),('kefu_sendprize_temp_3','huodong161|2','jobDetail','DEFAULT','0',NULL,1411441080000,1411441020000,5,'PAUSED','CRON',1383116863000,0,NULL,0,''),('kefu_sendprize_temp_5','huodong161|2','jobDetail','DEFAULT','0',NULL,1407292920000,1407292860000,5,'PAUSED','CRON',1383116891000,0,NULL,0,''),('kefu_sendprize_temp_6','huodong161|1','jobDetail','DEFAULT','0',NULL,1412840760000,1412840700000,5,'PAUSED','CRON',1383116910000,0,NULL,0,''),('kefu_sendprize_temp_7','huodong161|1','jobDetail','DEFAULT','0',NULL,1412840760000,1412840700000,5,'PAUSED','CRON',1383116920000,0,NULL,0,''),('kefu_sendprize_temp_4','huodong161|2','jobDetail','DEFAULT','0',NULL,1412840760000,1412840730000,5,'PAUSED','CRON',1385024057000,0,NULL,0,'');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_task`
--

DROP TABLE IF EXISTS `ss_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
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

--
-- Table structure for table `ss_user`
--

DROP TABLE IF EXISTS `ss_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `roles` varchar(255) NOT NULL,
  `register_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_user`
--

LOCK TABLES `ss_user` WRITE;
/*!40000 ALTER TABLE `ss_user` DISABLE KEYS */;
INSERT INTO `ss_user` VALUES (1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(2,'user','Calvin','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','admin','2012-06-03 18:00:00'),(3,'chengzhiwang','chengzhiwang','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(4,'shenfan103024','shenfan','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(5,'xudong01686','xudong','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(6,'chenleiming','chenleiming','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(7,'sunshiwang','sunshiwang','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(8,'mengjia','mengjia','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(9,'zhangji103511','zhangji','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(10,'xudawei','xudawei','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(11,'fenghezhao','fenghezhao','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(12,'daidandan','daidandan','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','event','2012-06-03 17:00:00');
/*!40000 ALTER TABLE `ss_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-13 15:15:49
