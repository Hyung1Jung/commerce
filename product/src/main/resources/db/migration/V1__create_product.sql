CREATE TABLE `product`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `created_at`   datetime(6) NOT NULL,
    `updated_at`   datetime(6) DEFAULT NULL,
    `product_id`   varchar(255) NOT NULL,
    `product_name` varchar(255) NOT NULL,
    `stock`        bigint       NOT NULL,
    `unit_price`   bigint       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci