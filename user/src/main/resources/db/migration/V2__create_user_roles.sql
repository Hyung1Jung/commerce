CREATE TABLE `user_roles`
(
    `user_id` bigint NOT NULL,
    `roles`   varchar(255) DEFAULT NULL,
    KEY       `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
    CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci