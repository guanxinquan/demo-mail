CREATE TABLE `dns` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT 'dns name',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `mail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mailboxId` int(11) DEFAULT NULL COMMENT 'mailbox的id',
  `content` longtext COMMENT '消息的内容',
  `size` int(11) DEFAULT NULL COMMENT '大小',
  `UIDL` varchar(70) DEFAULT NULL COMMENT 'UIDL',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `message_id` bigint(20) DEFAULT NULL COMMENT '消息id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `mailbox` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) DEFAULT NULL COMMENT 'mailbox的名称',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `message` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '消息的id',
  `header` varchar(5000) DEFAULT NULL COMMENT '消息的头部',
  `body` longtext COMMENT '消息体',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT 'email的名称',
  `password` varchar(50) DEFAULT NULL COMMENT 'email的密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
