CREATE TABLE `order_item`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) NOT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    `order_id`    varchar(255) NOT NULL,
    `product_id`  varchar(255) NOT NULL,
    `quantity`    bigint       NOT NULL,
    `total_price` bigint       NOT NULL,
    `unit_price`  bigint       NOT NULL,
    `user_id`     bigint       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci