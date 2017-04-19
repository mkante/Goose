TRUNCATE goose_migrations RESTART IDENTITY;

INSERT INTO goose_migrations (file) VALUES ('file1.sql');
INSERT INTO goose_migrations (file) VALUES ('file2.sql');
INSERT INTO goose_migrations (file) VALUES ('file3.sql');