package kante.goose.template

import kante.goose.error.SqlTemplateError
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by moh on 3/30/16.
 */
class TemplateFactoryTest
{

    @Test
    public void testType() {

        String d1 = "com.mysql.jdbc.Driver";
        String d2 = "nothing";

        BaseTemplate t = null ;
        t = TemplateFactory.getTemplate(d1);
        assertTrue(t instanceof MysqlTemplate);

        try {
            TemplateFactory.getTemplate(d2);
            assertTrue(false);
        }
        catch (SqlTemplateError e){
            assertTrue(true);
        }
    }
}
