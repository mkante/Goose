package kante.goose.error

/**
 * Created by moh on 3/30/16.
 */
class ConfigError extends Error
{
    ConfigError(String msg) {
        super(msg);
    }

    ConfigError(String msg, Throwable e) {
        super(msg, e);
    }
}
