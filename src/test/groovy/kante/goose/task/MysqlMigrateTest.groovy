package kante.goose.task

import kante.goose.DB
import kante.goose.Config
import kante.goose.error.SqlTemplateError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
class MysqlMigrateTest
{
    @Autowired
    Config extParam;

    @Test
    @Sql("init-1.sql")
    public void init() {

        MigrateSeed.data1();
        File f = new File("/tmp/goose_mgs");

        Config prms = new Config();
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

    @Test
    public void createFile() {

        File f1 = new File("/tmp/goose_mgs_2");
        MigrateSeed.data2();

        extParam.dir = f1.path;
        Migrate mg = new Migrate(extParam);

        File f2 = mg.createFile("DDL-1") ;

        assertTrue(f2.isDirectory());

        File upFile = new File(f2, "up.sql");
        File downFile = new File(f2, "down.sql");

        assertTrue(upFile.isFile());
        assertTrue(downFile.isFile());
    }

    @Test
    @Sql("init-2.sql")
    public void applyFiles() {

        MigrateSeed.data3();

        extParam.table = "goose_1_migrations"
        extParam.dir = "/tmp/goose_apply";

        List<File> files = [];
        files << new File(extParam.dir, "2016_02_10_0001_DDL");
        files << new File(extParam.dir, "2016_02_13_0001_DDL");

        Migrate mgrt = new Migrate(extParam);
        mgrt.applyFiles(files, Migrate.Direction.UP);

        def row = DB.SQL.firstRow("SELECT * FROM goose_1");
        assertTrue(row.id == 100);
        assertEquals(row.name, 'chris_brown');
        assertTrue(row.created_at instanceof Date);

        String cacheSql =
                """
                SELECT * FROM goose_1_migrations
                ORDER BY created_at ASC
                """;
        List cacheMgs = DB.SQL.rows(cacheSql);
        assertEquals(cacheMgs[0].file, "2016_02_10_0001_DDL");
        assertEquals(cacheMgs[1].file, "2016_02_13_0001_DDL");

        // Down grade
        List<File> revFiles = files.reverse();
        mgrt.applyFiles(revFiles , Migrate.Direction.DOWN);

        assertTrue(!DB.tableExists("goose_1"));

        cacheMgs = DB.SQL.rows(cacheSql);
        assertTrue(cacheMgs.isEmpty());
    }

    @Test
    @Sql("init-3.sql")
    public void walk() {

        MigrateSeed.data4();

        extParam.table = "dream_1_migrations";
        extParam.dir = "/tmp/goose_dream_1";

        String sql1 = "SELECT * FROM dream_1_migrations ORDER BY created_at ASC";
        def rows = null;

        Migrate mgrt = new Migrate(extParam);

        // Test init
        mgrt.init();
        assertTrue(new File(extParam.dir).isDirectory());
        assertTrue(DB.isTableEmpty(extParam.table));

        mgrt.next();
        assertTrue(DB.isTableEmpty(extParam.table));

        // New Files
        mgrt.createFile("DDL1");
        mgrt.createFile("DDL2");
        mgrt.createFile("DDL3");

        // 1 step forward
        mgrt.next();
        rows = DB.SQL.rows(sql1) ;
        assertTrue(rows.size() == 1);
        assertTrue(rows[0].file ==~ ".*_DDL1" );

        // run all
        mgrt.run();
        rows = DB.SQL.rows(sql1) ;
        assertTrue(rows.size() == 3);
        assertTrue(rows[0].file ==~ ".*_DDL1" );
        assertTrue(rows[1].file ==~ ".*_DDL2" );
        assertTrue(rows[2].file ==~ ".*_DDL3" );

        // 1 step backward
        mgrt.rollback();
        rows = DB.SQL.rows(sql1) ;
        assertTrue(rows.size() == 2);
        assertTrue(rows[0].file ==~ ".*_DDL1" );
        assertTrue(rows[1].file ==~ ".*_DDL2" );

        // reset
        mgrt.reset();
        assertTrue(DB.isTableEmpty(extParam.table));

        mgrt.rollback();
        assertTrue(DB.isTableEmpty(extParam.table));

        // All migration files are presents
        File root = new File(extParam.dir);
        assertTrue(root.isDirectory());

        assertTrue(root.listFiles().size() == 3);
    }
}
