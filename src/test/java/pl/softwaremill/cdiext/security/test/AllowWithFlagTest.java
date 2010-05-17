package pl.softwaremill.cdiext.security.test;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.el.ELEvaluator;
import pl.softwaremill.cdiext.security.Secure;
import pl.softwaremill.cdiext.security.SecurityConditionException;
import pl.softwaremill.cdiext.security.SecurityFlags;
import pl.softwaremill.cdiext.test.util.ArquillianUtil;

import javax.inject.Inject;
import java.util.concurrent.Callable;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class AllowWithFlagTest extends Arquillian {
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive ar = Archives.create("test.jar", JavaArchive.class)
                .addPackage(AllowWithFlagTest.class.getPackage())
                .addPackage(Secure.class.getPackage())
                .addPackage(ELEvaluator.class.getPackage());

        ar = ArquillianUtil.addTestBeansXml(ar);
        ar = ArquillianUtil.addExtensionsFromApp(ar);

        return ar;
    }

    @Inject
    private AllowWithFlagsBean allowWithFlagsBean;

    @Inject
    private SecurityFlags securityFlags;

    @Test
    public void testAllowSingleFlagOnlyPass() {
        securityFlags.doWithFlag("flag1", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                allowWithFlagsBean.testSingleFlagOnly();
                return null;
            }
        });
    }

    @Test
    public void testAllowDoubleFlagOnlyPass() {
        securityFlags.doWithFlag("flag2", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                allowWithFlagsBean.testDoubleFlagOnly();
                return null;
            }
        });
    }

    @Test(expectedExceptions = SecurityConditionException.class)
    public void testNoFlagsFail() {
        allowWithFlagsBean.testSingleFlagOnly();
    }

    @Test
    public void testNoFlagsSecurePass() {
        allowWithFlagsBean.testFlagWithSecure(true);
    }
}
