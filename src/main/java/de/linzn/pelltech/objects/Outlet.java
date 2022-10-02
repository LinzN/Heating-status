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

public class Outlet {
    private int index;
    private String name;
    private boolean active;
    private long date;

    public Outlet(int index, String name) {
        this.index = index;
        this.name = name;
        this.active = false;
        this.date = System.currentTimeMillis();
    }

    public void update(boolean active) {
        this.active = active;
        this.date = System.currentTimeMillis();
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

    public long getDate() {
        return date;
    }
}
