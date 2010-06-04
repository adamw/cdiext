package pl.softwaremill.cdiext.util.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.util.ObjectUtil;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class ObjectUtilTest {
    public class Data {
        private String data1;
        private String data2;

        public String getData1() {
            return data1;
        }

        public void setData1(String data1) {
            this.data1 = data1;
        }

        public String getData2() {
            return data2;
        }

        public void setData2(String data2) {
            this.data2 = data2;
        }
    }

    @Test
    public void testSet() {
        Data d = new Data();
        d.setData1("a");
        d.setData2("b");

        ObjectUtil.set(d, "data1", "c");

        Assert.assertEquals(d.getData1(), "c");
        Assert.assertEquals(d.getData2(), "b");
    }
}
