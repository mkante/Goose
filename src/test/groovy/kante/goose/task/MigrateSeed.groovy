package kante.goose.task

/**
 * Created by moh on 3/30/16.
 */
abstract class MigrateSeed
{

    public static void data1() {

        File f = new File("/tmp/goose_mgs");
        if (f.exists()) {
            f.delete();
        }
    }

    public static void data2() {

        File f = new File("/tmp/goose_mgs_2");
        if (f.exists()) {
            f.delete();
        }
    }


    public static void data3() {

        File root = new File("/tmp/goose_apply");
        if (root.exists()) {
            root.delete();
        }
        root.mkdirs();

        // File 1
        File f1 = new File(root, "2016_02_10_0001_DDL");
        f1.mkdirs();

        File up1 = new File(f1, "up.sql");
        up1.createNewFile();
        up1.text = """
        CREATE TABLE goose_1 (id INT);
        """;
        File down1 = new File(f1, "down.sql");
        down1.createNewFile();
        down1.text = """
        DROP TABLE goose_1;
        """;

        File f2 = new File(root, "2016_02_13_0001_DDL");
        f2.mkdirs();
        File up2 = new File(f2, "up.sql");
        up2.createNewFile();
        up2.text = """
        ALTER TABLE goose_1 ADD name VARCHAR(2500);
        ALTER TABLE goose_1 ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
        INSERT INTO goose_1 (id,name) VALUES(100,'chris_brown');
        """;
        File down2 = new File(f2, "down.sql");
        down2.createNewFile();
        down2.text = """
        ALTER TABLE goose_1 DROP COLUMN name;
        ALTER TABLE goose_1 DROP COLUMN created_at;
        """;

    }

    public static void data4() {

        File f = new File("/tmp/goose_dream_1");
        if (f.isDirectory()) {
            f.deleteDir();
        }
    }
}
