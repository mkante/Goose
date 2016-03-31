package kante.goose

import kante.goose.template.BaseTemplate
import kante.goose.template.TemplateFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by moh on 3/30/16.
 */
class Supervisor
{

    protected Logger log ;
    protected ExtentionParameter config;
    protected BaseTemplate sqlTemplate;
    File rootDir;

    public Supervisor(ExtentionParameter config) {
        log = LoggerFactory.getLogger(this.getClass());
        this.config = config;

        sqlTemplate = TemplateFactory.getTemplate(config.db.driver);
        rootDir = new File(config.dir);
    }

    public List<File> newFiles() {

        List<File> local = localFiles();
        List<File> cacheFiles = cacheFiles();
        if (cacheFiles.isEmpty()) {
            return local;
        }

        File lastCache = cacheFiles.last();
        log.info("Last cache file: "+lastCache);
        log.info("Local files: "+local);

        List<File> newFiles =
                local.findAll { file ->
                    isGreater(file.name, lastCache.name);
                }

        return newFiles;
    }

    protected boolean isGreater(String nameA, String nameB) {

        String partA = getDateFromName(nameA);
        String partB = getDateFromName(nameB);

        return partA > partB;
    }

    protected String getDateFromName(String name) {

        if (name == null) {
            return null;
        }

        Pattern pattern = ~/(\d{4}_\d{2}_\d{2}_\d+)/;

        Matcher mtchA = name.trim() =~ pattern ;

        String date = null;

        try {
            date = mtchA[0][0];
        }
        catch (IndexOutOfBoundsException e) {
            log.warn(e.message);
        }


        log.debug("'$name' matches "+date);

        return date ;
    }

    public List<File> localFiles(boolean desc = false) {

        if (!rootDir.isDirectory()) {
            throw new FileNotFoundException("Can't resolve file: "+rootDir);
        }

        List<File> lists = rootDir.listFiles();

        if (desc) {
            //lists = lists.sort{ a,b -> b.name.compareTo(a.name) }
            lists = lists.reverse();
        }

        return lists;
    }

    public List<File> cacheFiles(boolean desc = false) {

        String sql = sqlTemplate.allFiles(config.table) ;

        List<File> files = [];
        DB.SQL.eachRow(sql) { row ->

            files << new File(rootDir, row.file) ;
        }

        if (desc) {
            //files = files.sort{ a,b -> b.name.compareTo(a.name) }
            files = files.reverse();
        }

        return files;
    }
}
