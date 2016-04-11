/*
MySQL Data Transfer
Source Host: localhost
Source Database: wmeovg
Target Host: localhost
Target Database: wmeovg
Date: 2010-9-3 16:34:20
*/
CREATE DATABASE wmeovg CHARSET = utf8;
use wmeovg;
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `content` text,
  `createtime` varchar(20) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=966 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_model`;
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

-- ----------------------------
-- Table structure for sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_rolefunction
-- ----------------------------
DROP TABLE IF EXISTS `sys_rolefunction`;
CREATE TABLE `sys_rolefunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_roleoperation
-- ----------------------------
DROP TABLE IF EXISTS `sys_roleoperation`;
CREATE TABLE `sys_roleoperation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `operationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
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

-- ----------------------------
-- Table structure for sys_userfunction
-- ----------------------------
DROP TABLE IF EXISTS `sys_userfunction`;
CREATE TABLE `sys_userfunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `functionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_useroperation
-- ----------------------------
DROP TABLE IF EXISTS `sys_useroperation`;
CREATE TABLE `sys_useroperation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `operationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_userrole
-- ----------------------------
DROP TABLE IF EXISTS `sys_userrole`;
CREATE TABLE `sys_userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wmeovg_client_info
-- ----------------------------
DROP TABLE IF EXISTS `wmeovg_client_info`;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wmeovg_game
-- ----------------------------
DROP TABLE IF EXISTS `wmeovg_game`;
CREATE TABLE `wmeovg_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `server_name` varchar(20) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wmeovg_prize_log
-- ----------------------------
DROP TABLE IF EXISTS `wmeovg_prize_log`;
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

-- Table "wmeovg_goods_log" DDL

CREATE TABLE `wmeovg_goods_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gid` varchar(50) NOT NULL DEFAULT '',
  `cid` varchar(2) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  `zoneid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  `rolename` varchar(20) DEFAULT NULL,
  `mail_title` varchar(255) DEFAULT NULL,
  `mail_context` varchar(255) DEFAULT NULL,
  `goods_flag` int(11) DEFAULT NULL,
  `goods_price` int(11) DEFAULT NULL,
  `goods_price_before_discount` int(11) DEFAULT NULL,
  `prizeid` int(11) DEFAULT NULL,
  `attach_money` int(11) DEFAULT NULL,
  `reserved1` int(11) DEFAULT NULL,
  `reserved2` int(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_gid` (`gid`),
  KEY `idx_requqest_time` (`request_time`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for wmeovg_prize_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `wmeovg_prize_whitelist`;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_info_id` (`client_info_id`,`appid`,`prizeid`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `sys_function` VALUES ('1', '用户', '4', 'html/sys/user.html', 'main', '0', null, null, '0');
INSERT INTO `sys_function` VALUES ('2', '模块', '5', 'html/sys/model.html', 'main', '1', null, '', '0');
INSERT INTO `sys_function` VALUES ('3', '功能', '5', 'html/sys/fuc.html', 'main', '2', null, '', '0');
INSERT INTO `sys_function` VALUES ('4', '角色', '6', 'html/sys/role.html', 'main', '1', null, '', '0');
INSERT INTO `sys_function` VALUES ('5', '角色用户', '6', 'html/sys/roleuser.html', 'main', '2', null, '', '0');
INSERT INTO `sys_function` VALUES ('6', '角色功能', '6', 'html/sys/rolefuc.html', 'main', '3', null, '', '0');
INSERT INTO `sys_function` VALUES ('9', '重新登录', '2', ' ', '_blank', '0', null, '', '0');
INSERT INTO `sys_function` VALUES ('10', '系统日志', '7', 'html/sys/log.html', null, '1', null, '', null);
INSERT INTO `sys_function` VALUES ('11', '客户端信息', '9', 'html/config/clientinfo.html', null, '1', null, '', null);
INSERT INTO `sys_function` VALUES ('12', '物品白名单', '9', 'html/config/whitelist.html', null, '2', null, '', null);
INSERT INTO `sys_function` VALUES ('13', '兑换记录', '10', 'html/service/prizelog.html', null, '1', null, '', null);
INSERT INTO `sys_function` VALUES ('14', '游戏类别', '9', 'html/config/game.html', null, '3', null, '', null);

INSERT INTO `sys_model` VALUES ('1', '顶层模块', null, '0', null, null, '0');
INSERT INTO `sys_model` VALUES ('2', '系统管理', '1', '3', null, '用户管理、功能管理等', '0');
INSERT INTO `sys_model` VALUES ('3', '模板管理', '1', '2', null, '', '0');
INSERT INTO `sys_model` VALUES ('4', '用户管理', '2', '1', null, '', '0');
INSERT INTO `sys_model` VALUES ('5', '功能管理', '2', '2', null, '', '0');
INSERT INTO `sys_model` VALUES ('6', '授权管理', '2', '3', null, '', '0');
INSERT INTO `sys_model` VALUES ('7', '日志管理', '2', '4', null, null, '0');
INSERT INTO `sys_model` VALUES ('8', '个人管理', '2', '5', null, '', '0');
INSERT INTO `sys_model` VALUES ('9', '兑奖参数', '1', '2', null, '', null);
INSERT INTO `sys_model` VALUES ('10', '查询统计', '1', '1', null, '', null);
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '', null);
INSERT INTO `sys_role` VALUES ('2', '普通用户', null, '0');
INSERT INTO `sys_role` VALUES ('3', 'ttt', 'ddddd', null);
INSERT INTO `sys_rolefunction` VALUES ('1', '1', '1');
INSERT INTO `sys_rolefunction` VALUES ('2', '2', '2');
INSERT INTO `sys_rolefunction` VALUES ('3', '1', '3');
INSERT INTO `sys_rolefunction` VALUES ('4', '1', '4');
INSERT INTO `sys_rolefunction` VALUES ('5', '1', '5');
INSERT INTO `sys_rolefunction` VALUES ('6', '1', '6');
INSERT INTO `sys_rolefunction` VALUES ('13', '1', '9');
INSERT INTO `sys_rolefunction` VALUES ('20', '1', '2');
INSERT INTO `sys_rolefunction` VALUES ('21', '1', '10');
INSERT INTO `sys_rolefunction` VALUES ('22', '1', '11');
INSERT INTO `sys_rolefunction` VALUES ('23', '1', '12');
INSERT INTO `sys_rolefunction` VALUES ('24', '1', '13');
INSERT INTO `sys_rolefunction` VALUES ('27', '1', '14');
INSERT INTO `sys_user` VALUES ('1', 's%s', '1', 'liaomin01414', '测试测试233', '185', '0');
INSERT INTO `sys_user` VALUES ('2', 'df', '2', 'daidandan', '测试测试', null, '0');
INSERT INTO `sys_userrole` VALUES ('3', '1', '1');
INSERT INTO `sys_userrole` VALUES ('123', '1', '2');
INSERT INTO `sys_userrole` VALUES ('128', '1', '1');
INSERT INTO `sys_userrole` VALUES ('138', '3', '2');
INSERT INTO `sys_userrole` VALUES ('139', '2', '2');
INSERT INTO `sys_userrole` VALUES ('140', '3', '3');
INSERT INTO `sys_userrole` VALUES ('141', '2', '3');
