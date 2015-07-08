package com.codeaholicguy.gearman;

import org.apache.log4j.Logger;
import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;

/**
 * @author hoangnn
 */
public class WorkerTest extends AbstractGearmanFunction {

    private static final Logger LOGGER = Logger.getLogger(WorkerTest.class);

    @Override
    public GearmanJobResult executeFunction() {
        LOGGER.info("WorkerTest executeFunction()");
        String message = "Success";
        return new GearmanJobResultImpl(
                this.jobHandle,
                false,
                new byte[0],
                new byte[0],
                ByteUtils.toUTF8Bytes(message),
                0,
                0);
    }

}
