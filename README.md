Goose
---
A database migration plugin for Gradle. This was inspired from Laravel artisan migration tool.

##### Dependencies

>- JDK 1.6+
>- Gralde 2.3+

> ONLY MYSQL IS SUPPORTED FOR NOW

Install
----
Inside your `build.gralde`

```
apply plugin:"kante.goose.migration"
```
Make sure `kante.goose:goose:1.0-SNAPSHOT` is in your builscript classpath

Configuration
----
Setup your database credentials

```
goose {
	db {
		url = 'jdbc:mysql://localhost/my_db'
		user = 'test'
		password = 'tets'
		driver = 'com.mysql.jdbc.Driver'
	}
}
```

Don't forget to add the correct JDBC connector to classpath. Checkout [Maven repoository](http://mvnrepository.com)

Generating Migrations
----
To create a migration, use the `goose-make` task:

```
gradle goose-make -i
```

The new migration will be placed in your `src/main/resources/migrations` directory. Each migration file name contains a timestamp which allows Goose to determine the order of the migrations.

The `-Dname=some_name` property may also be used to indicate the name of the file.

```
gradle goose-make -Dname=create_users_table -i
```

Migration Structure
----
A migration directory contains two files: `up.sql` and `down.sql`. The up.sql file is used to add new tables, columns, or indexes to your database, while the down.sql method should simply reverse the operations performed by the up method.

Within both of these files you may use the SQL statements support my your database to expressively create and modify tables. 

```
// Inside up.sql 
CREATE TABLE users (
	id INT PRIMAY KEY,
	name VARCHAR(200),
	email VARCHAR(200),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

// Inside down.sql 
DROP TABLE users;

```

Running Migrations
----
To run all outstanding migrations for your application, use the __goose-migrate__ task. 

```
gradle goose-migrate -i
```

The **goose-next** task will run the next migration of your application:

```
gradle goose-next -i
```

Rolling Back Migrations
----
To rollback the latest migration "operation", you may use the **goose-rollback** task. Note that this rolls back the last "batch" of migrations that ran, which may include multiple migration files:

```
gradle goose-rollback -i
```

The **goose-reset** task will roll back all of your application's migrations:

```
gradle goose-reset -i
```

Tasks
----
Goose plugin will add following tasks to your project.

```
goose
goose-init
goose-make
goose-run
goose-next
goose-rollback
goose-reset
```
#### goose-init
To initialize the migration table and directory. You are required to call this task before any other commands.

The default migration cache table is `goose_migrations` and the default directory is `src/main/resources/goose/migrations`.

You cant change those settings with:

```
goose {
	...
	dir = 'some/path/relatif/to/the/project'
	table = 'custom_migratsins'
}
```

#### goose-make
Create a new migration.

#### goose-migrate

To run all new migrations

#### goose-next
To run the next migration

#### goose-rollback
To rollback the last migration

#### goose-reset
To reset all migrations


