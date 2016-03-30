package kante.goose.task

import kante.goose.DB
import kante.goose.ExtentionParameter
import kante.goose.template.BaseTemplate
import kante.goose.template.TemplateFactory
import kante.goose.util.FileResolver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static java.lang.System.out;

/**
 * Created by moh on 3/30/16.
 */
public class Migrate
{

    protected ExtentionParameter config;
    protected Logger log;
    protected BaseTemplate sqlTmplt;

    public Migrate(ExtentionParameter param) {
        log = LoggerFactory.getLogger(this.getClass());
        config = param;

        sqlTmplt =
                TemplateFactory.getTemplate(param.db.driver);
    }

    public void init() {

        log.debug("Creating migrations dir= "+config.dir);
        File file = new File(config.dir);
        if (!file.isDirectory()) {
            file.mkdir();
        }

        log.debug("Creating migrations table "+config.table);
        if (DB.tableExists(config.table)) {
            log.trace("Table already created");
            return;
        }
        String sql = sqlTmplt.init(config.table);
        DB.SQL.execute(sql);
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

    }

    public void reset() {

    }

    public void rollback() {

    }

    public void next() {

    }

}
