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

package de.linzn.pelltech;


import de.linzn.pelltech.command.HeatingCommand;
import de.linzn.pelltech.data.HeaterCallback;
import de.linzn.pelltech.dblogger.DBLogger;
import de.linzn.pelltech.restfulapi.GET_HeaterData;
import de.linzn.pelltech.restfulapi.GET_Notification;
import de.linzn.pelltech.webApi.WebApiHandler;
import de.linzn.restfulapi.RestFulApiPlugin;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;


public class PelltechPlugin extends STEMPlugin {

    public static PelltechPlugin pelltechPlugin;
    public DBLogger dbLogger;
    public HeaterProcessor2 heaterProcessor;
    private WebApiHandler webApiHandler;

    public PelltechPlugin() {
        pelltechPlugin = this;
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
        this.webApiHandler = new WebApiHandler(this);
    }

    @Override
    public void onDisable() {
    }
}
