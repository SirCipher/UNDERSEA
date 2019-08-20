package com.type2labs.undersea.common.cost;

import java.util.HashMap;
import java.util.Map;

public class CostConfigurationImpl implements CostConfiguration {

    private CostCalculator costCalculator;
    private Map<String, Object> biases = new HashMap<>();

    @Override
    public CostCalculator getCostCalculator() {
        return costCalculator;
    }

    public void setCostCalculator(CostCalculator costCalculator) {
        this.costCalculator = costCalculator;
    }

    @Override
    public void setBias(String key, Object value) {
        biases.put(key, value);
    }

    @Override
    public Object getBias(String key) {
        return this.biases.get(key);
    }

}
