package pl.softwaremill.cdiext.objectservice.test;

import pl.softwaremill.cdiext.objectservice.OS;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public interface Service2<T extends A> extends OS<T> {
    Object get();
}
