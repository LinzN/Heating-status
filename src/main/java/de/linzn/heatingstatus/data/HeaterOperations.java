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


import de.azcore.azcoreRuntime.AZCoreRuntimeApp;
import de.azcore.azcoreRuntime.taskManagment.operations.TaskOperation;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HeaterOperations {
    public static TaskOperation request_heating_operation = o -> {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("request");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AZCoreRuntimeApp.getInstance().getZSocketModule().getzServer().getClients().values().forEach(serverConnection -> serverConnection.writeOutput("heater_data", byteArrayOutputStream.toByteArray()));
        return null;
    };

}
