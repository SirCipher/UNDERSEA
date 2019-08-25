package com.type2labs.undersea.agent.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.ServiceNotRegisteredException;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.process.ProcessBuilderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for running the moos library that the agent operates on
 * <p>
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class HardwareInterface implements AgentService {

    private static final Logger logger = LogManager.getLogger(HardwareInterface.class);

    private Agent agent;
    private MoosConnector moosConnector;
    private Process process;

    public HardwareInterface() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        this.moosConnector = agent.services().getService(MoosConnector.class);

        if (moosConnector == null) {
            throw new ServiceNotRegisteredException(MoosConnector.class, HardwareInterface.class);
        }
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {
        AgentMetaData metaData = agent.metadata();

        if (metaData.isMaster()) {
            logger.info(agent.name() + ": starting shoreside server", agent);
            runShoreside();
        } else {
            logger.info(agent.name() + ": starting agent server", agent);
            runAgent();
        }
    }

    private void runAgent() {
        AgentMetaData metaData = agent.metadata();
        List<String> procArgs = new ArrayList<>();

        procArgs.add("pAntler");
        procArgs.add(metaData.getMetadataFileName());

        ProcessBuilder proc = ProcessBuilderUtil.getSanitisedBuilder();
        proc.command(procArgs);

        try {
            proc.directory(metaData.getMissionDirectory().getCanonicalFile());
            process = proc.start();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            BufferedWriter writer = Files.newBufferedWriter(new File(agent.name() + ".txt").toPath(),
                    StandardCharsets.UTF_8);

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                writer.write(s + "\n");
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new UnderseaException("Failed to start agent: " + parent().name(), e);
        }
    }

    private void runShoreside() {
        AgentMetaData metaData = agent.metadata();

        ProcessBuilder pb = ProcessBuilderUtil.getSanitisedBuilder();
        pb.command("pAntler", metaData.getMetadataFileName());
        pb.directory(metaData.getMissionDirectory());

        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                logger.error(agent + ": attempted to cleanly kill interface but failed. Forcibily destorying", agent);
                process.destroyForcibly();
            } else {
                logger.info(agent.name() + ": shut down MOOS process", agent);
            }
        }
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

}