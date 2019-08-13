package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.model.CostCalculator;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ResultOfMethodCallIgnored")
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
