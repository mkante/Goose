package kante.goose

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static java.lang.System.out;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by moh on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:goose-mysql.xml")
@ActiveProfiles("mysql")
class SupervisorTest
{

    @Autowired
    Config extParams

    @Test
    public void localFiles() {

        SupervisorSeed.data1();
        extParams.dir = "/tmp/goose_mgs";

        File rootDir = new File(extParams.dir);
        File f1 = new File(rootDir, "2016_09_01_00001_DDL");
        File f2 = new File(rootDir, "2016_10_01_00002_DDL");
        File f3 = new File(rootDir, "2016_11_01_00003_DDL");
        File f4 = new File(rootDir, "2016_11_15_00004_DDL");
        File f5 = new File(rootDir, "2016_11_15_00004_DDL2");
        File f6 = new File(rootDir, "2016_11_16_00004_DDL");

        Supervisor sp = new Supervisor(extParams);

        List<File> files = sp.localFiles();
        out.println("Files= "+files);
        assertEquals(files[0], f1);
        assertEquals(files[1], f2);
        assertEquals(files[2], f3);
        assertEquals(files[3], f4);
        assertEquals(files[4], f5);
        assertEquals(files[5], f6);

        files = sp.localFiles(true);
        assertEquals(files[5], f1);
        assertEquals(files[4], f2);
        assertEquals(files[3], f3);
        assertEquals(files[2], f4);
        assertEquals(files[1], f5);
        assertEquals(files[0], f6);
    }

    @Test
    @Sql("supervisor-1.sql")
    public void cacheFiles() {

        extParams.dir = "/tmp/goose_official";

        Supervisor sp = new Supervisor(extParams);
        File rootDir = new File(extParams.dir);
        File f1 = new File(rootDir, "2016_09_01_00001_DDL");
        File f2 = new File(rootDir, "2016_10_01_00002_DDL");
        File f3 = new File(rootDir, "2016_11_01_00003_DDL");

        List<File> files = sp.cacheFiles();
        out.println("Files= "+files);
        assertEquals(files[0], f1);
        assertEquals(files[1], f2);
        assertEquals(files[2], f3);

        files = sp.cacheFiles(true);
        assertEquals(files[2], f1);
        assertEquals(files[1], f2);
        assertEquals(files[0], f3);
    }


    @Test
    public void isGreater() {

        String n1 = "2016_09_01_00001_DDL";
        String n2 = "2016_10_01_00002_DDL";
        String n3 = "2016_11_01_00003_DDL";

        Supervisor sp = new Supervisor(extParams);
        assertTrue(!sp.isGreater(n1, n2));
        assertTrue(!sp.isGreater(n1, n3));

        assertTrue(sp.isGreater(n2, n1));
        assertTrue(!sp.isGreater(n2, n3));

        assertTrue(sp.isGreater(n3, n1));
        assertTrue(sp.isGreater(n3, n2));

        assertTrue(!sp.isGreater(null, n2));
        assertTrue(sp.isGreater(n3, null));

        assertTrue(!sp.isGreater("random", n2));
        assertTrue(sp.isGreater(n2, "random"));
    }

    @Test
    @Sql("supervisor-2.sql")
    public void newFiles() {

        SupervisorSeed.data2();

        extParams.dir = "/tmp/goose_flu";

        Supervisor sp = new Supervisor(extParams);
        File rootDir = new File(extParams.dir);
        File f1 = new File(rootDir, "2016_09_01_00001_DDL");
        File f2 = new File(rootDir, "2016_10_01_00002_DDL");
        File f3 = new File(rootDir, "2016_11_01_00003_DDL");
        File f4 = new File(rootDir, "2016_11_15_00004_DDL");
        File f5 = new File(rootDir, "2016_11_15_00014_DDL2");
        File f6 = new File(rootDir, "2016_11_16_00004_DDL");

        List<File> files = sp.newFiles();
        println("New files: "+files)
        assertTrue(files.size() == 3)

        assertEquals(files[0].path, f4.path);
        assertEquals(files[1].path, f5.path);
        assertEquals(files[2].path, f6.path);
    }


}
