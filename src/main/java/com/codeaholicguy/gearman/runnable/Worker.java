package com.codeaholicguy.gearman.runnable;

import com.codeaholicguy.gearman.config.Configuration;
import org.apache.log4j.Logger;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.worker.DefaultGearmanFunctionFactory;
import org.gearman.worker.GearmanWorker;
import org.gearman.worker.GearmanWorkerImpl;

import java.util.Iterator;
import java.util.Map;

/**
 * @author hoangnn
 */
public class Worker implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Worker.class);

    private GearmanNIOJobServerConnection connection;
    private Configuration configuration;
    private GearmanWorker worker;

    public Worker(Configuration configuration) {
        this.connection = new GearmanNIOJobServerConnection(configuration.getHost(), configuration.getPort());
        this.configuration = configuration;
        this.worker = new GearmanWorkerImpl();
    }

    public void stop() {
        this.worker.stop();
    }

    public boolean isRunning() {
        return this.worker.isRunning();
    }

    public void run() {
        try {
            DefaultGearmanFunctionFactory factory =
                    new DefaultGearmanFunctionFactory(
                            configuration.getFunction(),
                            configuration.getClassName());

            this.worker.addServer(this.connection);
            this.worker.registerFunctionFactory(factory);
            this.worker.work();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
