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
}
