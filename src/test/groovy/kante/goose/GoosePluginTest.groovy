package kante.goose

import kante.goose.error.GoosePluginError
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskState
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue;

/**
 * Created by moh on 4/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
@ActiveProfiles("mysql")
class GoosePluginTest
{
    Project project;

    @Autowired
    ExtentionParameter extParams;

    public GoosePluginTest() {
        project = ProjectBuilder.builder().build();
        project.apply plugin:"kante.goose.migration";
    }

    @Test
    public void testTasks() {


        List<String> taskNames = project.tasks*.name
        println "All tasks: "+taskNames;


        taskNames.contains([
                'goose',
                'goose-init',
                'goose-make',
                'goose-migrate',
                'goose-next',
                'goose-reset',
                'goose-rollback'
        ])
        assertTrue(true);
    }

    @Test
    public void init1() {

        Task initTask = project.tasks['goose-init'];

        try {

            initTask.execute()
            assertTrue(false);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            assertTrue(e.cause instanceof GoosePluginError);
        }
    }

    @Test
    @Sql("data-2.sql")
    public void init2() {

        GoosePluginSeed.data3();

        File f1 = new File("/tmp/goose_testred");
        assertTrue(!f1.isDirectory());

        String t1 = "goose_testred";
        assertTrue(!DB.tableExists(t1))

        Task initTask = null;
        initTask = project.tasks['goose-init'];

        // set url
        extParams.dir = f1.path;
        extParams.table = t1;

        project.goose.db = extParams.db ;
        project.goose.table = extParams.table ;
        project.goose.dir = extParams.dir;

        println "URL= "+extParams.db;
        println "Extention "+project.goose;
        println "State= "+initTask.state ;

        initTask.execute()

        assertTrue(f1.isDirectory());
        assertTrue(DB.tableExists(t1))
    }

}
