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
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.modules.commandModule.ICommand;

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

        AppLogger.logger("############################################", false);
        AppLogger.logger("## Inlets:", false);
        AppLogger.logger("############################################", false);
        for (Inlet inlet : inlets) {
            AppLogger.logger(inlet.getName() + " status -> " + inlet.isHealth() + " value -> " + inlet.getValue(), false);
        }

        AppLogger.logger("############################################", false);
        AppLogger.logger("## Outlets:", false);
        AppLogger.logger("############################################", false);
        for (Outlet outlet : outlets) {
            AppLogger.logger(outlet.getName() + " active -> " + outlet.isActive(), false);
        }
        AppLogger.logger("############################################", false);
        AppLogger.logger("## Notifies:", false);
        AppLogger.logger("############################################", false);
        for (Notify notify : notifies) {
            AppLogger.logger(notify.getName() + " status -> " + notify.isActive(), false);
        }

    }
}
