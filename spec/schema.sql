CREATE DATABASE blog;

USE blog;

CREATE TABLE posts (
  visible BIT(1) NOT NULL,
  date DATETIME(6) NOT NULL,
  id VARCHAR(255) NOT NULL,
  summary TEXT NOT NULL,
  text TEXT,
  title VARCHAR(255) NOT NULL,
  slug VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) COLLATE utf8mb4_uca1400_ai_ci;

CREATE INDEX idx_post_visible ON posts (visible);
CREATE INDEX idx_post_date ON posts (date);

CREATE TABLE tags (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(32) NOT NULL,
  slug VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (id)
) COLLATE utf8mb4_uca1400_ai_ci;

CREATE UNIQUE INDEX idx_tag_name ON tags (name);

CREATE TABLE post_tags (
  post_id VARCHAR(255) NOT NULL,
  tag_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts(id),
  FOREIGN KEY (tag_id) REFERENCES tags(id),
  PRIMARY KEY (post_id, tag_id)
) COLLATE utf8mb4_uca1400_ai_ci;

CREATE TABLE users (
  active BIT(1) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  id VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) COLLATE utf8mb4_uca1400_ai_ci;

CREATE TABLE files (
  id VARCHAR(255) NOT NULL,
  path VARCHAR(255) NOT NULL,
  content_type VARCHAR(255),
  uploaded_at DATETIME(6),
  original_filename VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) COLLATE utf8mb4_uca1400_ai_ci;

CREATE INDEX idx_files_original_filename ON files (original_filename);
CREATE INDEX idx_files_uploaded_at ON files (uploaded_at);
