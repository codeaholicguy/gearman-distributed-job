package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;
import org.apache.log4j.Logger;
import org.gearman.client.*;
import org.gearman.common.GearmanNIOJobServerConnection;

import java.util.concurrent.ExecutionException;

/**
 * @author hoangnn
 */
public class ClientTest {

    private static final Logger LOGGER = Logger.getLogger(WorkerTest.class);

    public static void main(String[] args) {
        String data = "";
        boolean result;

        Configuration configuration = new Configuration(
                "gearman-test",
                "127.0.0.1",
                4730,
                8,
                1000,
                50,
                "gearman-test-function",
                "com.codeaholicguy.gearman.WorkerTest");

        Client client = Client.getInstance(configuration);
        result = client.push(data.getBytes());

        LOGGER.info(String.format("Job return %s", result));
    }
}