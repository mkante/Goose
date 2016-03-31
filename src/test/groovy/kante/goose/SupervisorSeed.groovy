package kante.goose

/**
 * Created by moh on 3/30/16.
 */
class SupervisorSeed
{

    public static  void data1() {

        File root = new File("/tmp/goose_mgs");
        if (root.isDirectory()) {
            root.deleteDir();
        }
        root.mkdirs();

        File m1 = new File(root, "2016_09_01_00001_DDL");
        m1.mkdir();
        File m2 = new File(root, "2016_10_01_00002_DDL");
        m2.mkdir();
        File m3 = new File(root, "2016_11_01_00003_DDL");
        m3.mkdir();
        File m4 = new File(root, "2016_11_15_00004_DDL");
        m4.mkdir();
        File m5 = new File(root, "2016_11_15_00004_DDL2");
        m5.mkdir();
        File m6 = new File(root, "2016_11_16_00004_DDL");
        m6.mkdir();
    }

    public static  void data2() {

        File root = new File("/tmp/goose_flu");
        if (root.isDirectory()) {
            root.deleteDir();
        }
        root.mkdirs();

        File m1 = new File(root, "2016_09_01_00001_DDL");
        m1.mkdir();
        File m2 = new File(root, "2016_10_01_00002_DDL");
        m2.mkdir();
        File m3 = new File(root, "2016_11_01_00003_DDL");
        m3.mkdir();
        File m4 = new File(root, "2016_11_15_00004_DDL");
        m4.mkdir();
        File m5 = new File(root, "2016_11_15_00014_DDL2");
        m5.mkdir();
        File m6 = new File(root, "2016_11_16_00004_DDL");
        m6.mkdir();
    }
}
