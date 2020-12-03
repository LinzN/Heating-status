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

package de.linzn.heatingstatus.command;


import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.heatingstatus.objects.Outlet;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.commandModule.ICommand;

import java.util.Date;
import java.util.List;

public class HeatingCommand implements ICommand {
    @Override
    public boolean executeTerminal(String[] strings) {
        if (strings.length > 0) {
            String command = strings[0];

            if (command.equalsIgnoreCase("status")) {
                status();
            }
        }
        return false;
    }

    private void status() {
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();
        List<Outlet> outlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getOutletsList();
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        long lastSync = (new Date().getTime() - HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getDate().getTime()) / 1000;

        STEMSystemApp.LOGGER.LIVE("############################################");
        STEMSystemApp.LOGGER.LIVE("## Inlets:");
        STEMSystemApp.LOGGER.LIVE("############################################");
        for (Inlet inlet : inlets) {
            STEMSystemApp.LOGGER.LIVE(inlet.getName() + " status -> " + inlet.isHealth() + " value -> " + inlet.getValue());
        }

        STEMSystemApp.LOGGER.LIVE("############################################");
        STEMSystemApp.LOGGER.LIVE("## Outlets:");
        STEMSystemApp.LOGGER.LIVE("############################################");
        for (Outlet outlet : outlets) {
            STEMSystemApp.LOGGER.LIVE(outlet.getName() + " active -> " + outlet.isActive());
        }
        STEMSystemApp.LOGGER.LIVE("############################################");
        STEMSystemApp.LOGGER.LIVE("## Notifies:");
        STEMSystemApp.LOGGER.LIVE("############################################");
        for (Notify notify : notifies) {
            STEMSystemApp.LOGGER.LIVE(notify.getName() + " status -> " + notify.isActive());
        }

        STEMSystemApp.LOGGER.LIVE("############################################");
        STEMSystemApp.LOGGER.LIVE("## Last sync:");
        STEMSystemApp.LOGGER.LIVE(lastSync + " seconds ago!");
    }
}
