package pl.softwaremill.cdiext.persistence;

/**
 *
 * @author Adam Warski (adam at warski dot org)
 */
public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
