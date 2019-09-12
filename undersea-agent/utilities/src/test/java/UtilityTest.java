/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.Test;

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