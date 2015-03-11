CREATE TABLE `mail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mailboxId` int(11) DEFAULT NULL COMMENT 'mailbox的id',
  `content` longtext COMMENT '消息的内容',
  `size` int(11) DEFAULT NULL COMMENT '大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `mailbox` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) DEFAULT NULL COMMENT 'mailbox的名称',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT 'email的名称',
  `password` varchar(50) DEFAULT NULL COMMENT 'email的密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;