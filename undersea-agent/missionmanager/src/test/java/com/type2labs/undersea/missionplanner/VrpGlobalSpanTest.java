package com.type2labs.undersea.missionplanner;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Created by Thomas Klapwijk on 2019-07-31.
 */
class VrpGlobalSpanTest {

    @Test
    void main() {
        try {
            VrpGlobalSpan.main(null);
        } catch (Exception ignored) {
            Assert.fail();
        }
    }

}