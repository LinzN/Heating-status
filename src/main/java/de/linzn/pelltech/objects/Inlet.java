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


import de.linzn.openJL.math.FloatingPoint;

public class Inlet {
    private int index;
    private String name;
    private boolean health;
    private double value;
    private long date;

    public Inlet(int index, String name) {
        this.index = index;
        this.name = name;
        this.health = false;
        this.value = 0.0;
        this.date = System.currentTimeMillis();
    }

    public void update(boolean health, double value) {
        this.health = health;
        this.value = value;
        this.date = System.currentTimeMillis();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public boolean isHealth() {
        return health;
    }

    public double getValue() {
        return FloatingPoint.round(value, 2);
    }

    public long getDate() {
        return date;
    }
}
