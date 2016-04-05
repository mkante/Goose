package kante.goose.util

/**
 * Created by moh on 4/5/16.
 */
abstract class Assert {

    public static void notNull(Object val, Throwable exception) {

        if (val == null) {
            throw exception;
        }
    }
}
