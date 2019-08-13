package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.visualiser.VisualiserClient;
import com.type2labs.undersea.common.visualiser.VisualiserClientImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class VisualiserTest {

    @BeforeClass
    public static void initVisualiser() {
        new Visualiser();
    }

    @Test
    public void testConnection() throws IOException, InterruptedException {
        VisualiserClient client = new VisualiserClientImpl();
        client.openConnection();

        Thread.sleep(5000);

        client.closeConnection();
    }

}