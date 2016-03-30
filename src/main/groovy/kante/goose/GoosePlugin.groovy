package kante.goose

import kante.goose.task.Migrate
import org.gradle.api.Project;
import org.gradle.api.Plugin;
import static java.lang.System.out;

public class GoosePlugin implements Plugin<Project>
{

    @Override
    void apply(Project project) {

        project.extentions.create('goose', ExtentionParameter.class);

        project.task('goose') {

            DB.init(project.goose);
            Migrate mgrTask = new Migrate(project.goose);

            switch(params) {
                case 'init':
                    mgrTask.init();
                    break;
                case 'make':
                    String name = "";
                    mgrTask.createFile(prefix);
                    break;
                case 'migrate':
                    mgrTask.run();
                    break;
                case 'migrate:reset':
                    mgrTask.reset();
                    break;
                case 'migrate:next':
                    mgrTask.next();
                    break;
                case 'migrate:rollback':
                    mgrTask.rollback();
                    break;
                default: this.help();
            }
        }
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