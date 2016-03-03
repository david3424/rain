/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50141
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50141
File Encoding         : 65001

Date: 2014-03-06 14:35:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `data_item_attr_log`
-- ----------------------------
DROP TABLE IF EXISTS `data_item_attr_log`;
CREATE TABLE `data_item_attr_log` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `item_turns` bigint(16) NOT NULL COMMENT '这个就是为了让log们知道我们是一次检查的。这是个序列号，每次job执行+1',
  `attr_name` varchar(32) NOT NULL,
  `ch_name` varchar(16) DEFAULT NULL,
  `result` varchar(24) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_item_attr` (`item_id`,`attr_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个表记录一个数据监控项的数据属性值，只记录出现异常的值。';

-- ----------------------------
-- Records of data_item_attr_log
-- ----------------------------

-- ----------------------------
-- Table structure for `data_item_attr_setting`
-- ----------------------------
DROP TABLE IF EXISTS `data_item_attr_setting`;
CREATE TABLE `data_item_attr_setting` (
  `item_id` int(11) NOT NULL,
  `attr_name` varchar(32) NOT NULL COMMENT '监控的属性名',
  `ch_name` varchar(16) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  `sql` varchar(511) NOT NULL,
  `data_source` varchar(32) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`item_id`,`attr_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这里是监控项的一些属性值得设定，这些值都可以用于规则的计算，波动的log的记录和报警';

-- ----------------------------
-- Records of data_item_attr_setting
-- ----------------------------

-- ----------------------------
-- Table structure for `data_monitor_check`
-- ----------------------------
DROP TABLE IF EXISTS `data_monitor_check`;
CREATE TABLE `data_monitor_check` (
  `item_id` int(11) NOT NULL,
  `checker_name` varchar(32) NOT NULL COMMENT '检查的英文名字了，用来标识一个检查',
  `ch_name` varchar(32) DEFAULT NULL COMMENT '中文名用于显示',
  `memo` varchar(128) DEFAULT NULL,
  `expression` varchar(128) NOT NULL COMMENT '规则的表达式了，a+b =c 这种，a为属性',
  `abn_level` int(11) NOT NULL DEFAULT '4' COMMENT '这个需要级别吗，感觉数据一出问题就是出问题了,先默认为4吧',
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`,`checker_name`),
  KEY `idx` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个表示用来做数据规则监控的一个规则的录入，也就是监控项的一个监控子项';

-- ----------------------------
-- Records of data_monitor_check
-- ----------------------------

-- ----------------------------
-- Table structure for `data_monitor_check_log`
-- ----------------------------
DROP TABLE IF EXISTS `data_monitor_check_log`;
CREATE TABLE `data_monitor_check_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `checker_name` varchar(32) NOT NULL,
  `item_turns` bigint(20) NOT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_monitor_check_log
-- ----------------------------

-- ----------------------------
-- Table structure for `data_monitor_item`
-- ----------------------------
DROP TABLE IF EXISTS `data_monitor_item`;
CREATE TABLE `data_monitor_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(32) NOT NULL COMMENT '监控项名（建议英文）',
  `item_name_ch` varchar(16) DEFAULT NULL COMMENT '监控项中文名，方面页面上查看',
  `begin_time` char(20) DEFAULT NULL COMMENT '监控开始时间',
  `end_time` char(20) DEFAULT NULL COMMENT '监控结束时间',
  `change_time` char(20) DEFAULT NULL COMMENT '最后状态改变时间',
  `job_status` int(11) NOT NULL COMMENT 'job状态',
  `job_cron` char(16) NOT NULL COMMENT 'cron表达式，控制job执行',
  `user_id` int(11) NOT NULL COMMENT '这个监控项是谁建立的',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注字段',
  `turns` bigint(16) NOT NULL DEFAULT '1' COMMENT '这个用来记录本次该项是第几次，这样数据和检查log就可以关联起来的了',
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前监控项的状态，基本就是正常和异常了',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='本表示监控项的定义表，记录监控项是监控什么的，统计有job的设定，多长时间监控一次';

