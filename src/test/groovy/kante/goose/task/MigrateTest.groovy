package kante.goose.task

import kante.goose.DB
import kante.goose.ExtentionParameter
import kante.goose.error.SqlTemplateError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertTrue;

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
@ActiveProfiles("mysql")
class MigrateTest
{
    @Autowired
    ExtentionParameter extParam;

    @Test
    @Sql("init-1.sql")
    public void init() {

        MigrateSeed.data1();
        File f = new File("/tmp/goose_mgs");

        ExtentionParameter prms = new ExtentionParameter();
        Migrate mg = null ;

        try {
            new Migrate(prms);
            assertTrue(false);
        }
        catch (SqlTemplateError e){
            assertTrue(true);
        }

        extParam.table = "some_mgs";
        extParam.dir = f.path;
        mg = new Migrate(extParam);
        assertTrue(true);

        mg.init();
        assertTrue(f.isDirectory());
        assertTrue(DB.tableExists(extParam.table));

        // init second time
        mg.init();
        assertTrue(true);
    }
}
