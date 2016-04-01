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

        project.extentions.create('goose', ExtentionParameter.class);

        project.task('goose') {
            this.help();
        }

        project.task('goose-init') {
            newMigrate(project).init();
        }

        project.task('goose-make') {
            String name = properties.name;
            File dir = newMigrate(project).createFile(names);
            log.info("migration: "+dir.path+" created");
        }

        project.task('goose-migrate') {
            newMigrate(project).run();
        }

        project.task('goose-next') {
            newMigrate(project).next();
        }

        project.task('goose-reset') {
            newMigrate(project).reset();
        }

        project.task('goose-rollback') {
            newMigrate(project).rollback();
        }

    }

    protected Migrate newMigrate(Project project) {

        DB.init(project.goose);
        return new Migrate(project.goose);
    }

    protected void help() {
        String msg = """
        goose
            init
            make
            migrate
            migrate:reset
            migrate:next
            migrate:rollback
            help
        """;

        out.println(msg);
    }
}