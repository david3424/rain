-- MySQL dump 10.13  Distrib 5.1.41, for Win32 (ia32)
--
-- Host: localhost    Database: rain_bh
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
-- Table structure for table `t_game_dict`
--

DROP TABLE IF EXISTS `t_game_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_game_dict` (
  `game_id` int(11) NOT NULL,
  `game_name` varchar(30) NOT NULL,
  `game_ab` varchar(30) NOT NULL,
  `game_type` varchar(30) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `client` int(11) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_game_dict`
--

LOCK TABLES `t_game_dict` WRITE;
/*!40000 ALTER TABLE `t_game_dict` DISABLE KEYS */;
INSERT INTO `t_game_dict` VALUES (27,'lost','bhphs','2',NULL,1);
/*!40000 ALTER TABLE `t_game_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_log`
--

DROP TABLE IF EXISTS `t_sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `option_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `message` text,
  `log_type` int(11) NOT NULL DEFAULT '1' COMMENT '日志类型[1登陆]、[2操作]',
  PRIMARY KEY (`log_id`)
) ENGINE=MyISAM AUTO_INCREMENT=364 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_log`
--

LOCK TABLES `t_sys_log` WRITE;
/*!40000 ALTER TABLE `t_sys_log` DISABLE KEYS */;
INSERT INTO `t_sys_log` VALUES (341,'xudawei','0:0:0:0:0:0:0:1','2014-09-29 03:32:50','登录成功！',1),(342,'xudawei','0:0:0:0:0:0:0:1','2014-09-29 03:43:40','登录成功！',1),(343,'xudawei','0:0:0:0:0:0:0:1','2014-09-29 07:12:28','登录成功！',1),(344,'xudawei','0:0:0:0:0:0:0:1','2014-10-08 10:21:18','登录成功！',1),(345,'xudawei','0:0:0:0:0:0:0:1','2014-10-08 10:27:35','登录成功！',1),(346,'xudawei','0:0:0:0:0:0:0:1','2014-10-08 10:33:19','登录成功！',1),(347,'xudawei','0:0:0:0:0:0:0:1','2014-10-08 10:36:35','登录成功！',1),(348,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 08:25:07','登录成功！',1),(349,'xudawei','127.0.0.1','2014-10-11 08:31:53','登录成功！',1),(350,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 08:37:25','登录成功！',1),(351,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 08:40:00','登录成功！',1),(352,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 08:47:03','登录成功！',1),(353,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 08:48:33','登录成功！',1),(354,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 09:39:40','登录成功！',1),(355,'guest','0:0:0:0:0:0:0:1','2014-10-11 10:03:25','登录成功！',1),(356,'guest','0:0:0:0:0:0:0:1','2014-10-11 10:07:39','登录成功！',1),(357,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 10:10:53','登录成功！',1),(358,'guest','0:0:0:0:0:0:0:1','2014-10-11 10:11:52','登录成功！',1),(359,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 10:14:57','登录成功！',1),(360,'guest','0:0:0:0:0:0:0:1','2014-10-11 10:15:13','登录成功！',1),(361,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 10:15:32','登录成功！',1),(362,'guest','0:0:0:0:0:0:0:1','2014-10-11 10:15:46','登录成功！',1),(363,'xudawei','0:0:0:0:0:0:0:1','2014-10-11 10:15:54','登录成功！',1);
/*!40000 ALTER TABLE `t_sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_menu_type`
--

DROP TABLE IF EXISTS `t_sys_menu_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_menu_type` (
  `menu_type_id` int(11) NOT NULL,
  `menu_type_name` varchar(255) NOT NULL,
  `menu_order` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_menu_type`
--

LOCK TABLES `t_sys_menu_type` WRITE;
/*!40000 ALTER TABLE `t_sys_menu_type` DISABLE KEYS */;
INSERT INTO `t_sys_menu_type` VALUES (9,'平台管理',90,NULL);
/*!40000 ALTER TABLE `t_sys_menu_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_permission`
--

DROP TABLE IF EXISTS `t_sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_url` varchar(255) NOT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `game_id` int(11) NOT NULL,
  `menu_type_id` int(11) NOT NULL,
  `resource_order` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`permission_id`)
) ENGINE=MyISAM AUTO_INCREMENT=825 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_permission`
--

LOCK TABLES `t_sys_permission` WRITE;
/*!40000 ALTER TABLE `t_sys_permission` DISABLE KEYS */;
INSERT INTO `t_sys_permission` VALUES (49,'/pppppp/system/show_menu_page','菜单管理','平台管理',0,9,0),(46,'/pppppp/system/show_user_page','用户管理','平台管理',0,9,0),(47,'/pppppp/system/show_role_page','权限管理','平台管理',0,9,0),(48,'/pppppp/system/show_log_page','操作日志','平台管理',0,9,0);
/*!40000 ALTER TABLE `t_sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_permission_mapping`
--

DROP TABLE IF EXISTS `t_sys_permission_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_permission_mapping` (
  `mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
) ENGINE=MyISAM AUTO_INCREMENT=927 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_permission_mapping`
--

LOCK TABLES `t_sys_permission_mapping` WRITE;
/*!40000 ALTER TABLE `t_sys_permission_mapping` DISABLE KEYS */;
INSERT INTO `t_sys_permission_mapping` VALUES (417,1,46),(418,1,47),(419,1,48),(420,1,49),(421,1,50);
/*!40000 ALTER TABLE `t_sys_permission_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role`
--

DROP TABLE IF EXISTS `t_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `role_code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES (1,'管理员','ROLE_admin','管理员角色'),(2,'访客','ROLE_guest','访客');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role_game_mapping`
--

DROP TABLE IF EXISTS `t_sys_role_game_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role_game_mapping` (
  `mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1065 DEFAULT CHARSET=utf8 COMMENT='��ɫȨ��ӳ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role_game_mapping`
--

LOCK TABLES `t_sys_role_game_mapping` WRITE;
/*!40000 ALTER TABLE `t_sys_role_game_mapping` DISABLE KEYS */;
INSERT INTO `t_sys_role_game_mapping` VALUES (1060,1,27),(1063,1,0),(1062,2,27),(1064,2,0);
/*!40000 ALTER TABLE `t_sys_role_game_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user`
--

DROP TABLE IF EXISTS `t_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `account_type` int(11) NOT NULL DEFAULT '100',
  `status` int(11) NOT NULL DEFAULT '200',
  `email` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `agent` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1616 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user`
--

LOCK TABLES `t_sys_user` WRITE;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` VALUES (1612,'许大伟','xudawei','3323042e7e412059744b64d176707ce5',1,100,200,NULL,NULL,'all'),(1615,'访客','guest','b4b3e4355446f7d6e5a540c479b33b9e',2,100,200,NULL,NULL,'');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-11 18:31:25
