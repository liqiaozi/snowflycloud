/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : snowflycloud_cloud1

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-10-26 17:41:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_business_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_category`;
CREATE TABLE `sys_business_category` (
  `business_id` varchar(255) NOT NULL,
  `business_name` varchar(255) NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `sort` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`business_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_category
-- ----------------------------
INSERT INTO `sys_business_category` VALUES ('component', '组件', '001', '1', '1');
INSERT INTO `sys_business_category` VALUES ('product', '产品', '001', '2', '1');
INSERT INTO `sys_business_category` VALUES ('download', '下载', '002', '1', '1');
INSERT INTO `sys_business_category` VALUES ('framework', '构架', '002', '2', '1');
INSERT INTO `sys_business_category` VALUES ('env', '环境', '003', '1', '1');
INSERT INTO `sys_business_category` VALUES ('os', '镜像', '003', '2', '1');

-- ----------------------------
-- Table structure for sys_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client` (
  `clent_id` varchar(255) NOT NULL,
  `client_cn_name` varchar(255) NOT NULL,
  `client_en_name` varchar(255) NOT NULL,
  `customer_id` varchar(255) NOT NULL,
  `home_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO `sys_client` VALUES ('001', '集成开发平台', 'HiDO', '100', 'http://aaa/#/home');
INSERT INTO `sys_client` VALUES ('002', '软件管理平台', 'SMSP', '100', 'http://bbb/#/');
INSERT INTO `sys_client` VALUES ('003', '环境管理系统', 'EMS', '200', 'http://ccc/#/');

-- ----------------------------
-- Table structure for sys_customer
-- ----------------------------
DROP TABLE IF EXISTS `sys_customer`;
CREATE TABLE `sys_customer` (
  `customer_id` varchar(255) NOT NULL,
  `customer_en_name` varchar(255) NOT NULL,
  `customer_zn_name` varchar(255) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_customer
-- ----------------------------
INSERT INTO `sys_customer` VALUES ('100', 'caihaoliang', '蔡浩亮');
INSERT INTO `sys_customer` VALUES ('200', 'zhourui9', '周睿9');
