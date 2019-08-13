package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.model.CostCalculator;

public interface CostConfiguration {

    CostCalculator getCostCalculator();

    void setCostCalculator(CostCalculator costCalculator);

    void setBias(String key, Object value);

    Object getBias(String key);

}
