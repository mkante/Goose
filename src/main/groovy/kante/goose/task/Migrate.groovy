package kante.goose.task

import kante.goose.DB
import kante.goose.Config
import kante.goose.Supervisor
import kante.goose.template.BaseTemplate
import kante.goose.template.TemplateFactory
import kante.goose.util.FileResolver
import kante.goose.util.Migrator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by moh on 3/30/16.
 */
public class Migrate
{

    public static enum Direction { UP, DOWN }

    protected Config config;
    protected Logger log;
    protected BaseTemplate sqlTmplt;
    protected Supervisor supervisor;

    public Migrate(Config param) {
        log = LoggerFactory.getLogger(this.getClass());
        config = param;

        sqlTmplt =
                TemplateFactory.getTemplate(param.db.driver);

        supervisor = new Supervisor(param);
    }

    public void init() {

        log.debug("Creating migrations dir= "+config.dir);
        File file = new File(config.dir);
        if (!file.isDirectory()) {
            file.mkdir();
        }

        log.debug("Creating migrations table "+config.table);
        if (DB.tableExists(config.table)) {
            log.println("Table already created");
            return;
        }
        String sql = sqlTmplt.init(config.table);
        DB.SQL.execute(sql);

        log.info("Initialized");
    }

    public File createFile(String name) {

        String fileName = FileResolver.newDirName(name);

        File dir = new File(config.dir+"/"+fileName);
        log.debug("Creating migration file: "+dir.path);

        dir.mkdirs();

        File up = new File(dir, "up.sql");
        up.createNewFile();

        File down = new File(dir, "down.sql");
        down.createNewFile();

        return dir;
    }

    public void run() {

        List<File> files = supervisor.newFiles();
        applyFiles(files, Direction.UP);
    }

    public void next() {

        List<File> files = supervisor.newFiles();
        if (files.isEmpty()) {
            log.println("No migration.");
            return;
        }

        File file = files.first();
        applyFiles([file], Direction.UP);
    }

    public void reset() {

        List<File> files = supervisor.cacheFiles(true);
        applyFiles(files, Direction.DOWN);
    }

    public void rollback() {

        List<File> files = supervisor.cacheFiles(true);
        if (files.isEmpty()) {
            log.println("No migration.");
            return;
        }

        File file = files.first();
        applyFiles([file], Direction.DOWN);
    }

    protected void applyFiles(List<File> files, Direction direction) {

        if (files.isEmpty()) {
            log.println("No migration.");
            return;
        }

        files.forEach { file ->

            Migrator mgr = new Migrator(file);

            switch (direction) {
                case Direction.UP:
                    mgr.up();
                    dbInsert(file.name);
                    log.println("Added: "+file.name);
                    break;

                case Direction.DOWN:
                    mgr.down();
                    dbDelete(file.name);
                    log.println("Rollback: "+file.name);
                    break;
            }
        }
    }

    protected void dbInsert(String fileName) {

        String sql =
                sqlTmplt.insert([
                        'table': config.table,
                        'file': fileName,
                ]);
        DB.SQL.execute(sql);
    }

    protected void dbDelete(String fileName) {

        String sql =
                sqlTmplt.delete([
                        'table': config.table,
                        'file': fileName,
                ]);
        DB.SQL.execute(sql);
    }

}
