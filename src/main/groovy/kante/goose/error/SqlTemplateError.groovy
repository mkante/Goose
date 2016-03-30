package kante.goose.error

/**
 * Created by moh on 3/30/16.
 */
class SqlTemplateError extends Error
{
    SqlTemplateError(String msg) {
        super(msg);
    }

    SqlTemplateError(String msg, Throwable e) {
        super(msg, e);
    }
}
