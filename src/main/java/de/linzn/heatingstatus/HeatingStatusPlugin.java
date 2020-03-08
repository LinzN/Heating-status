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


import de.azcore.azcoreRuntime.AZCoreRuntimeApp;
import de.azcore.azcoreRuntime.modules.pluginModule.AZPlugin;
import de.linzn.heatingstatus.command.HeatingCommand;
import de.linzn.heatingstatus.data.HeaterCallback;
import de.linzn.heatingstatus.dblogger.DBLogger;


public class HeatingStatusPlugin extends AZPlugin {

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
        AZCoreRuntimeApp.getInstance().getZSocketModule().getzServer().registerEvents(new DataListener());
        AZCoreRuntimeApp.getInstance().getCommandModule().registerCommand("heating", new HeatingCommand());
        AZCoreRuntimeApp.getInstance().getCallBackService().registerCallbackListener(new HeaterCallback(), this);
    }

    @Override
    public void onDisable() {
    }
}
