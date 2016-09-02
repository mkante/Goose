package kante.goose.template

import kante.goose.error.SqlTemplateError

/**
 * Created by moh on 3/30/16.
 */
abstract class TemplateFactory
{
    public static BaseTemplate getTemplate(String driver) {

        BaseTemplate templt = null;
        switch (driver) {
            case 'com.mysql.jdbc.Driver':
            case 'com.mysql.cj.jdbc.Driver':
                templt = new MysqlTemplate();
                break;
            default:
                throw new SqlTemplateError("No template for Driver="+driver);
        }

        return templt;
    }
}
