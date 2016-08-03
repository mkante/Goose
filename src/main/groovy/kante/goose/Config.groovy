package kante.goose

/**
 * Created by moh on 3/30/16.
 */
public class Config
{

    public static class DBParams
    {
        String url;
        String driver;
        String user;
        String password;
    }

    String dir = 'src/main/resources/goose/migrations';
    String table ='goose_migrations' ;
    DBParams db ;

    public Config() {
        db = new DBParams();
    }

    public void setDBUrl(String value) {
        db.url = value;
    }
    public void setDBUser(String value) {
        db.user = value;
    }
    public void setDBPassword(String value) {
        db.password = value;
    }
    public void setDBDriver(String value) {
        db.driver = value;
    }

    @Override
    public String toString() {

        Map<String,Object> obj = [
                'db': [
                        'url': this.db.url,
                        'driver': this.db.driver,
                        'user': this.db.user,
                        'password': this.db.password
                ],
                'dir': this.dir,
                'table': this.table,
        ];

        return obj.toString() ;
    }
}
