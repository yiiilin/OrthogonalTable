/*
Navicat MySQL Data Transfer

Source Server         : 阿里云Mysql
Source Server Version : 50726
Source Host           : rm-uf6ck33a76x59qa8q9o.mysql.rds.aliyuncs.com:3306
Source Database       : testcase

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-03-15 20:13:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `testcase`
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seed` varchar(50) DEFAULT NULL,
  `kind` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_skn` (`seed`,`kind`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testcase
-- ----------------------------
INSERT INTO `testcase` VALUES ('104', '0', '123', 'cdf');
INSERT INTO `testcase` VALUES ('102', '0', '123', 'dqw');
INSERT INTO `testcase` VALUES ('105', '0', '123', 'we');
INSERT INTO `testcase` VALUES ('103', '0', '123', 'zx');
INSERT INTO `testcase` VALUES ('106', '0', '5345', 'dqw');
INSERT INTO `testcase` VALUES ('107', '0', '5345', 'ge');
INSERT INTO `testcase` VALUES ('109', '0', '5345', 'tyh');
INSERT INTO `testcase` VALUES ('108', '0', '5345', 'xcv');
INSERT INTO `testcase` VALUES ('115', '0', '567', 'asd');
INSERT INTO `testcase` VALUES ('114', '0', '567', 'fe');
INSERT INTO `testcase` VALUES ('116', '0', '567', 'ferw');
INSERT INTO `testcase` VALUES ('117', '0', '567', 'vedfve');
INSERT INTO `testcase` VALUES ('110', '0', 'qwe', 'asdq');
INSERT INTO `testcase` VALUES ('111', '0', 'qwe', 'cs');
INSERT INTO `testcase` VALUES ('113', '0', 'qwe', 'grte');
INSERT INTO `testcase` VALUES ('112', '0', 'qwe', 'vrce');
INSERT INTO `testcase` VALUES ('118', '0', 'yth', 'dwedwq');
INSERT INTO `testcase` VALUES ('121', '0', 'yth', 'sdf');
INSERT INTO `testcase` VALUES ('120', '0', 'yth', 'vrfev');
INSERT INTO `testcase` VALUES ('119', '0', 'yth', 'zxcw');
INSERT INTO `testcase` VALUES ('122', '1', '1', '1');
INSERT INTO `testcase` VALUES ('123', '1', '1', '2');
INSERT INTO `testcase` VALUES ('124', '1', '2', '1');
INSERT INTO `testcase` VALUES ('125', '1', '2', '2');
INSERT INTO `testcase` VALUES ('126', '1', '3', '1');
INSERT INTO `testcase` VALUES ('127', '1', '3', '2');
INSERT INTO `testcase` VALUES ('128', '1', '4', '1');
INSERT INTO `testcase` VALUES ('129', '1', '4', '2');
INSERT INTO `testcase` VALUES ('130', '1', '5', '1');
INSERT INTO `testcase` VALUES ('131', '1', '5', '2');
INSERT INTO `testcase` VALUES ('132', '1', '5', '3');
INSERT INTO `testcase` VALUES ('133', '1', '5', '4');
