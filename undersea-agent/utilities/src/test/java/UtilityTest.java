import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

public class UtilityTest {

    @Test
    public void propertyKeyTo2dDoubleArray() {
        Properties properties = Utility.getPropertiesByName("src/test/resources/test.properties");
        double[][] result = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        Assert.assertEquals(4, result.length);

        Assert.assertEquals(43.824855, result[0][0], 0.0);
        Assert.assertEquals(-70.330388, result[0][1], 0.0);

        Assert.assertEquals(43.824855, result[1][0], 0.0);
        Assert.assertEquals(-70.329775, result[1][1], 0.0);

        Assert.assertEquals(43.824411, result[2][0], 0.0);
        Assert.assertEquals(-70.329775, result[2][1], 0.0);

        Assert.assertEquals(43.824410, result[3][0], 0.0);
        Assert.assertEquals(-70.330382, result[3][1], 0.0);
    }
}