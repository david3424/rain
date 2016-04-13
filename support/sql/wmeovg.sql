-- MySQL dump 10.13  Distrib 5.6.20, for osx10.8 (x86_64)
--
-- Host: localhost    Database: wmeovg
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
-- Table structure for table `sys_function`
--

DROP TABLE IF EXISTS `sys_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `modelId` int(11) DEFAULT NULL,
  `src` varchar(200) DEFAULT NULL,
  `target` varchar(50) DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT '0',
  `rootPath` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_function`
--

LOCK TABLES `sys_function` WRITE;
/*!40000 ALTER TABLE `sys_function` DISABLE KEYS */;
INSERT INTO `sys_function` VALUES (1,'用户',4,'html/sys/user.html','main',0,NULL,NULL,0),(2,'模块',5,'html/sys/model.html','main',1,NULL,'',0),(3,'功能',5,'html/sys/fuc.html','main',2,NULL,'',0),(4,'角色',6,'html/sys/role.html','main',1,NULL,'',0),(5,'角色用户',6,'html/sys/roleuser.html','main',2,NULL,'',0),(6,'角色功能',6,'html/sys/rolefuc.html','main',3,NULL,'',0),(9,'重新登录',2,' ','_blank',0,NULL,'',0),(10,'系统日志',7,'html/sys/log.html',NULL,1,NULL,'',NULL),(11,'客户端信息',9,'html/config/clientinfo.html',NULL,1,NULL,'',NULL),(12,'物品白名单',9,'html/config/whitelist.html',NULL,2,NULL,'',NULL),(13,'兑换记录',10,'html/service/prizelog.html',NULL,1,NULL,'',NULL),(14,'游戏类别',9,'html/config/game.html',NULL,3,NULL,'',NULL),(15,'jms控制',2,'html/sys/service.html',NULL,6,NULL,'控制JMS开关',NULL),(16,'发送失败查看',10,'html/service/sendfailed.html',NULL,2,NULL,'',NULL);
/*!40000 ALTER TABLE `sys_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `content` text,
  `createtime` varchar(20) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_model`
--

DROP TABLE IF EXISTS `sys_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `fid` int(11) DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT '0',
  `rootPath` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_model`
--

LOCK TABLES `sys_model` WRITE;
/*!40000 ALTER TABLE `sys_model` DISABLE KEYS */;
INSERT INTO `sys_model` VALUES (1,'顶层模块',NULL,0,NULL,NULL,0),(2,'系统管理',1,3,NULL,'用户管理、功能管理等',0),(3,'模板管理',1,2,NULL,'',0),(4,'用户管理',2,1,NULL,'',0),(5,'功能管理',2,2,NULL,'',0),(6,'授权管理',2,3,NULL,'',0),(7,'日志管理',2,4,NULL,NULL,0),(8,'个人管理',2,5,NULL,'',0),(9,'兑奖参数',1,2,NULL,'',NULL),(10,'查询统计',1,1,NULL,'',NULL);
/*!40000 ALTER TABLE `sys_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation`
--

DROP TABLE IF EXISTS `sys_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation`
--

LOCK TABLES `sys_operation` WRITE;
/*!40000 ALTER TABLE `sys_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'系统管理员','',NULL),(2,'普通用户',NULL,0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_rolefunction`
--

DROP TABLE IF EXISTS `sys_rolefunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_rolefunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_rolefunction`
--

LOCK TABLES `sys_rolefunction` WRITE;
/*!40000 ALTER TABLE `sys_rolefunction` DISABLE KEYS */;
INSERT INTO `sys_rolefunction` VALUES (1,1,1),(2,2,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(13,1,9),(20,1,2),(21,1,10),(22,1,11),(23,1,12),(24,1,13),(27,1,14),(28,1,15),(29,1,16);
/*!40000 ALTER TABLE `sys_rolefunction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_roleoperation`
--

DROP TABLE IF EXISTS `sys_roleoperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_roleoperation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `operationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_roleoperation`
--

LOCK TABLES `sys_roleoperation` WRITE;
/*!40000 ALTER TABLE `sys_roleoperation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_roleoperation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `loginName` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `login_count` int(11) DEFAULT '0',
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'david','1','admin','测试测试233',185,0),(2,'df','2','guest','测试测试',NULL,0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_userfunction`
--

DROP TABLE IF EXISTS `sys_userfunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_userfunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_userfunction`
--

LOCK TABLES `sys_userfunction` WRITE;
/*!40000 ALTER TABLE `sys_userfunction` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_userfunction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_useroperation`
--

DROP TABLE IF EXISTS `sys_useroperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_useroperation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `operationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_useroperation`
--

LOCK TABLES `sys_useroperation` WRITE;
/*!40000 ALTER TABLE `sys_useroperation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_useroperation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_userrole`
--

DROP TABLE IF EXISTS `sys_userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_userrole`
--

LOCK TABLES `sys_userrole` WRITE;
/*!40000 ALTER TABLE `sys_userrole` DISABLE KEYS */;
INSERT INTO `sys_userrole` VALUES (3,1,1),(128,1,1),(139,2,2);
/*!40000 ALTER TABLE `sys_userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wmeovg_client_info`
--

DROP TABLE IF EXISTS `wmeovg_client_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wmeovg_client_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `cid` varchar(2) DEFAULT NULL,
  `private_key` varchar(32) DEFAULT NULL,
  `priority` tinyint(1) DEFAULT NULL,
  `root_url` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `client_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wmeovg_client_info`
--

LOCK TABLES `wmeovg_client_info` WRITE;
/*!40000 ALTER TABLE `wmeovg_client_info` DISABLE KEYS */;
INSERT INTO `wmeovg_client_info` VALUES (8,1,'monitor','01','WHNDckQqd3dJM29W',0,'http://localhost:8080/api/servlet/wmeovg/callback','2016-04-11 16:57:11',0,'127.0.0.1');
/*!40000 ALTER TABLE `wmeovg_client_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wmeovg_game`
--

DROP TABLE IF EXISTS `wmeovg_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wmeovg_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `server_name` varchar(20) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wmeovg_game`
--

LOCK TABLES `wmeovg_game` WRITE;
/*!40000 ALTER TABLE `wmeovg_game` DISABLE KEYS */;
INSERT INTO `wmeovg_game` VALUES (12,'手游','sy',1);
/*!40000 ALTER TABLE `wmeovg_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wmeovg_prize_log`
--

DROP TABLE IF EXISTS `wmeovg_prize_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wmeovg_prize_log` (
  `gid` varchar(50) NOT NULL DEFAULT '',
  `cid` varchar(2) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  `zoneid` int(11) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `rolename` varchar(20) DEFAULT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  `prizeid` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `priority` tinyint(1) DEFAULT NULL,
  `callback` varchar(255) DEFAULT NULL,
  `send_status` tinyint(1) DEFAULT NULL,
  `process_count` int(11) DEFAULT NULL,
  `callback_status` tinyint(1) DEFAULT NULL,
  `request_time` datetime DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `message_id` varchar(50) DEFAULT NULL,
  `prize_resend_count` tinyint(1) DEFAULT NULL,
  `callback_time` datetime DEFAULT NULL,
  `callback_httpstatus` int(2) DEFAULT NULL,
  `callback_count` tinyint(1) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `prize_send_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`gid`),
  KEY `idx_requqest_time` (`request_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wmeovg_prize_log`
--

LOCK TABLES `wmeovg_prize_log` WRITE;
/*!40000 ALTER TABLE `wmeovg_prize_log` DISABLE KEYS */;
INSERT INTO `wmeovg_prize_log` VALUES ('01-20160412-15096ee8-7970-4365-9eec-0848d4bc0f90','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:06:50','2016-04-12 16:22:26',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-1c4366da-529c-45dd-996e-4b7e6b0aa138','01','lottery_demo_prize',3305,'cindy3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:20:50','2016-04-12 16:55:22',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-1d5360c8-c380-4705-81ff-4c8064fca0e4','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:10:00','2016-04-12 16:23:23',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-22538e71-c6ff-4108-ae69-fe05214836df','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:07:50','2016-04-12 16:22:42',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-4361481e-efbf-4d3f-aa46-30e5933cba35','01','lottery_demo_prize',3305,'cindy3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:19:51','2016-04-12 16:54:40',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-4763f061-ae82-43ba-958a-94537a04e6e9','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 14:30:50','2016-04-12 14:59:40',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-49a96955-5c4e-438b-90a2-c2a57b1dbd9c','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 13:58:00','2016-04-12 14:59:16',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-5328ab2e-1a62-4f6c-b192-f7a4739c9821','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:09:50','2016-04-12 16:23:15',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-56b4945a-e627-42c7-8be2-50bb924a4672','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 13:59:09','2016-04-12 14:59:32',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-6d937b9b-f11c-42ac-aa34-de062cd20515','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:07:00','2016-04-12 16:22:34',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-6da1110d-e370-4fa9-ad7d-d87679ab58da','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 13:58:50','2016-04-12 14:59:24',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-6f08fe33-93f3-4f26-a37a-d1489495ec0e','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 14:31:00','2016-04-12 14:59:48',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-71ac2c96-bd42-420e-954e-af588c97c6e2','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:09:00','2016-04-12 16:23:07',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-7a523f30-723f-4584-a8ac-047c20ff3840','01','lottery_demo_prize',3305,'cindy3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:21:00','2016-04-12 16:55:44',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-826d62eb-e257-4791-b32f-dbcf4cb53a77','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-5,1,10,'2016-04-12 13:57:50','2016-04-12 14:59:07',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-afe1b6fd-03d2-4cfd-8173-8d070a854c38','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:08:00','2016-04-12 16:22:50',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-dfacea2a-a409-4211-a952-d71f918e74a4','01','lottery_demo_prize',3305,'david3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:08:50','2016-04-12 16:22:58',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL),('01-20160412-e4e8ea13-ffa7-4a63-a706-acb37a9cf9ee','01','lottery_demo_prize',3305,'cindy3424',NULL,1990980375,10000,1,0,'callback&5',-1,1,10,'2016-04-12 16:20:19','2016-04-12 16:55:06',NULL,3,'2016-04-13 14:27:36',200,0,'127.0.0.1',NULL);
/*!40000 ALTER TABLE `wmeovg_prize_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wmeovg_prize_whitelist`
--

DROP TABLE IF EXISTS `wmeovg_prize_whitelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wmeovg_prize_whitelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `client_info_id` int(11) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  `prizename` varchar(200) DEFAULT NULL,
  `prizeid` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `send_count` int(11) DEFAULT NULL,
  `fail_count` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(100) DEFAULT NULL,
  `text` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_info_id` (`client_info_id`,`appid`,`prizeid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wmeovg_prize_whitelist`
--

LOCK TABLES `wmeovg_prize_whitelist` WRITE;
/*!40000 ALTER TABLE `wmeovg_prize_whitelist` DISABLE KEYS */;
INSERT INTO `wmeovg_prize_whitelist` VALUES (111,1,8,12,'lottery_demo_prize','虚拟1',10000,80,18,18,'2016-04-12 13:55:50',0,'测试标题','测试正文');
/*!40000 ALTER TABLE `wmeovg_prize_whitelist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-13 14:34:35
