package pl.softwaremill.cdiext.util;

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
}
