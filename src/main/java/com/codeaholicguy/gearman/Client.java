package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;
import org.apache.log4j.Logger;
import org.gearman.client.*;
import org.gearman.common.GearmanNIOJobServerConnection;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hoangnn
 */
public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class);

    private ArrayBlockingQueue<GearmanClient> queue;
    private Configuration configuration;

    private static Client instance;

    public static Client getInstance(Configuration configuration) {
        if (instance == null) {
            synchronized (Client.class) {
                if (instance == null) {
                    instance = new Client(configuration);
                }
            }
        }

        return instance;
    }


    private Client(Configuration configuration) {
        this.configuration = configuration;
        this.queue = new ArrayBlockingQueue(configuration.getQueueSize());
    }

    private GearmanClient borrowClient() {
        GearmanClient client = null;
        if (this.queue.size() > 0) {
            synchronized (GearmanClient.class) {
                try {
                    client = this.queue.take();
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        if (client == null) {
            GearmanNIOJobServerConnection connection1 = new GearmanNIOJobServerConnection(
                    configuration.getHost(),
                    configuration.getPort());

            client = new GearmanClientImpl();
            client.addJobServer(connection1);
        }

        return client;
    }

    private void returnClient(GearmanClient client) {
        try {
            if (client != null && this.queue.size() <= configuration.getQueueSize()) {
                this.queue.put(client);
            } else {
                this.destroyClient(client);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    private void destroyClient(GearmanClient client) {
        if (client != null) {
            client.shutdown();
        }
    }

    public boolean push(byte[] data) {
        return this.push(data, configuration.getFunction());
    }

    private boolean push(byte[] data, String function) {
        boolean result = false;
        GearmanClient client = this.borrowClient();

        try {
            GearmanJob gearmanJob = GearmanJobImpl.createBackgroundJob(function, data, UUID.randomUUID().toString());
            client.submit(gearmanJob);

            GearmanJobResult jobResult = gearmanJob.get();
            result = jobResult.jobSucceeded();

            this.returnClient(client);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            this.destroyClient(client);
        }

        return result;
    }
}
