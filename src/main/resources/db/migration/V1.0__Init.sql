-- ----------------------------
-- Table structure for apis
-- ----------------------------
CREATE TABLE `apis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `is_case` tinyint(1) DEFAULT NULL,
  `priority_level` varchar(10) DEFAULT NULL,
  `accept` varchar(255) DEFAULT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `request_body` varchar(2000) DEFAULT NULL,
  `request_params` varchar(2000) DEFAULT NULL,
  `path_variables` varchar(2000) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `basic_username` varchar(255) DEFAULT NULL,
  `basic_password` varchar(255) DEFAULT NULL,
  `cookie_type` varchar(10) DEFAULT NULL,
  `cookie_names` varchar(255) DEFAULT NULL,
  `store_cookie_key` varchar(40) DEFAULT NULL,
  `with_cookie_key` varchar(40) DEFAULT NULL,
  `test_order` int(11) DEFAULT NULL,
  `response_body` varchar(2000) DEFAULT NULL,
  `status_is` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `run_once` tinyint(1) DEFAULT NULL,
  `run_count` bigint(20) DEFAULT 0,
  `api_source` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for groups
-- ----------------------------
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `project_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for projects
-- ----------------------------
CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `base_url` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for classes
-- ----------------------------
CREATE TABLE `classes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `md5` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);


-- ----------------------------
-- Table structure for results
-- ----------------------------
CREATE TABLE `results` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `success` tinyint(1) NOT NULL,
  `request_body` varchar(2000) DEFAULT NULL,
  `response_body` varchar(2000) DEFAULT NULL,
  `request_params` varchar(2000) DEFAULT NULL,
  `path_variables` varchar(2000) DEFAULT NULL,
  `time` bigint(20) NOT NULL,
  `failed_msg` varchar(2000) DEFAULT NULL,
  `api_id` bigint(20) NOT NULL,
  `report_id` varchar(255) NOT NULL,
  `start` datetime(6) NOT NULL,
  `method` varchar(10) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `status_code` int(4) NOT NULL,
  PRIMARY KEY (`id`)
);