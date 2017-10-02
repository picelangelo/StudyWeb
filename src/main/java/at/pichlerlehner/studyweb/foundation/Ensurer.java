package at.pichlerlehner.studyweb.foundation;

import org.apache.commons.lang3.StringUtils;

public final class Ensurer {

    public static <T> T ensureNotNull(T object) {

        if (object == null)
            throw new IllegalArgumentException("Argument must not be null!");

        return object;
    }

    public static String ensureNotBlank(String object) {

        if (StringUtils.isBlank(object))
            throw new IllegalArgumentException("Argument must not be null, empty or blank!");

        return object;
    }

    public static int ensureNotNegative(int number) {
        if (number < 0)
            throw new IllegalArgumentException("Argument must not be smaller than null");

        return number;
    }

}
