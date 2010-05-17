package pl.softwaremill.cdiext.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class CollectionUtil {
    public static <K,V> Map<K,V> singleKeyMap(K key, V value) {
        Map<K,V> ret = new HashMap<K,V>();
        ret.put(key, value);
        return ret;
    }
}
