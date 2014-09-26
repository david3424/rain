use rain_bh;
-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
CREATE TABLE `t_sys_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `option_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `message` text,
  `log_type` int(11) NOT NULL DEFAULT '1' COMMENT '日志类型[1登陆]、[2操作]',
  PRIMARY KEY (`log_id`)
) ENGINE=MyISAM AUTO_INCREMENT=341 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_menu_type
-- ----------------------------
CREATE TABLE `t_sys_menu_type` (
  `menu_type_id` int(11) NOT NULL,
  `menu_type_name` varchar(255) NOT NULL,
  `menu_order` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
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

-- ----------------------------
-- Table structure for t_sys_permission_mapping
-- ----------------------------
CREATE TABLE `t_sys_permission_mapping` (
  `mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
) ENGINE=MyISAM AUTO_INCREMENT=926 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `role_code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role_game_mapping
-- ----------------------------
CREATE TABLE `t_sys_role_game_mapping` (
  `mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1060 DEFAULT CHARSET=utf8 COMMENT='��ɫȨ��ӳ���';

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
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
) ENGINE=MyISAM AUTO_INCREMENT=1615 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_value
-- ----------------------------
CREATE TABLE `t_sys_value` (
  `id` varchar(100) NOT NULL,
  `gameid` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `edit_flag` int(11) DEFAULT NULL,
  `read_flag` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_game_dict
-- ----------------------------
CREATE TABLE `t_game_dict` (
  `game_id` int(11) NOT NULL,
  `game_name` varchar(30) NOT NULL,
  `game_ab` varchar(30) NOT NULL,
  `game_type` varchar(30) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `client` int(11) DEFAULT '0' COMMENT ''
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `t_sys_user` VALUES ('1612', '许大伟', 'xudawei', '3323042e7e412059744b64d176707ce5', '1', '100', '200', null, null, 'all');
INSERT INTO `t_sys_role` VALUES ('1', '管理员', 'ROLE_admin', '管理员角色');
