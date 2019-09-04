package com.type2labs.undersea.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;
import com.type2labs.undersea.common.service.transaction.TransactionData;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.junit.Test;

public class WaypointExecutorTest {

    @Test
    public void lolz() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();


        TransactionData transactionData = TransactionData.from(new LogEntry(new TaskImpl(), 0, null).getMessage());


        String s = objectMapper.writeValueAsString(TransactionData.from(new TaskImpl()));
        System.out.println(s);
    }

    @Test
    @IgnoredOnCi
    public void doTest() throws InterruptedException {
//        Agent agent = (new AgentFactory().createWithName("alpha"));
//        agent.services().registerService(new MoosConnector());
//        agent.services().startServices();
//
//        Task task = new TaskImpl("110.87291551628573, -18.830200577525677", TaskType.WAYPOINT);
//
//        WaypointExecutor waypointExecutor = new WaypointExecutor(task);
//        waypointExecutor.initialise(agent);
//        waypointExecutor.run();
//
//
//        Thread.sleep(1000);
//        System.out.println("");
    }

}