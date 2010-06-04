package pl.softwaremill.cdiext.security;

import javax.enterprise.inject.Model;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * A bean for managing security flags.
 * @author Adam Warski (adam at warski dot org)
 */
@Model
public class SecurityFlags {
    private Map<String, Boolean> flags = new HashMap<String, Boolean>();

    public boolean hasFlag(String name) {
        return flags.containsKey(name) && flags.get(name);
    }

    public <T> T doWithFlag(String flagName, Callable<T> callable) {
        Boolean oldValue = flags.put(flagName, true);
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            flags.put(flagName, oldValue);
        }
    }
}
