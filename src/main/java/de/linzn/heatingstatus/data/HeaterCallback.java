/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.heatingstatus.data;


import de.azcore.azcoreRuntime.AppLogger;
import de.azcore.azcoreRuntime.taskManagment.AbstractCallback;
import de.azcore.azcoreRuntime.taskManagment.CallbackTime;
import de.azcore.azcoreRuntime.taskManagment.operations.OperationRegister;
import de.azcore.azcoreRuntime.taskManagment.operations.TaskOperation;
import de.azcore.azcoreRuntime.utils.Color;

import java.util.concurrent.TimeUnit;

public class HeaterCallback extends AbstractCallback {
    @Override
    public void operation() {
        TaskOperation taskOperation = OperationRegister.getOperation("request_heating_operation");
        addOperationData(taskOperation, null);
    }

    @Override
    public void callback(Object object) {
        AppLogger.debug(Color.GREEN + "Heating data request send");
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 3, TimeUnit.MINUTES);
    }

}
