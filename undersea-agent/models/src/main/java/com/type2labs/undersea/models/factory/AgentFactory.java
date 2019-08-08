package com.type2labs.undersea.models.factory;

import com.type2labs.undersea.models.impl.AgentImpl;
import com.type2labs.undersea.models.impl.Range;
import com.type2labs.undersea.models.impl.Sensor;
import com.type2labs.undersea.utilities.AbstractFactory;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AgentFactory implements AbstractFactory<AgentImpl> {

    @Override
    public AgentImpl get(String name) {
        throw new NotImplementedException("Get agent not supported at present");
    }

    @Override
    public List<AgentImpl> createN(int n) {
        List<AgentImpl> dslAgents = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            AgentImpl dslAgent = new AgentImpl(Integer.toString(i));

            ThreadLocalRandom random = ThreadLocalRandom.current();

            int rate = random.nextInt(3);
            double reliability = random.nextDouble(1);

            dslAgent.setSensors(Collections.singletonList(new Sensor("SENSOR" + i, rate, reliability,
                    Sensor.SensorType.CONDUCTIVITY)));
            dslAgent.setServerPort(String.valueOf(ThreadLocalRandom.current().nextInt()));

            int min = random.nextInt(5);
            int max = random.nextInt(min, 10);
            int value = random.nextInt(50);

            dslAgent.setSpeedRange(new Range(min, max, value));

            dslAgents.add(dslAgent);
        }

        return dslAgents;
    }

}
