package kante.goose

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.sql.SQLException

/**
 * Created by moh on 3/30/16.
 */
public class DB
{

    public static Sql SQL ;
    protected static  Logger log;

    static {
        log = LoggerFactory.getLogger(DB.class);
    }

    public static void init(ExtentionParameter params) {

        init([
                'url': params.db.url,
                'user': params.db.user,
                'password': params.db.password,
                'driver': params.db.driver
        ]);
    }

    public static void init(Map params) {

        log.debug("Initializationg DB params= "+params);
        SQL = Sql.newInstance(params);
        log.debug("DB ready");
    }

    public static boolean tableExists(String table) {

        boolean bool = false;
        try {
            GroovyRowResult row = DB.SQL.firstRow("SELECT * FROM "+table);
            bool = true ;
        }
        catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return bool;
    }

    public static boolean isTableEmpty(String table) {

        GroovyRowResult row = DB.SQL.firstRow("SELECT * FROM "+table);
        return (row == null)? true : false;
    }
}
