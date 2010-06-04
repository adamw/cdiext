package pl.softwaremill.cdiext.objectservice.extension;

import java.lang.reflect.Type;

/**
 * A specification of an object service: the object class, the service type and the service class (the two can differ
 * if the class is parametrized e.g. using generics).
 *
 * @author Adam Warski (adam at warski dot org)
 */
public class ObjectServiceSpecification {
    private final Class<?> objectClass;
    private final Type serviceType;
    private final Class<?> serviceClass;

    public ObjectServiceSpecification(Class<?> objectClass, Type serviceType, Class<?> serviceClass) {
        this.objectClass = objectClass;
        this.serviceType = serviceType;
        this.serviceClass = serviceClass;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }

    public Type getServiceType() {
        return serviceType;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    @Override
    public String toString() {
        return "ObjectServiceSpecification{" +
                "objectClass=" + objectClass +
                ", serviceType=" + serviceType +
                ", serviceClass=" + serviceClass +
                '}';
    }
}