SET SQL_MODE='';

DROP TABLE IF EXISTS goose_migrations;
CREATE TABLE goose_migrations (
  id int(11) DEFAULT NULL,
  file text NOT NULL,
  created_at timestamp NOT NULL
);

INSERT INTO goose_migrations SET
file = '2016_09_01_00001_DDL',
created_at='2016-04-05 16:19:25'
;

INSERT INTO goose_migrations SET
file = '2016_10_01_00002_DDL',
created_at='2016-04-05 16:19:25'
;

INSERT INTO goose_migrations SET
file = '2016_11_01_00003_DDL',
created_at='2016-04-05 16:19:25'
;