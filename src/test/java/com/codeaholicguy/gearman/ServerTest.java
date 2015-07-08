package com.codeaholicguy.gearman;

import com.codeaholicguy.gearman.config.Configuration;

/**
 * @author hoangnn
 */
public class ServerTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration(
                "gearman-test",
                "127.0.0.1",
                4730,
                8,
                1000,
                50,
                "gearman-test-function",
                "com.codeaholicguy.gearman.WorkerTest");

        Server server = Server.getInstance(configuration);
        server.start();

    }
}