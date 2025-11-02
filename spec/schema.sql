CREATE TABLE posts (
  visible BIT(1) NOT NULL,
  date DATETIME(6) NOT NULL,
  id VARCHAR(255) NOT NULL,
  summary TEXT NOT NULL,
  text TEXT,
  title VARCHAR(255) NOT NULL,
  slug VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_post_visible ON posts (visible);
CREATE INDEX idx_post_date ON posts (date);

CREATE TABLE users (
  active BIT(1) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  id VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE files (
  id VARCHAR(255) NOT NULL,
  path VARCHAR(255) NOT NULL,
  content_type VARCHAR(255),
  uploaded_at DATETIME(6),
  original_filename VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_media_uploaded_at ON files (uploaded_at);
