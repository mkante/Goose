package kante.goose.util

import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotEquals
import static org.junit.Assert.assertTrue;
import static java.lang.System.out;

/**
 * Created by moh on 3/30/16.
 */
class FileResolverTest
{

    @Test
    public void newFileName() {

        String name1 = FileResolver.newDirName();
        out.println("Name1: "+name1);

        String name2 = FileResolver.newDirName("users");
        out.println("Name2: "+name2);

        String name3 = FileResolver.newDirName("  ");
        out.println("Name3: "+name3);

        assertNotEquals(name1, name2);

        assertTrue( name1.matches(/.*_DDL$/));
        assertTrue( name2.matches(/.*_users$/));
        assertTrue( name3.matches(/.*_DDL$/));
    }
}
