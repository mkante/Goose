package kante.goose.util

import kante.goose.DB
import kante.goose.error.MigratorError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import javax.naming.Context;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
@ActiveProfiles("mysql")
class MigratorTest
{


    @Test
    public void testUp() {

        MigratorSeed.data1();

        File m0 = new File("/tmp/blabla");
        File m1 = new File("/tmp/owesome_mg1");
        File m2 = new File("/tmp/owesome_mg2");

        Migrator mgr = null;

        try{
            mgr = new Migrator(m0);
            assertTrue(false);
        }
        catch (MigratorError e){
            assertTrue(true);
        }

        mgr = new Migrator(m1);
        try{
            mgr.up() ;
            assertTrue(false);
        }
        catch (MigratorError e){
            assertTrue(true);
        }

        mgr = new Migrator(m2);
        mgr.up() ;

        assertTrue(DB.tableExists("sushi_1"));

        mgr.down() ;
        assertTrue(!DB.tableExists("sushi_1"));
        assertTrue(DB.tableExists("sushi_2"));
    }
}
