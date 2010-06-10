package pl.softwaremill.cdiext.persistence;

import org.hibernate.FlushMode;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class EntityManagerProducer {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces @RequestScoped @Writeable
    public EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return new EntityManagerTxEnlistDecorator(entityManager);
    }

    @Produces @RequestScoped @ReadOnly
    public EntityManager getReadOnlyEntityManager() {
        EntityManager readOnlyEntityManager = entityManagerFactory.createEntityManager();
        EntityManagerUtil.makeEntityManagerReadOnly(readOnlyEntityManager);

        return new EntityManagerTxEnlistDecorator(readOnlyEntityManager);
    }

    public void disposeOfReadOnlyEntityManager(@Disposes @ReadOnly EntityManager readOnlyEntityManager) {
        readOnlyEntityManager.close();
    }

    public void disposeOfWriteableEntityManager(@Disposes @Writeable EntityManager writeableEntityManager) {
        writeableEntityManager.close();
    }
}
