package pl.softwaremill.cdiext.security.test;

import pl.softwaremill.cdiext.security.Secure;
import pl.softwaremill.cdiext.security.SecureVar;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class SecureBeanSecureVarExp {
    @Secure("#{var1 == 'a'}")
    public void method1(@SecureVar(value = "var1", exp = "#{p.content}") StringHolder sh) { }
}