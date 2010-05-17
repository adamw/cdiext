package pl.softwaremill.cdiext.security.test;

import pl.softwaremill.cdiext.security.Secure;
import pl.softwaremill.cdiext.security.SecureVar;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Secure("#{var3 == true}")
public class SecureBeanCompoundContextual {
    @AccessIfVar1Set
    @Secure("#{var2 == true}")
    public void method1(@SecureVar("var1") boolean var1, @SecureVar("var2") boolean var2, @SecureVar("var3") boolean var3) { }
}