package pl.softwaremill.cdiext.objectservice;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class Service1B implements Service1<B> {
    private B serviced;

    @Override
    public void setServiced(B serviced) {
        this.serviced = serviced;
    }

    @Override
    public Object get() {
        return serviced.getValue();
    }
}
