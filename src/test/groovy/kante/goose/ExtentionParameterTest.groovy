package kante.goose

import org.junit.Test

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
 * Created by moh on 3/30/16.
 */
class ExtentionParameterTest
{

    @Test
    public void testOne() {

        String dir = "src/main/resources/goose/migrations";
        ExtentionParameter prm = new ExtentionParameter();
        assertNull(prm.db.driver);
        assertNull(prm.db.url);
        assertNull(prm.db.user);
        assertNull(prm.db.password);

        assertEquals(prm.table, "goose_migrations");
        assertEquals(prm.dir, dir);

        String driver = 'com.mysql.jdbc.Driver';
        String url = 'jdbc:mysql://localhost/db1';
        String user = 'test';
        String password = 'test';

        prm.db.driver = driver;
        prm.db.url = url;
        prm.db.user = user;
        prm.db.password = password;
        out.println(prm);

        assertEquals(prm.db.driver, driver);
        assertEquals(prm.db.url, url);
        assertEquals(prm.db.user, user);
        assertEquals(prm.db.password, password);
        assertEquals(prm.table, "goose_migrations");
        assertEquals(prm.dir, dir);
    }
}
