CREATE TABLE posts (
  id VARCHAR(255) NOT NULL,
  title VARCHAR(255) NOT NULL,
  preview_text TEXT NOT NULL,
  full_text TEXT,
  slug VARCHAR(255) NOT NULL,
  date TIMESTAMPTZ NOT NULL,
  visible BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_post_visible ON posts (visible);
CREATE INDEX idx_post_date ON posts (date);

CREATE TABLE tags (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(32) NOT NULL,
  slug VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_tag_name ON tags (name);

CREATE TABLE post_tags (
  post_id VARCHAR(255) NOT NULL,
  tag_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts(id),
  FOREIGN KEY (tag_id) REFERENCES tags(id),
  PRIMARY KEY (post_id, tag_id)
);

CREATE TABLE users (
  active BOOLEAN NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  id VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE files (
  id VARCHAR(255) NOT NULL,
  path VARCHAR(255) NOT NULL,
  content_type VARCHAR(255),
  uploaded_at TIMESTAMPTZ,
  original_filename VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_files_original_filename ON files (original_filename);
CREATE INDEX idx_files_uploaded_at ON files (uploaded_at);
