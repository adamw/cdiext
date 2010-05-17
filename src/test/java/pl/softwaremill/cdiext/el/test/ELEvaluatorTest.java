package pl.softwaremill.cdiext.el.test;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.el.ELEvaluator;
import pl.softwaremill.cdiext.test.util.ArquillianUtil;

import javax.inject.Inject;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ELEvaluatorTest extends Arquillian {
    @Inject
    private ELEvaluator elEvaluator;

    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive ar = Archives.create("test.jar", JavaArchive.class)
                .addClasses(StringHoldingBean.class)
                .addPackage(ELEvaluator.class.getPackage());

        ar = ArquillianUtil.addEmptyBeansXml(ar);

        return ar;
    }

    @Test
    public void testEvaluate() {
        Assert.assertEquals(elEvaluator.evaluate("#{stringHoldingBean.value}", String.class), "test value");
    }

    @Test
    public void testParameter() {
        try {
            elEvaluator.setParameter("testParam1", 123);
            elEvaluator.setParameter("testParam2", 2);
            Assert.assertEquals(elEvaluator.evaluate("#{testParam1 + 10 + testParam2}", Integer.class), (Integer) 135);
        } finally {
            elEvaluator.clearParameter("testParam1");
            elEvaluator.clearParameter("testParam2");
        }
    }
}
