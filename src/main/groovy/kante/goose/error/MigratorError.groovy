package kante.goose.error

/**
 * Created by moh on 3/30/16.
 */
class MigratorError extends Error
{
    MigratorError(String msg) {
        super(msg);
    }

    MigratorError(String msg, Throwable e) {
        super(msg, e);
    }
}
