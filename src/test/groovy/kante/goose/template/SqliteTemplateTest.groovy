package kante.goose.template

import groovy.sql.GroovyRowResult
import kante.goose.DB
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.*

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-sqlite.xml")
class SqliteTemplateTest
{
    Logger log = LoggerFactory.getLogger(SqliteTemplateTest.class);
    SqliteTemplate template;
    String table = "goose_migrations";

    @Before
    public void before() {
        template = new SqliteTemplate();
        createDb();
    }

    @Test
    public void testInitializaton() {
        assertTrue(DB.tableExists(table))
    }

    @Test
    public void testDataInsertion() {
        assertTrue(DB.isTableEmpty(table))
        Map<String,Object> props = [
                table: this.table,
                file: "file1.sql"
            ];
        String sql = template.insert(props);
        DB.SQL.execute(sql);

        String selectSql = template.allFiles(table);
        List<GroovyRowResult> rows = DB.SQL.rows(selectSql);
        log.debug("Rows = " + rows);
        assertTrue(rows.size() == 1);
        assertEquals(1, rows[0].id);
        assertEquals("file1.sql", rows[0].file);
        assertNotNull(rows[0].created_at);
    }

    @Test
    public void testDeletion() {

        String sql1 = "INSERT INTO goose_migrations (file) VALUES ('file1.sql')";
        String sql2 = "INSERT INTO goose_migrations (file) VALUES ('file2.sql')";
        String sql3 = "INSERT INTO goose_migrations (file) VALUES ('file3.sql')";
        DB.SQL.execute(sql1);
        DB.SQL.execute(sql2);
        DB.SQL.execute(sql3);

        Map<String,Object> props = [
                table: this.table,
                file: "file2.sql"
        ];
        String sql = template.delete(props);
        DB.SQL.execute(sql);

        String selectSql = template.allFiles(table);
        List<GroovyRowResult> rows = DB.SQL.rows(selectSql);
        println("Rows: " + rows);
        assertEquals(2, rows.size());

        assertEquals(1, rows[0].id);
        assertEquals("file1.sql", rows[0].file);
        assertEquals(3, rows[1].id);
        assertEquals("file3.sql", rows[1].file);
    }

    public void createDb() {
        DB.SQL.execute("DROP TABLE IF EXISTS "+table);
        String sql = template.init(table);
        DB.SQL.execute(sql);
    }
}
