/*
Navicat MySQL Data Transfer

Source Server         : limk
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mybank

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-07-15 15:15:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(255) COLLATE utf8_bin NOT NULL,
  `userId` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_userid2user_userId` (`userId`),
  CONSTRAINT `account_userid2user_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '张三', '445281199999');
INSERT INTO `account` VALUES ('2', '李四', '564651365165');

-- ----------------------------
-- Table structure for bank
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(255) COLLATE utf8_bin NOT NULL,
  `fatherBankId` int(11) NOT NULL,
  `bankAddr` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of bank
-- ----------------------------
INSERT INTO `bank` VALUES ('1', '北京总行', '1', '北京');
INSERT INTO `bank` VALUES ('2', '广东省总行', '1', '广州天河');
INSERT INTO `bank` VALUES ('3', '广州分行', '2', '广州');
INSERT INTO `bank` VALUES ('4', '深圳分行', '2', '深圳');

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cardId` varchar(255) COLLATE utf8_bin NOT NULL,
  `cardType` int(11) NOT NULL COMMENT '0:储蓄卡  1 信用卡',
  `belongAccountId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `serviceCharge` double NOT NULL COMMENT '开发手续费',
  `bankId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cardId` (`cardId`),
  KEY `card_bankid2bank_id` (`bankId`),
  KEY `card_belongid2account_id` (`belongAccountId`),
  CONSTRAINT `card_bankid2bank_id` FOREIGN KEY (`bankId`) REFERENCES `bank` (`id`),
  CONSTRAINT `card_belongid2account_id` FOREIGN KEY (`belongAccountId`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES ('1', '752452452', '0', '1', '2019-07-03 19:13:08', '30', '3');
INSERT INTO `card` VALUES ('2', '6353563563', '1', '1', '2019-07-06 19:13:29', '30', '3');
INSERT INTO `card` VALUES ('3', '1243124124', '1', '2', '2019-07-01 19:15:45', '30', '4');
INSERT INTO `card` VALUES ('4', '54235235', '1', '2', '2019-07-04 19:16:03', '30', '2');
INSERT INTO `card` VALUES ('5', '1232423423', '1', '2', '2019-06-13 09:01:41', '30', '2');
INSERT INTO `card` VALUES ('6', '1243454656', '1', '2', '2018-06-29 14:54:45', '30', '2');

-- ----------------------------
-- Table structure for cashcardinfo
-- ----------------------------
DROP TABLE IF EXISTS `cashcardinfo`;
CREATE TABLE `cashcardinfo` (
  `cardId` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `tranMoney` double NOT NULL,
  `tranType` int(11) NOT NULL COMMENT '0：转入  1：转出',
  `cardMoney` double(255,0) NOT NULL,
  `serviceCharge` double NOT NULL,
  PRIMARY KEY (`cardId`,`createTime`),
  CONSTRAINT `cashcard_cardid2card_cardid` FOREIGN KEY (`cardId`) REFERENCES `card` (`cardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of cashcardinfo
-- ----------------------------
INSERT INTO `cashcardinfo` VALUES ('752452452', '2019-07-04 19:14:03', '100', '0', '100', '0.1');
INSERT INTO `cashcardinfo` VALUES ('752452452', '2019-07-08 19:15:06', '50', '1', '50', '0.1');

-- ----------------------------
-- Table structure for creditcardinfo
-- ----------------------------
DROP TABLE IF EXISTS `creditcardinfo`;
CREATE TABLE `creditcardinfo` (
  `cardId` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `tranMoney` double NOT NULL,
  `tranType` int(11) NOT NULL,
  `cardMoney` double NOT NULL COMMENT '0：消费  1：还款',
  `serviceCharge` double NOT NULL,
  PRIMARY KEY (`cardId`,`createTime`),
  CONSTRAINT `creditcard_cardid2card_cardid` FOREIGN KEY (`cardId`) REFERENCES `card` (`cardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of creditcardinfo
-- ----------------------------

-- ----------------------------
-- Table structure for loan
-- ----------------------------
DROP TABLE IF EXISTS `loan`;
CREATE TABLE `loan` (
  `id` int(11) NOT NULL,
  `accountId` int(11) NOT NULL,
  `loanMoney` double NOT NULL,
  `loanType` int(11) NOT NULL COMMENT '0 贷款；1 还款',
  `creatTime` datetime NOT NULL,
  `serviceCharge` double NOT NULL COMMENT '贷款/还款 手续费',
  `bankId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`creatTime`),
  KEY `loan_accountid2account_id` (`accountId`),
  KEY `loan_bankid2bank_id` (`bankId`),
  CONSTRAINT `loan_accountid2account_id` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`),
  CONSTRAINT `loan_bankid2bank_id` FOREIGN KEY (`bankId`) REFERENCES `bank` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of loan
-- ----------------------------

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `level` varchar(255) COLLATE utf8_bin NOT NULL,
  `belongBank` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `manger_belongbank2bank_bankid` (`belongBank`),
  CONSTRAINT `manger_belongbank2bank_bankid` FOREIGN KEY (`belongBank`) REFERENCES `bank` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of manager
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `userId` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `addr` varchar(255) COLLATE utf8_bin NOT NULL,
  `mobile` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '445281199999', '普宁', '18814444');
INSERT INTO `user` VALUES ('2', '李四', '564651365165', '汕尾', '89415163');
INSERT INTO `user` VALUES ('3', '王五', '445281111111', '汕头', '1884123655');
