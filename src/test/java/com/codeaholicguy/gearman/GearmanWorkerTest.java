package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;

/**
 * @author hoangnn
 */
public class GearmanWorkerTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration(
                "gearman-test",
                "127.0.0.1",
                4730,
                8,
                "gearman-test-function",
                "com.codeaholicguy.gearman.WorkerTest");

        GearmanManager gearmanManager = new GearmanManager(configuration);
        gearmanManager.start();

    }
}