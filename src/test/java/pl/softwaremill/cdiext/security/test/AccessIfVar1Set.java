package pl.softwaremill.cdiext.security.test;

import pl.softwaremill.cdiext.security.Secure;
import pl.softwaremill.cdiext.security.SecureBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@SecureBinding
@Secure("#{var1 == true}")
public @interface AccessIfVar1Set {
}