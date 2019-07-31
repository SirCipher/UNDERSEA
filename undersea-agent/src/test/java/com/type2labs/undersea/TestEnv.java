package com.type2labs.undersea;

import org.junit.Test;

public class TestEnv {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.library.path"));
    }

}
