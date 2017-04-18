package kante.goose.template

import groovy.sql.GroovyRowResult
import kante.goose.DB
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
class MysqlTemplateTest
{
    Logger log = LoggerFactory.getLogger(MysqlTemplateTest.class);
    MysqlTemplate template;
    String table = "mysql_migrations";

    @Before
    public void before() {
        template = new MysqlTemplate();
    }

    @Test
    @Sql("MysqlTemplateTest-1.sql")
    public void testInitializaton() {

        String sql = template.init(table);
        DB.SQL.execute(sql);
        log.debug("DESC table: " + table);
        assertTrue(DB.tableExists(table))
    }

    @Test
    @Sql("MysqlTemplateTest-2.sql")
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
    @Sql("MysqlTemplateTest-3.sql")
    public void testDeletion() {

        Map<String,Object> props = [
                table: this.table,
                file: "file2.sql"
        ];
        String sql = template.delete(props);
        DB.SQL.execute(sql);

        String selectSql = template.allFiles(table);
        List<GroovyRowResult> rows = DB.SQL.rows(selectSql);
        assertTrue(rows.size() == 2);

        assertEquals(1, rows[0].id);
        assertEquals("file1.sql", rows[0].file);
        assertEquals(3, rows[1].id);
        assertEquals("file3.sql", rows[1].file);
    }
}
