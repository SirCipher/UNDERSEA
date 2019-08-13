package com.type2labs.undersea.common.visualiser;

public class VisualiserData {

    private Object data;
    private VisualiserDataType dataType;

    public VisualiserData(Object data, VisualiserDataType dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public VisualiserDataType getDataType() {
        return dataType;
    }

}
