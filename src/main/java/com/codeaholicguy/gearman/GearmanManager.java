package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;
import com.codeaholicguy.gearman.runnable.Worker;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hoangnn
 */
public class GearmanManager {

    private static final Logger LOGGER = Logger.getLogger(GearmanManager.class);

    private Configuration configuration;
    private List<Worker> workers;

    public GearmanManager(Configuration configuration) {
        this.configuration = configuration;
        this.workers = new ArrayList<Worker>();
    }

    public void start() {
        try {

            LOGGER.info(String.format("Starting service %s with %s worker(s)",
                    this.configuration.getInstance(),
                    this.configuration.getSize()));

            Worker worker;
            Thread thread;
            for (int i = 0; i < this.configuration.getSize(); i++) {
                LOGGER.info(String.format("Starting worker %s", i));
                worker = new Worker(configuration);

                this.workers.add(worker);

                thread = new Thread(worker);
                thread.start();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public boolean stop() {
        Iterator<Worker> isRunningWorkers = this.workers.iterator();

        Worker worker;
        while (isRunningWorkers.hasNext()) {
            worker = isRunningWorkers.next();
            worker.stop();
        }

        boolean isRunning = true;

        while (isRunning) {
            isRunning = false;
            isRunningWorkers = this.workers.iterator();

            while (isRunningWorkers.hasNext()) {
                worker = isRunningWorkers.next();
                if (worker.isRunning()) {
                    isRunning = true;
                    break;
                }
            }

            try {
                Thread.sleep(configuration.getIdleTime());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        LOGGER.info("Worker is stopped.");
        return true;
    }

    public boolean status() {
        Iterator<Worker> isRunningWorkers = this.workers.iterator();

        Worker worker;
        do {
            if (!isRunningWorkers.hasNext()) {
                return false;
            }

            worker = isRunningWorkers.next();
        } while (!worker.isRunning());

        return true;
    }
}
