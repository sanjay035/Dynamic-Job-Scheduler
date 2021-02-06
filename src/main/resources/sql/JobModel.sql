USE QUARTZ_SCHEMA;

CREATE TABLE IF NOT EXISTS `cron_jobs` (
  `jobid` bigint NOT NULL AUTO_INCREMENT,
  `body` json DEFAULT NULL,
  `callback_url` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `delay_between_retries` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `headers` json DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `param` json DEFAULT NULL,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `retry_count` int DEFAULT NULL,
  PRIMARY KEY (`jobid`)
);