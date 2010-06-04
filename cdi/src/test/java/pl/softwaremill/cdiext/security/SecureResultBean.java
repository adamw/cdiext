package pl.softwaremill.cdiext.security;

import pl.softwaremill.cdiext.security.SecureResult;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class SecureResultBean {
    @SecureResult("#{result == 'a'}")
    public String method1(String p) { return p; }
}