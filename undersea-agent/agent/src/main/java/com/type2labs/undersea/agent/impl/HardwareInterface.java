package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.process.ProcessBuilderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Interface for running the moos library that the agent operates on
 * <p>
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class HardwareInterface implements AgentService {

    private final Logger logger = LogManager.getLogger(HardwareInterface.class);

    private Agent agent;
    private Process process;
    private boolean started = false;

    public HardwareInterface() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public long transitionTimeout() {
        return 5000;
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    private void launchAgent(AgentMetaData metaData) {
        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("./" + metaData.getProperty(AgentMetaData.PropertyKey.LAUNCH_FILE_NAME));
        pb.directory((File) metaData.getProperty(AgentMetaData.PropertyKey.MISSION_DIRECTORY));

        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        AgentMetaData metaData = agent.metadata();

        // TODO: This needs to be changed to a MRS check
        if ((boolean) metaData.getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)) {
            logger.info(agent.name() + ": starting shoreside server", agent);
            launchAgent(metaData);
        } else {
            logger.info(agent.name() + ": starting agent server", agent);
            launchAgent(metaData);
        }

        started = true;

        logger.info(agent.name() + ": started agent server", agent);
    }

    @Override
    public void shutdown() {
        if (process != null) {
            logger.info(agent.name() + ": shutting down MOOS process", agent);
            process.destroy();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error(agent + ": attempted to cleanly kill interface but failed", agent);
                return;
            }

            if (process.isAlive()) {
                logger.error(agent + ": attempted to cleanly kill interface but failed. Forcibily destroying", agent);
                process.destroyForcibly();
            } else {
                logger.info(agent.name() + ": shut down MOOS process", agent);
            }
        }

        started = false;
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

}
