/*
Navicat MySQL Data Transfer

Source Server         : limk
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mybank

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-07-03 09:46:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(255) COLLATE utf8_bin NOT NULL,
  `userId` varchar(11) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_userid2user_userId` (`userId`),
  CONSTRAINT `account_userid2user_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of account
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of bank
-- ----------------------------

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
  KEY `card_belongid2account_id` (`belongAccountId`),
  KEY `card_bankid2bank_id` (`bankId`),
  KEY `cardId` (`cardId`),
  CONSTRAINT `card_bankid2bank_id` FOREIGN KEY (`bankId`) REFERENCES `bank` (`id`),
  CONSTRAINT `card_belongid2account_id` FOREIGN KEY (`belongAccountId`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of card
-- ----------------------------

-- ----------------------------
-- Table structure for cashcardinfo
-- ----------------------------
DROP TABLE IF EXISTS `cashcardinfo`;
CREATE TABLE `cashcardinfo` (
  `cardId` varchar(11) COLLATE utf8_bin NOT NULL,
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

-- ----------------------------
-- Table structure for creditcardinfo
-- ----------------------------
DROP TABLE IF EXISTS `creditcardinfo`;
CREATE TABLE `creditcardinfo` (
  `cardId` varchar(11) COLLATE utf8_bin NOT NULL,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
-- Table structure for User
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of User
-- ----------------------------
