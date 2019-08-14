package com.type2labs.undersea.common.visualiser;

import java.io.IOException;

public interface VisualiserClient {

    void write(Object data) throws IOException;

    void closeConnection() throws IOException;

}
