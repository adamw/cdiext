package pl.softwaremill.cdiext.el;

import javax.inject.Named;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Named
public class StringHoldingBean {
    public String getValue() {
        return "test value";
    }
}
