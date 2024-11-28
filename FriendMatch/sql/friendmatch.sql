/*
Navicat MySQL Data Transfer

Source Server         : 001
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : friendmatch

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2024-11-28 14:08:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fileName` varchar(255) DEFAULT '',
  `uploadTime` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `isDelete` int(255) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tagName` varchar(256) DEFAULT NULL COMMENT '标签名称',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户 id',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父标签 id',
  `isParent` tinyint(4) DEFAULT NULL COMMENT '0 - 不是, 1 - 父标签',
  `createTime` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `updateTime` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniIdx_tagName` (`tagName`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(256) NOT NULL COMMENT '队伍名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `maxNum` int(11) NOT NULL DEFAULT 1 COMMENT '最大人数',
  `expireTime` datetime DEFAULT NULL COMMENT '过期时间',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id（队长 id）',
  `imgUrl` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0 - 公开，1 - 私有，2 - 加密',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `createTime` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `updateTime` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='队伍';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(256) DEFAULT NULL COMMENT '用户昵称',
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) DEFAULT NULL COMMENT '账号',
  `avatarUrl` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `userPassword` varchar(512) NOT NULL COMMENT '密码',
  `phone` varchar(128) DEFAULT NULL COMMENT '电话',
  `email` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `profile` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `userStatus` int(11) NOT NULL DEFAULT 0 COMMENT '状态 0 - 正常',
  `createTime` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `updateTime` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `userRole` int(11) NOT NULL DEFAULT 0 COMMENT '用户角色 0 - 普通用户 1 - 管理员',
  `tags` varchar(1024) DEFAULT NULL COMMENT '标签 json 列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Table structure for user_team
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `teamId` bigint(20) DEFAULT NULL COMMENT '队伍id',
  `joinTime` datetime DEFAULT NULL COMMENT '加入时间',
  `createTime` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `updateTime` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户队伍关系';
