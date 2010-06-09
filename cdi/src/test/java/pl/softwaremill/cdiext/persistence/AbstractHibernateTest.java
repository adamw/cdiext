package pl.softwaremill.cdiext.persistence;

import org.hibernate.ejb.Ejb3Configuration;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.util.ArquillianUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public abstract class AbstractHibernateTest extends Arquillian {
    private EntityManagerFactory emf;
    private Ejb3Configuration cfg;

    @BeforeClass
    public void init() throws IOException {
        cfg = new Ejb3Configuration();
        cfg.configure("hibernate.test.cfg.xml");
        configure(cfg);
        emf = cfg.buildEntityManagerFactory();
    }

    protected abstract void configure(Ejb3Configuration cfg);

    @AfterClass
    public void close() {
        emf.close();
    }

    public EntityManager newEntityManager() {
        return emf.createEntityManager();
    }

    public EntityManager newReadOnlyEntityManager() {
        EntityManager em = newEntityManager();
        EntityManagerUtil.makeEntityManagerReadOnly(em);

        return em;
    }
}