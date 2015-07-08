package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;
import org.apache.log4j.Logger;
import org.gearman.client.*;
import org.gearman.common.GearmanNIOJobServerConnection;

import java.util.concurrent.ExecutionException;

/**
 * @author hoangnn
 */
public class PushGearmanWorkerTest {

    private static final Logger LOGGER = Logger.getLogger(WorkerTest.class);

    public static void main(String[] args) {
        String data  = "";

        Configuration configuration = new Configuration(
                "gearman-test",
                "127.0.0.1",
                4730,
                8,
                "gearman-test-function",
                "com.codeaholicguy.gearman.WorkerTest");

        GearmanClient gearmanClient = new GearmanClientImpl();
        GearmanNIOJobServerConnection connection = new GearmanNIOJobServerConnection(
                configuration.getHost(),
                configuration.getPort());

        gearmanClient.addJobServer(connection);

        GearmanJob gearmanJob = GearmanJobImpl.createBackgroundJob(configuration.getFunction(), data.getBytes(), "");
        gearmanClient.submit(gearmanJob);

        GearmanJobResult result = null;
        try {
            result = gearmanJob.get();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info(String.format("Job return %s", result.jobSucceeded()));
    }
}