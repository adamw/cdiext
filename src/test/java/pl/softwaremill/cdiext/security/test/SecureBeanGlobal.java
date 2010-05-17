package pl.softwaremill.cdiext.security.test;

import pl.softwaremill.cdiext.security.Secure;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Secure("#{false}")
public class SecureBeanGlobal {
    public void method1() { }
}