package pl.softwaremill.cdiext.util;

import java.lang.reflect.Field;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ObjectUtil {
    public static boolean eq(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }

        return o2 != null && o1.equals(o2);

    }

    public static void set(Object on, String propertyName, Object value) {
        try {
            Field f = on.getClass().getDeclaredField(propertyName);
            f.setAccessible(true);
            f.set(on, value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void get(Object on, String propertyName) {
        try {
            Field f = on.getClass().getDeclaredField(propertyName);
            f.setAccessible(true);
            f.get(on);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
