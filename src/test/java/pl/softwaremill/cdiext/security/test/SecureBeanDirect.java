package pl.softwaremill.cdiext.security.test;

import pl.softwaremill.cdiext.security.Secure;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class SecureBeanDirect {
    @Secure("#{true}")
    public void method1() { }

    @Secure("#{false}")
    public void method2() { }
}
