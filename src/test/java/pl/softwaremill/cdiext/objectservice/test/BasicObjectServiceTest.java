package pl.softwaremill.cdiext.objectservice.test;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.objectservice.OS;
import pl.softwaremill.cdiext.objectservice.OSP;
import pl.softwaremill.cdiext.objectservice.extension.ObjectServiceExtension;
import pl.softwaremill.cdiext.test.util.ArquillianUtil;

import javax.inject.Inject;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class BasicObjectServiceTest extends Arquillian {
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive ar = Archives.create("test.jar", JavaArchive.class)
                .addPackage(A.class.getPackage())
                .addPackage(OS.class.getPackage())
                .addPackage(ObjectServiceExtension.class.getPackage());

        ar = ArquillianUtil.addEmptyBeansXml(ar);
        ar = ArquillianUtil.addExtensionsFromApp(ar);

        return ar;
    }

    @Inject
    private OSP<A, Service1<A>> service1;

    @Inject
    private OSP<A, Service2<A>> service2;

    @Test
    public void testService1WithB() {
        Assert.assertEquals(service1.f(new B(10)).get(), 10);
    }

    @Test
    public void testService1WithC() {
        Assert.assertEquals(service1.f(new C("x")).get(), "x");
    }

    @Test
    public void testService1WithAnonymousB() {
        Assert.assertEquals(service1.f(new B(11) {
            @Override
            public Integer getValue() {
                return 12;
            }
        }).get(), 12);
    }

    @Test
    public void testNewServiceInstances2WithB() {
        // On each invocation of f() there should be a new bean created.
        int instancesStart = Service2B.instanceCount();
        Assert.assertEquals(service2.f(new B(10)).get(), 10);
        Assert.assertEquals(service2.f(new B(12)).get(), 12);
        int instancesEnd = Service2B.instanceCount();
        Assert.assertEquals(instancesEnd - instancesStart, 2);
    }

    @Test
    public void testNewServiceInstances2WithC() {
        // On each invocation of f() there should be a new bean created.
        int instancesStart = Service2C.instanceCount();
        Assert.assertEquals(service2.f(new C("x")).get(), "x");
        Assert.assertEquals(service2.f(new C("y")).get(), "y");
        int instancesEnd = Service2C.instanceCount();
        Assert.assertEquals(instancesEnd - instancesStart, 2);
    }
}
