/*
 Navicat Premium Data Transfer

 Source Server         : local_mysql
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : virtual

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 22/12/2019 22:22:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coin
-- ----------------------------
DROP TABLE IF EXISTS `coin`;
CREATE TABLE `coin`  (
  `uid` int(11) NOT NULL,
  `sell` bigint(255) NULL DEFAULT NULL,
  `residue` bigint(255) NULL DEFAULT NULL,
  `total` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for coin_info
-- ----------------------------
DROP TABLE IF EXISTS `coin_info`;
CREATE TABLE `coin_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `coin` bigint(255) NULL DEFAULT NULL,
  `type` int(1) NULL DEFAULT NULL COMMENT '0-卖出;1-买入',
  `status` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '0-未卖出,1-已卖出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coin_info
-- ----------------------------
INSERT INTO `coin_info` VALUES (11, 11, 'hl', 77, 0, 1);
INSERT INTO `coin_info` VALUES (12, 12, 'q', 77, 1, 0);
INSERT INTO `coin_info` VALUES (13, 11, 'hl', 50, 0, 0);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `typeOne` int(1) NULL DEFAULT NULL COMMENT '1-人民币;2-虚拟币',
  `typeTwo` int(1) NULL DEFAULT NULL COMMENT '1-充值;2-提现;3-转入;4-转出',
  `amount` bigint(255) NULL DEFAULT NULL,
  `createDate` timestamp(0) NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (8, '11', 'hl', 1, 1, 3333, '2019-12-22 00:00:00', '微信');
INSERT INTO `record` VALUES (9, '11', 'hl', 1, 2, 2, '2019-12-22 00:00:00', '微信');
INSERT INTO `record` VALUES (10, '11', 'hl', 1, 4, 3, '2019-12-22 00:00:00', '机械币');
INSERT INTO `record` VALUES (11, '11', 'hl', 1, 2, 1000, '2019-12-22 00:00:00', '微信');

-- ----------------------------
-- Table structure for rmb_info
-- ----------------------------
DROP TABLE IF EXISTS `rmb_info`;
CREATE TABLE `rmb_info`  (
  `id` int(11) NOT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `rmbIn` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '充值方式',
  `rmbOut` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '提现方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rmb` bigint(255) NULL DEFAULT 0,
  `coin` bigint(255) NULL DEFAULT 0,
  `status` int(1) NOT NULL DEFAULT 1,
  `coinSell` bigint(255) NULL DEFAULT 0,
  `coinResidue` bigint(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '888888', 0, 0, 1, 0, 0);
INSERT INTO `user` VALUES (11, 'hl', '888888', 2431, 97, 1, 50, 47);
INSERT INTO `user` VALUES (12, 'q', 'qqq', 0, 22, 1, 0, 22);

SET FOREIGN_KEY_CHECKS = 1;
