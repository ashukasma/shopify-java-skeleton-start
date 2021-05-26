-- Adminer 4.7.8 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;

DROP DATABASE IF EXISTS `shopify_db`;
CREATE DATABASE `shopify_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `shopify_db`;

DROP TABLE IF EXISTS `global_shopify_script`;
CREATE TABLE `global_shopify_script` (
  `id` int NOT NULL AUTO_INCREMENT,
  `src` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `event` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'onload',
  `version` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '1.0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `store_billings`;
CREATE TABLE `store_billings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` int NOT NULL,
  `charge_id` bigint NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `activated_date` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `store_id` (`store_id`),
  CONSTRAINT `store_billings_ibfk_4` FOREIGN KEY (`store_id`) REFERENCES `store_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `store_details`;
CREATE TABLE `store_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shopify_store_id` bigint NOT NULL,
  `my_shopify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `store_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `store_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `plan` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `owner_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_no` bigint DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `plan_renewal_date` date DEFAULT NULL,
  `currency` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `current_status` int NOT NULL DEFAULT '0' COMMENT '1 - paid plan, 0 - unpaid plan',
  `timezone` varchar(110) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `intro_tour` smallint NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `store_id` (`shopify_store_id`),
  UNIQUE KEY `store_id_2` (`shopify_store_id`),
  UNIQUE KEY `my_shopify_url` (`my_shopify_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `store_plan_details`;
CREATE TABLE `store_plan_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` int NOT NULL,
  `max_charge` int NOT NULL,
  `current_charge` int(10) unsigned zerofill NOT NULL,
  `discount` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `store_id` (`store_id`),
  CONSTRAINT `store_plan_details_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `store_scripts`;
CREATE TABLE `store_scripts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` int NOT NULL,
  `script_id` int NOT NULL,
  `script_tag_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `version` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `custom` int NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_id` (`store_id`),
  CONSTRAINT `store_scripts_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `store_users`;
CREATE TABLE `store_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2021-05-13 10:53:42
