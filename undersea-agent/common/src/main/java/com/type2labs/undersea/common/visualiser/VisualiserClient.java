package com.type2labs.undersea.common.visualiser;

import java.io.IOException;

public interface VisualiserClient {

    void write(VisualiserData data);

    void openConnection() throws IOException;

    void closeConnection() throws IOException;

}
