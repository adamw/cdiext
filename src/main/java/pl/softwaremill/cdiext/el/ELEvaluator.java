package pl.softwaremill.cdiext.el;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public interface ELEvaluator {
    <T> T evaluate(String expression, Class<T> expectedResultType);

    void setParameter(String name, Object value);

    void clearParameter(String name);
}
