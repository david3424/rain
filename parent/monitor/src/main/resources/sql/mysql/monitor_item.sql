/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50141
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50141
File Encoding         : 65001

Date: 2014-01-09 15:33:54
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `monitor_item`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_item`;
CREATE TABLE `monitor_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(64) NOT NULL,
  `item_name_ch` varchar(32) DEFAULT NULL,
  `item_url` varchar(255) DEFAULT NULL,
  `return_type` varchar(8) DEFAULT NULL,
  `change_time` char(20) DEFAULT NULL,
  `job_status` int(11) NOT NULL DEFAULT '0' COMMENT '定时器状态',
  `job_cron` varchar(16) NOT NULL,
  `create_time` char(20) NOT NULL,
  `memo` varchar(128) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '监控项状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_item
-- ----------------------------
INSERT INTO `monitor_item` VALUES ('1', 'hdservice', 'http://localhost:8080/test/service', 'common', '2014-01-09 15:25:40', '1', '0/10 * * * * ?', '2013-12-19 16:30:24', '1', '3');
INSERT INTO `monitor_item` VALUES ('2', 'test2', 'http://localhost:8080/test/database/read', 'common', '2014-01-09 11:25:40', '1', '0/10 * * * * ?', '2013-12-19 16:30:41', '1', '1');
INSERT INTO `monitor_item` VALUES ('3', 'test3', 'http://localhost:8080/test/database/write', 'common', '2014-01-09 11:25:40', '1', '0/10 * * * * ?', '2014-01-06 11:38:50', '1', '1');
INSERT INTO `monitor_item` VALUES ('4', 'imgdatabase', 'http://localhost:8080/test/database/img', 'common', '2014-01-09 11:26:00', '1', '0/30 * * * * ?', '2014-01-06 11:42:58', '1', '1');

-- ----------------------------
-- Table structure for `monitor_item_base`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_item_base`;
CREATE TABLE `monitor_item_base` (
  `itemid` int(11) NOT NULL,
  `baseid` int(11) NOT NULL,
  `memo` varchar(64) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`itemid`,`baseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_item_base
-- ----------------------------

-- ----------------------------
-- Table structure for `monitor_item_status_log`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_item_status_log`;
CREATE TABLE `monitor_item_status_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(64) NOT NULL,
  `item_url` varchar(255) NOT NULL,
  `return_type` varchar(8) NOT NULL,
  `status_begin` char(20) NOT NULL,
  `status_end` char(20) NOT NULL,
  `job_cron` varchar(16) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '监控项状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `monitor_remind`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_remind`;
CREATE TABLE `monitor_remind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `remind_type` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_item_type_user` (`item_id`,`remind_type`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_remind
-- ----------------------------
INSERT INTO `monitor_remind` VALUES ('1', '1', '2', '1', '2014-01-06 17:53:05', '0');
INSERT INTO `monitor_remind` VALUES ('2', '1', '1', '1', '2014-01-09 10:21:51', '0');

-- ----------------------------
-- Table structure for `monitor_user`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_user`;
/*CREATE TABLE `monitor_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `ch_name` varchar(8) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `phone` char(11) NOT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;*/

CREATE TABLE monitor_user
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(32) NOT NULL,
  ch_name VARCHAR(8),
  dept_id INT,
  email VARCHAR(128),
  phone CHAR(11) NOT NULL,
  create_time CHAR(20) NOT NULL,
  status INT DEFAULT 0 NOT NULL,
  password VARCHAR(255) DEFAULT '691b14d79bf0fa2215f155235df5e670b64394cc' COMMENT '默认为 admin',
  roles VARCHAR(255),
  salt VARCHAR(255) DEFAULT '7efbd59d9741d34f'
);


-- ----------------------------
-- Records of monitor_user
-- ----------------------------
INSERT INTO monitor_user (id, username, ch_name, dept_id, email, phone, create_time, status, password, roles, salt) VALUES (1, 'admin', '程志旺', null, 'zhiwang.cheng@gmail.com', '13693293051', '2014-01-03 15:08:35', 0, '691b14d79bf0fa2215f155235df5e670b64394cc', 'admin', '7efbd59d9741d34f');
INSERT INTO monitor_user (id, username, ch_name, dept_id, email, phone, create_time, status, password, roles, salt) VALUES (2, 'david', '许大伟', null, 'todavidxu@qq.com', '18611195426', '2014-03-05 14:45:22', 0, '691b14d79bf0fa2215f155235df5e670b64394cc', 'User', '7efbd59d9741d34f');
INSERT INTO monitor_user (id, username, ch_name, dept_id, email, phone, create_time, status, password, roles, salt) VALUES (3, 'cindy', '张继', null, 'david.3424@gmail.com', '13693293051', '2014-03-05 14:52:11', 0, '691b14d79bf0fa2215f155235df5e670b64394cc', 'User', '7efbd59d9741d34f');


