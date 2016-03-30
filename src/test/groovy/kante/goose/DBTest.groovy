package kante.goose

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
@ActiveProfiles("mysql")
class DBTest
{

    @Test
    @Sql("data-1.sql")
    public void tableExists() {

        String t1 = "hello_1";
        Assert.assertTrue(!DB.tableExists(t1));

        DB.SQL.execute("CREATE TABLE "+t1+" (id INT) ");

        Assert.assertTrue(DB.tableExists(t1));
    }
}
