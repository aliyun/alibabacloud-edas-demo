CREATE TABLE `txc_undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `xid` varchar(100) NOT NULL COMMENT '全局事务ID',
  `branch_id` bigint(20) NOT NULL COMMENT '分支事务ID',
  `rollback_info` longblob NOT NULL COMMENT 'LOG',
  `status` int(11) NOT NULL COMMENT '状态',
  `server` varchar(32) NOT NULL COMMENT '分支所在DB IP',
  PRIMARY KEY (`id`),
  KEY `unionkey` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=211225994 DEFAULT CHARSET=utf8 COMMENT='事务日志表';

