package kante.goose

import kante.goose.error.ConfigError
import kante.goose.error.GoosePluginError
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before;
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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
class GoosePluginTest
{
    Project project;

    @Autowired
    Config conf;

    @Before
    public void before() {
        project = ProjectBuilder.builder().build();
        project.apply plugin:"kante.goose.migration";

        project.goose.configDir = "src/test/resources";
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

        project.goose.configDir = null;
        Task initTask = project.tasks['goose-init'];

        try {

            initTask.execute()
            assertTrue(false);
        }
        catch (Throwable e) {
            e.printStackTrace();
            assertTrue(e.cause instanceof ConfigError);
        }
    }

    @Test
    public void getConfiguration() {
        GoosePlugin plg = new GoosePlugin();
        System.properties["profile"] =  "  ";
        assertEquals("goose.properties", plg.configFile);
        System.properties.profile = "prod";
        assertEquals("goose-prod.properties", plg.configFile);
        System.properties.profile = "dev   ";
        assertEquals("goose-dev.properties", plg.configFile);
    }

    @Test
    @Sql("data-2.sql")
    public void init2() {

        GoosePluginSeed.data3();
        project.goose.configDir = "src/test/resources";
        project.goose.configFile = "mysql_goose.properties";

        File f1 = new File("/tmp/goose_testred");
        assertTrue(!f1.isDirectory());

        String t1 = "goose_tested";
        assertTrue(!DB.tableExists(t1))

        Task initTask = project.tasks['goose-init'];

        // set url
        conf.dir = f1.path;
        conf.table = t1;

        project.goose.table = conf.table ;
        project.goose.dir = conf.dir;

        println "URL= "+conf.db;
        println "Extention "+project.goose;
        println "State= "+initTask.state ;

        initTask.execute()

        assertTrue(f1.isDirectory());
        assertTrue(DB.tableExists(t1))
    }

}
