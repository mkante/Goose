package kante.goose

import kante.goose.error.ConfigError
import kante.goose.task.Migrate
import org.gradle.api.Project;
import org.gradle.api.Plugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory;

import static java.lang.System.out;

public class GoosePlugin implements Plugin<Project>
{
    protected Logger log ;

    public GoosePlugin() {
        super();
        log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    void apply(Project project) {

        def pluginGroup = "Goose migration"

        project.extensions.create('goose', ExternalParams.class);

        project.task('goose',
                group: pluginGroup) << {
            this.help();
        }

        project.task('goose-init',
                group: pluginGroup) << {

            newMigrate(project).init();
        }

        project.task('goose-make',
                group: pluginGroup) << {

            String name = System.properties.name;
            File dir = newMigrate(project).createFile(name);
            project.logger.println("migration: "+dir.path+" created");
        }

        project.task('goose-migrate',
                group: pluginGroup) << {

            newMigrate(project).run();
            project.logger.println("Migration done");

        }

        project.task('goose-next',
                group: pluginGroup) << {

            newMigrate(project).next();
        }

        project.task('goose-reset',
                group: pluginGroup) << {

            newMigrate(project).reset();
        }

        project.task('goose-rollback', group: pluginGroup) << {
            newMigrate(project).rollback();
        }

    }

    protected Migrate newMigrate(Project project) {

        Config conf = new Config();
        conf.dir = project.goose.dir;
        conf.table = project.goose.table;

        String cfgFile = project.goose.configFile;
        cfgFile = (cfgFile == null)? "goose.properties" : cfgFile;

        File confFile = new File(project.goose.configDir+"/"+cfgFile);
        if (!confFile.isFile()) {
            throw new ConfigError("can't locate config file: "+confFile);
        }

        log.info "Found config file: "+confFile;

        Properties props = new Properties();
        props.load (new FileInputStream(confFile));

        log.info "Loaded properties: "+props;

        conf.db.driver = props['db.driver'];
        conf.db.url = props['db.url'];
        conf.db.user = props['db.user'];
        conf.db.password = props['db.password'];

        DB.init(conf);
        return new Migrate(conf);
    }

    protected void help() {
        String msg = """
        goose-init
        goose-make
        goose-migrate
        goose-reset
        goose-next
        goose-rollback
        """;

        out.println(msg);
    }
}