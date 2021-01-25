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

package de.linzn.heatingstatus;



import de.linzn.heatingstatus.command.HeatingCommand;
import de.linzn.heatingstatus.data.HeaterCallback;
import de.linzn.heatingstatus.dblogger.DBLogger;
import de.linzn.heatingstatus.restfulapi.GET_HeaterData;
import de.linzn.heatingstatus.restfulapi.GET_Notification;
import de.linzn.restfulapi.RestFulApiPlugin;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;


public class HeatingStatusPlugin extends STEMPlugin {

    public static HeatingStatusPlugin heatingStatusPlugin;

    public DBLogger dbLogger;
    public HeaterProcessor2 heaterProcessor;

    public HeatingStatusPlugin() {
        heatingStatusPlugin = this;
        dbLogger = new DBLogger();
        heaterProcessor = new HeaterProcessor2();
    }

    @Override
    public void onEnable() {
        STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(new HeaterCallback(), this);
        STEMSystemApp.getInstance().getCommandModule().registerCommand("heating", new HeatingCommand());
        STEMSystemApp.getInstance().getMqttModule().subscribe("uvr/canbus/data", new MqttCanbusListener());
        RestFulApiPlugin.restFulApiPlugin.registerIGetJSONClass(new GET_HeaterData(this));
        RestFulApiPlugin.restFulApiPlugin.registerIGetJSONClass(new GET_Notification(this));
    }

    @Override
    public void onDisable() {
    }
}
