CREATE TABLE `post` (
  `visible` bit(1) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `summary` text DEFAULT NULL,
  `text` text DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE INDEX `idx_post_visible` ON post(`visible`);
CREATE INDEX `idx_post_date` ON post(`date`);

CREATE TABLE `user` (
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `media` (
  `id` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `uploaded_at` datetime(6) DEFAULT NULL,
  `original_filename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE INDEX `idx_media_uploaded_at` ON media(`uploaded_at`);
