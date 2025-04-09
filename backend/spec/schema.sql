CREATE TABLE post (
  visible BIT(1) NOT NULL,
  date DATETIME(6),
  id VARCHAR(255) NOT NULL,
  summary TEXT,
  text TEXT,
  title VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_post_visible ON post(visible);
CREATE INDEX idx_post_date ON post(date);

CREATE TABLE user (
  active BIT(1) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  id VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE media (
  id VARCHAR(255) NOT NULL,
  path VARCHAR(255) NOT NULL,
  content_type VARCHAR(255),
  uploaded_at DATETIME(6),
  original_filename VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX idx_media_uploaded_at ON media (uploaded_at);
