package com.type2labs.undersea;

import org.junit.Test;

public class TestEnv {

    @Test
    public void test() {
        System.out.println("Library path: " + System.getProperty("java.library.path"));
        System.out.println("PATH: " + System.getenv("PATH"));
    }

}
