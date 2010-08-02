package pl.softwaremill.cdiext.persistence;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class EntityManagerFactoryProducer {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @RequestScoped
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
