package kante.goose

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

        project.extensions.create('goose', ExtentionParameter.class);

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

            String name = properties.name;
            File dir = newMigrate(project).createFile(names);
            log.info("migration: "+dir.path+" created");
        }

        project.task('goose-migrate',
                group: pluginGroup) << {

            newMigrate(project).run();
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

        DB.init(project.goose);
        return new Migrate(project.goose);
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