package com.type2labs.undersea.common.cost;

public interface CostConfiguration {

    CostCalculator getCostCalculator();

    void setCostCalculator(CostCalculator costCalculator);

    void setBias(String key, Object value);

    Object getBias(String key);

}
