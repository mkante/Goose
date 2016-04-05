package kante.goose.error

/**
 * Created by moh on 3/30/16.
 */
class GoosePluginError extends Error
{
    GoosePluginError(String msg) {
        super(msg);
    }

    GoosePluginError(String msg, Throwable e) {
        super(msg, e);
    }
}
