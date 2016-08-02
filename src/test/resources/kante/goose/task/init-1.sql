DROP TABLE IF EXISTS goose_migrations;
CREATE TABLE goose_migrations (
  id int(11) DEFAULT NULL,
  file text NOT NULL,
  created_at timestamp NOT NULL
)