CREATE TABLE `o_pay_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(4) NOT NULL,
  `channelid` int(4) NOT NULL COMMENT '支付渠道id',
  `amount` int(4) NOT NULL COMMENT '支付金额，当为卡类型时传0',
  `currencycode` VARCHAR(3)  NULL  COMMENT '币种，卡类型时null',
  `version` VARCHAR(5) NOT NULL DEFAULT 'v1.0' COMMENT '版本有变',
  `orderid` varchar(100) DEFAULT NULL  COMMENT '商户生成的唯一订单id',
  `paymentid` varchar(50) DEFAULT NULL COMMENT '第三方返回的支付流水号',
  #paymenturl 支付url是返回的，20分钟内有效
  `returnurl` varchar(255) DEFAULT NULL,
  `userid` int(4) DEFAULT NULL,
  `type` int(4) DEFAULT 0 COMMENT '支付类型，暂时只有一种',
  `createtime` varchar(100) DEFAULT NULL,
  `finishtime` varchar(100) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_order` (`orderid`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `o_pay_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(4) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `callbackurl` varchar(100) DEFAULT NULL,
  `createtime` varchar(100) DEFAULT NULL,
  `privatekey` varchar(50) NOT NULL,
  `status` int(11) DEFAULT '0',
  `notes` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `userid` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_app` (`appid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='支付字典表'