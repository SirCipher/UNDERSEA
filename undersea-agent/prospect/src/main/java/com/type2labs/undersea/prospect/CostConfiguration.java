package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.model.CostCalculator;

public interface CostConfiguration {

    void setCostCalculator(CostCalculator costCalculator);

    CostCalculator getCostCalculator();

    void setBias(String key, Object value);

    Object getBias(String key);

}