-- Records of data_monitor_item
-- ----------------------------
INSERT INTO `data_monitor_item` VALUES ('1', 'event_a', 'a活动数据监控', null, null, '2014-01-14 14:47:32', '1', '0/10 * * * * ?', '1', '没有什么需要记忆的', '1', '2014-01-14 14:47:32', '0');

-- ----------------------------
-- Table structure for `data_oscillation_check`
-- ----------------------------
DROP TABLE IF EXISTS `data_oscillation_check`;
CREATE TABLE `data_oscillation_check` (
  `item_id` int(11) NOT NULL,
  `attr_name` varchar(32) NOT NULL,
  `oscillation` varchar(64) DEFAULT NULL COMMENT '简短中文说名，用于显示',
  `memo` varchar(128) DEFAULT NULL COMMENT '较长说明，用于短信提示',
  `phase_strategy` int(11) NOT NULL,
  `check_strategy` int(11) DEFAULT '1',
  `time_step` int(11) DEFAULT '60' COMMENT '每隔多少时间检查一次，单位分钟',
  `abn_level` int(11) NOT NULL DEFAULT '3',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0表示检查 1表示不检查',
  PRIMARY KEY (`item_id`,`attr_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个表示用来监控波动的，出现突增或者突减就报警，仅仅是报警了';

-- ----------------------------
-- Records of data_oscillation_check
-- ----------------------------

-- ----------------------------
-- Table structure for `data_oscillation_log`
-- ----------------------------
DROP TABLE IF EXISTS `data_oscillation_log`;
CREATE TABLE `data_oscillation_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `attr_name` varchar(32) NOT NULL,
  `item_turns` bigint(20) NOT NULL,
  `total` int(11) NOT NULL,
  `current_num` int(11) NOT NULL,
  `phase_strategy_id` int(11) NOT NULL,
  `phase_detail` varchar(11) NOT NULL,
  `times` int(11) NOT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0表示正常值，参与统计， 1表示不正常',
  PRIMARY KEY (`id`),
  KEY `idx_item_attr` (`item_id`,`attr_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个需要波动监控的数据的log，正常与否都有记录的';


CREATE TABLE `data_monitor_remind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `remind_type` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_item_type_user` (`item_id`,`remind_type`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of data_oscillation_log
-- ----------------------------

-- ----------------------------
-- Table structure for `data_phase_detail`
-- ----------------------------
DROP TABLE IF EXISTS `data_phase_detail`;
CREATE TABLE `data_phase_detail` (
  `strategy_id` int(11) NOT NULL,
  `phase_name` varchar(16) NOT NULL,
  `memo` varchar(128) DEFAULT NULL,
  `phase_start` char(20) NOT NULL,
  `phase_end` char(20) NOT NULL,
  `tendency` int(11) NOT NULL DEFAULT '0' COMMENT '0 表示平衡，1 表示上升，2表示下降',
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`strategy_id`,`phase_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这里面定义具体的时段，比如先忙时采用不同的监控手段，现在这个表有用于记录，但是没有参与计算波动';
-- ----------------------------
-- Records of data_phase_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `data_phase_strategy`
-- ----------------------------
DROP TABLE IF EXISTS `data_phase_strategy`;
CREATE TABLE `data_phase_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `strategy_name` varchar(32) DEFAULT NULL,
  `memo` varchar(128) DEFAULT NULL,
  `create_time` char(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_strategy` (`strategy_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='这是用于数据波动监控的一个配置表，主要用来定义时段策略，比如a策略分闲忙时，detail表里面就记录两条记录';
-- ----------------------------
-- Records of data_phase_strategy
-- ----------------------------

ALTER TABLE `data_monitor_item` ADD COLUMN `data_source`  varchar(32) NULL AFTER `item_name_ch`;
ALTER TABLE `data_oscillation_log` ADD COLUMN `create_time_long`  bigint(20) NULL AFTER `create_time`;



