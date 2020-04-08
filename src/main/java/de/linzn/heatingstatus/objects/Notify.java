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

package de.linzn.heatingstatus.objects;


import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.notificationModule.NotificationContainer;
import de.stem.stemSystem.modules.notificationModule.NotificationPriority;

public class Notify {
    private int index;
    private String name;
    private boolean active;
    private long date;

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
                NotificationContainer notificationContainer = new NotificationContainer("New notify " + this.name.toUpperCase() + " with state ACTIVE is called!", NotificationPriority.HIGH);
                STEMSystemApp.getInstance().getNotificationModule().pushNotification(notificationContainer);
            }
        }
    }

    public long getDate() {
        return date;
    }
}
