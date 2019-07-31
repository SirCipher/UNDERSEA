package com.type2labs.undersea.models.factory;

import com.type2labs.undersea.models.model.Agent;
import com.type2labs.undersea.models.model.Range;
import com.type2labs.undersea.models.model.Sensor;
import com.type2labs.undersea.utilities.AbstractFactory;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AgentFactory implements AbstractFactory<Agent> {

    @Override
    public Agent get(String name) {
        throw new NotImplementedException("Get agent not supported at present");
    }

    @Override
    public List<Agent> createN(int n) {
        List<Agent> agents = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            Agent agent = new Agent(Integer.toString(i));

            ThreadLocalRandom random = ThreadLocalRandom.current();

            int rate = random.nextInt(3);
            double reliability = random.nextDouble(1);

            agent.setSensors(Collections.singletonList(new Sensor("SENSOR" + i, rate, reliability, Sensor.SensorType.CONDUCTIVITY)));
            agent.setServerPort(String.valueOf(ThreadLocalRandom.current().nextInt()));

            int min = random.nextInt(5);
            int max = random.nextInt(min, 10);
            int value = random.nextInt(50);

            agent.setSpeedRange(new Range(min, max, value));

            agents.add(agent);
        }

        return agents;
    }

}
