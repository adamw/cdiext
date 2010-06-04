package pl.softwaremill.cdiext.util;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class BeanInject {
    @SuppressWarnings({"unchecked"})
    public static <T> T lookup(Class<T> beanClass) {
        try {
            BeanManager manager = (BeanManager) new InitialContext().lookup("java:app/BeanManager");

            Set<?> beans = manager.getBeans(beanClass);
            if (beans.size() != 1) {
                if (beans.size() == 0) {
                    throw new RuntimeException("No beans of class " + beanClass + " found.");
                } else {
                    throw new RuntimeException("Multiple beans of class " + beanClass + " found: " + beans + ".");
                }
            }

            Bean<T> myBean = (Bean<T>) beans.iterator().next();

            return (T) manager.getReference(myBean, beanClass, manager.createCreationalContext(myBean));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
