package kante.goose.template

import kante.goose.error.SqlTemplateError

/**
 * Created by moh on 3/30/16.
 */
abstract class TemplateFactory
{
    public static BaseTemplate getTemplate(String driver) {

        BaseTemplate templt = null;

        if ( driver ==~ /.*mysql.*/) {
            templt = new MysqlTemplate();
        }
        else if ( driver ==~ /.*sqlite.*/) {
            templt = new SqliteTemplate();
        }
        else if ( driver ==~ /.*postgresql.*/) {
            templt = new PostgresTemplate();
        }
        else {
            throw new SqlTemplateError("No template for Driver="+driver);
        }

        return templt;
    }
}
