package pl.softwaremill.cdiext.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows access when any of the given security flags are set.
 * Has higher priority than restrictions specified with @{@link Secure}, that is when access is granted basing
 * on security flags, other restrictions are not checked.
 * @author Adam Warski (adam at warski dot org)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface AllowWithFlag {
    String[]    value();
}
