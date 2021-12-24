CREATE TABLE `refresh_token`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `secret_key` bigint       NOT NULL,
    `token`      varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci