DROP TABLE IF EXISTS goose_1_migrations;

CREATE TABLE goose_1_migrations (
  id int(11) DEFAULT NULL,
  file text NOT NULL,
  created_at timestamp NOT NULL
)