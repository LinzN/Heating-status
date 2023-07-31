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

package de.linzn.pelltech.objects;


import de.linzn.pelltech.PelltechPlugin;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.informationModule.InformationBlock;
import de.stem.stemSystem.modules.informationModule.InformationIntent;
import de.stem.stemSystem.modules.notificationModule.NotificationPriority;

public class Notify {
    private int index;
    private String name;
    private boolean active;
    private long date;

    private InformationBlock informationBlock = null;

    public Notify(int index, String name) {
        this.index = index;
        this.name = name;
        this.active = false;
        this.date = System.currentTimeMillis();
    }

    public void update(boolean active) {
        boolean oldValue = this.active;
        this.active = active;
        this.date = System.currentTimeMillis();
        this.checkStatus(oldValue);
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    private void checkStatus(boolean oldValue) {
        if (!oldValue) {
            if (this.active) {
                String message = "New notify " + this.name.toUpperCase() + " with state ACTIVE is called!";
                STEMSystemApp.getInstance().getNotificationModule().pushNotification(message, NotificationPriority.HIGH, PelltechPlugin.pelltechPlugin);

                if (this.informationBlock != null && this.informationBlock.isActive()) {
                    this.informationBlock.expire();
                    this.informationBlock = null;
                }
                this.informationBlock = new InformationBlock("Pelltech Heizung", "Neue Meldung: " + this.name.toUpperCase(), PelltechPlugin.pelltechPlugin);
                this.informationBlock.setIcon("FIRE");
                this.informationBlock.setExpireTime(-1L);
                this.informationBlock.addIntent(InformationIntent.SHOW_DISPLAY);
                STEMSystemApp.getInstance().getInformationModule().queueInformationBlock(informationBlock);

            } else {
                if (this.informationBlock != null && this.informationBlock.isActive()) {
                    this.informationBlock.expire();
                    this.informationBlock = null;
                }
            }
        }
    }

    public long getDate() {
        return date;
    }
}
