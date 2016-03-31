package kante.goose.util

import kante.goose.DB

/**
 * Created by moh on 3/30/16.
 */
class MigratorSeed
{

    public static void data1() {

        File m1 = new File("/tmp/owesome_mg1");
        if (m1.isDirectory()) {
            m1.delete();
        }
        m1.mkdirs();

        File m2 = new File("/tmp/owesome_mg2");
        if (m2.isDirectory()) {
            m2.delete();
        }
        m2.mkdirs();
        File up = new File(m2, "up.sql");
        up.createNewFile();
        up.text = """
        DROP TABLE IF EXISTS sushi_1 ;

        CREATE TABLE sushi_1 (
            id INT ,
            name VARCHAR(200) NOT NULL
        );
        """;

        File down = new File(m2, "down.sql");
        down.createNewFile();
        down.text = """
        DROP TABLE sushi_1;
        DROP TABLE IF EXISTS sushi_2;

        CREATE TABLE sushi_2 (
            id INT ,
            name VARCHAR(200) NOT NULL
        );
        """;
    }

}
