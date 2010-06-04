package pl.softwaremill.cdiext.objectservice.extension;

import org.jboss.weld.literal.DefaultLiteral;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;
import pl.softwaremill.cdiext.objectservice.OS;
import pl.softwaremill.cdiext.objectservice.OSP;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ObjectServiceProviderBean implements Bean {
    private final ObjectServiceExtension extension;
    private final ObjectServiceSpecification specification;

    public ObjectServiceProviderBean(ObjectServiceExtension extension, ObjectServiceSpecification specification) {
        this.extension = extension;
        this.specification = specification;
    }

    @Override
    public Set<Type> getTypes() {
        return Collections.<Type>singleton(new ParameterizedTypeImpl(
                OSP.class,
                new Type[] { specification.getObjectClass(), specification.getServiceType() },
                null));
    }

    @Override
    public Object create(CreationalContext creationalContext) {
        return new OSP() {
            @Override
            public OS f(Object obj) {
                OS objectService = (OS) extension.serviceForObject(obj.getClass(), specification.getServiceClass());
                //noinspection unchecked
                objectService.setServiced(obj);
                return objectService;
            }
        };
    }

    @Override
    public void destroy(Object instance, CreationalContext creationalContext) { }

    @Override
    public Set<Annotation> getQualifiers() {
        return Collections.<Annotation>singleton(DefaultLiteral.INSTANCE);
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return Dependent.class;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.emptySet();
    }

    @Override
    public Class<?> getBeanClass() {
        return OSP.class;
    }

    @Override
    public boolean isAlternative() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }
}
