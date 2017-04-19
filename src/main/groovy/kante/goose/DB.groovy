package kante.goose

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import kante.goose.error.GoosePluginError
import kante.goose.util.Assert
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

    public static void init(Config params) {

        init([
                'url': params.db.url,
                'user': params.db.user,
                'password': params.db.password,
                'driver': params.db.driver
        ]);
    }

    public static void init(Map params) {

        String ms1 = "Goose 'db.url' is required" ;
        String ms2 = "Goose 'db.user' is required" ;
        String ms3 = "Goose 'db.password' is required" ;
        String ms4 = "Goose 'db.driver' is required" ;

        Assert.notNull(params.url, new GoosePluginError(ms1))
        //Assert.notNull(params.user, new GoosePluginError(ms2))
        //Assert.notNull(params.password, new GoosePluginError(ms3))
        Assert.notNull(params.driver, new GoosePluginError(ms4))

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
