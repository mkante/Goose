package kante.goose.util

/**
 * Created by moh on 3/30/16.
 */
class FileResolver
{

    public static String newDirName(String suffix) {

        suffix = (suffix == null)? "DDL" : suffix.trim();
        suffix = (suffix.equals(''))? "DDL": suffix;

        Date date = new Date();
        String name = date.format("yyyy_MM_dd");
        name += "_"+date.getTime()+"_" ;
        name += suffix;

        return name;
    }
}
