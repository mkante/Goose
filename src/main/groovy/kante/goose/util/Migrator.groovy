package kante.goose.util

import kante.goose.DB
import kante.goose.error.MigratorError
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by moh on 3/30/16.
 */
class Migrator
{
    protected File migration;
    protected Logger log;

    String scriptDelimiter = ';';

    public Migrator(File migration) {
        log = LoggerFactory.getLogger(this.getClass());

        if (!migration.isDirectory()) {
            throw new MigratorError("Invalid migraiton: "+migration);
        }

        this.migration = migration;
    }

    public void up() {

        File file = new File(migration, "up.sql");
        if (!file.isFile()) {
            throw new MigratorError("Your migration is missing 'up.sql'");
        }

        String script = file.text;
        log.debug("Migrator up: "+script);

        executeScript(script);
    }

    public void down() {

        File file = new File(migration, "down.sql");
        if (!file.isFile()) {
            throw new MigratorError("Your migration is missing 'down.sql'");
        }

        String script = file.text;
        log.debug("Migrator down: "+script);

        executeScript(script);
    }

    protected void executeScript(String script) {

        String[] stmnts = script.split(scriptDelimiter);

        stmnts.each { sql ->

            sql = sql.trim();
            if (sql.isEmpty()) {
                return;
            }

            DB.SQL.execute(sql);
        }
    }
}