# ss_role 角色
CREATE TABLE ss_role
(
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  permissions VARCHAR(255)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX name ON ss_role ( name );


INSERT INTO ss_role (id, name, permissions) VALUES (1, 'admin', 'user:view,user:edit');
INSERT INTO ss_role (id, name, permissions) VALUES (2, 'user', 'user:view');
# 角色用户关联
CREATE TABLE ss_user_role
(
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY ( user_id, role_id )
);

INSERT INTO ss_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO ss_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO ss_user_role (user_id, role_id) VALUES (3, 2);
-- ----------------------------
-- Table structure for `server_monitor_return_type`
-- ----------------------------
DROP TABLE IF EXISTS `server_monitor_return_type`;
CREATE TABLE `server_monitor_return_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(8) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`type_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_monitor_return_type
-- ----------------------------
INSERT INTO `server_monitor_return_type` VALUES ('1', 'common', '0');
INSERT INTO `server_monitor_return_type` VALUES ('6', 'test3', '0');

-- ----------------------------
-- Table structure for `server_monitor_return_type_settings`
-- ----------------------------
DROP TABLE IF EXISTS `server_monitor_return_type_settings`;
CREATE TABLE `server_monitor_return_type_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(8) NOT NULL,
  `attribute_name` varchar(16) DEFAULT NULL,
  `judge_method` varchar(255) NOT NULL,
  `health_value` int(11) NOT NULL,
  `default_level` int(11) NOT NULL DEFAULT '4',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_monitor_return_type_settings
-- ----------------------------
INSERT INTO `server_monitor_return_type_settings` VALUES ('1', 'common', 'consumingTime', '<', '245', '3', '0');
INSERT INTO `server_monitor_return_type_settings` VALUES ('2', 'common', 'status', '=', '1', '4', '0');

-- ----------------------------
-- Table structure for `server_monitor_values`
-- ----------------------------
DROP TABLE IF EXISTS `server_monitor_values`;
CREATE TABLE `server_monitor_values` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `type_code` varchar(16) NOT NULL,
  `attribute_name` varchar(32) NOT NULL,
  `judge_method` varchar(16) NOT NULL,
  `health_value` int(11) NOT NULL,
  `abn_level` int(11) NOT NULL DEFAULT '1',
  `abn_times` int(11) NOT NULL,
  `status_begin` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_item_attr` (`item_id`,`attribute_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_monitor_values
-- ----------------------------
INSERT INTO `server_monitor_values` VALUES ('1', '2', '', 'consumingTime', '<', '122', '3', '0', '2014-01-09 11:25:40', '1');
INSERT INTO `server_monitor_values` VALUES ('2', '1', '', 'consumingTime', '<', '123', '3', '0', '2014-01-09 11:25:30', '1');
INSERT INTO `server_monitor_values` VALUES ('3', '2', '', 'status', '=', '1', '4', '0', '2014-01-09 11:25:30', '1');
INSERT INTO `server_monitor_values` VALUES ('4', '1', '', 'status', '=', '1', '4', '0', '2014-01-09 15:25:30', '1');
INSERT INTO `server_monitor_values` VALUES ('5', '3', '', 'consumingTime', '<', '321', '3', '0', '2014-01-09 15:02:50', '1');
INSERT INTO `server_monitor_values` VALUES ('6', '3', '', 'status', '=', '1', '4', '0', '2014-01-09 11:25:30', '1');
INSERT INTO `server_monitor_values` VALUES ('7', '4', '', 'consumingTime', '<', '123', '3', '0', '2014-01-09 15:03:00', '1');
INSERT INTO `server_monitor_values` VALUES ('8', '4', '', 'status', '=', '1', '4', '0', '2014-01-09 11:25:30', '1');
INSERT INTO `server_monitor_values` VALUES ('9', '1', '', 'test3', '>', '1222', '3', '718', '2014-01-09 11:04:02', '2');

-- ----------------------------
-- Table structure for `server_monitor_values_log`
-- ----------------------------
DROP TABLE IF EXISTS `server_monitor_values_log`;
CREATE TABLE `server_monitor_values_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `type_code` varchar(16) NOT NULL,
  `attribute_name` varchar(32) NOT NULL,
  `judge_method` varchar(16) NOT NULL,
  `health_value` int(11) NOT NULL,
  `abn_level` int(11) NOT NULL DEFAULT '1',
  `abn_times` int(11) NOT NULL,
  `status_begin` char(20) NOT NULL,
  `status_end` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_item_attr` (`item_id`,`attribute_name`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_monitor_values_log
-- ----------------------------

CREATE TABLE `monitor_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(32) NOT NULL,
  `url` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `is_leaf` int(11) NOT NULL DEFAULT '1' COMMENT '0 标识不是叶子，1标识是',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `monitor_model` VALUES ('1', '功能管理', '', '0', '1', '0');
INSERT INTO `monitor_model` VALUES ('2', '服务监控', '/server/item/manage', '1', '1', '0');
INSERT INTO `monitor_model` VALUES ('3', '用户管理', '/user/manage', '1', '1', '0');
INSERT INTO `monitor_model` VALUES ('4', '角色管理', '/role/manage', '1', '1', '0');
INSERT INTO `monitor_model` VALUES ('5', '监控规则', '/server/response/manage', '1', '1', '0');
INSERT INTO `monitor_model` VALUES ('6', '功能树操作', '/model/tree', '0', '1', '0');