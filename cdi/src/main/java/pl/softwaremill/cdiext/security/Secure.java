package pl.softwaremill.cdiext.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks a condition before executing a method (pre-condition).
 * TODO: add an internationalized message
 * @author Adam Warski (adam at warski dot org)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secure {
    /**
     * @return The EL expression that should be evaluated. If it evaluates to {@code true}, access will be granted.
     * The EL expression may reference any objects that are in any context, as well as any parameters named with
     * @{@link SecureVar}.
     */
    String  value();
}
