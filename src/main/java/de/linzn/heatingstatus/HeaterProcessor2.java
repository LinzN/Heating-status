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


import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.heatingstatus.objects.Outlet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class HeaterProcessor2 {
    private List<Inlet> inletsList;
    private List<Outlet> outletsList;
    private List<Notify> notifiesList;
    private Date date;

    public HeaterProcessor2() {
        this.loadData();
    }

    private void loadData() {
        this.inletsList = HeatingStatusPlugin.heatingStatusPlugin.dbLogger.loadInlets();
        this.outletsList = HeatingStatusPlugin.heatingStatusPlugin.dbLogger.loadOutlets();
        this.notifiesList = HeatingStatusPlugin.heatingStatusPlugin.dbLogger.loadNotifies();
    }

    void process(JSONObject rawJsonObject) {
        JSONArray inlets = rawJsonObject.getJSONArray("inlets");
        this.updateInlets(inlets);
        JSONArray outlets = rawJsonObject.getJSONArray("outlets");
        this.updateOutlets(outlets);
        JSONArray notifies = rawJsonObject.getJSONArray("notifies");
        this.updateNotifies(notifies);
        this.date = new Date();
    }

    private void updateInlets(JSONArray inlets) {
        for (int i = 0; i < inlets.length(); i++) {
            JSONObject jsonInlet = inlets.getJSONObject(i);
            if (!jsonInlet.getString("description").equalsIgnoreCase("  -----") && !jsonInlet.getString("description").isEmpty()) {
                int index = jsonInlet.getInt("index");
                boolean health = jsonInlet.getString("state").equalsIgnoreCase("OK");
                double value = jsonInlet.getDouble("value");

                /* Get inlet from list*/
                Inlet inlet = null;
                for (Inlet tempInlet : inletsList) {
                    if (tempInlet.getIndex() == index) {
                        inlet = tempInlet;
                        break;
                    }
                }

                /* Create inlet if not exist*/
                if (inlet == null) {
                    String name = jsonInlet.getString("description");
                    inlet = new Inlet(index, name);
                    this.inletsList.add(inlet);
                }

                /* Update inlet with new data */
                inlet.update(health, value);
            }
        }
        //HeatingStatusPlugin.heatingStatusPlugin.dbLogger.saveInlets(this.inletsList);
    }

    private void updateOutlets(JSONArray outlets) {
        for (int i = 0; i < outlets.length(); i++) {
            JSONObject jsonOutlet = outlets.getJSONObject(i);
            if (!jsonOutlet.getString("description").equalsIgnoreCase("  -----") && !jsonOutlet.getString("description").isEmpty()) {
                int index = jsonOutlet.getInt("index");
                boolean active = jsonOutlet.getString("value").equalsIgnoreCase("EIN");

                /* Get outlet from list*/
                Outlet outlet = null;
                for (Outlet tempOutlet : outletsList) {
                    if (tempOutlet.getIndex() == index) {
                        outlet = tempOutlet;
                        break;
                    }
                }

                /* Create outlet if not exist*/
                if (outlet == null) {
                    String name = jsonOutlet.getString("description");
                    outlet = new Outlet(index, name);
                    this.outletsList.add(outlet);
                }

                /* Update outlet with new data */
                outlet.update(active);
            }
        }
        //HeatingStatusPlugin.heatingStatusPlugin.dbLogger.saveOutlets(this.outletsList);
    }

    private void updateNotifies(JSONArray notifies) {
        for (int i = 0; i < notifies.length(); i++) {
            JSONObject jsonNotifies = notifies.getJSONObject(i);
            if (!jsonNotifies.getString("notify").equalsIgnoreCase("  -----") && !jsonNotifies.getString("notify").isEmpty()) {
                int index = jsonNotifies.getInt("index");
                boolean active = jsonNotifies.getString("state").equalsIgnoreCase("EIN");

                /* Get notify from list*/
                Notify notify = null;
                for (Notify tempNotify : notifiesList) {
                    if (tempNotify.getIndex() == index) {
                        notify = tempNotify;
                        break;
                    }
                }

                /* Create notify if not exist*/
                if (notify == null) {
                    String name = jsonNotifies.getString("notify");
                    notify = new Notify(index, name);
                    this.notifiesList.add(notify);
                }

                /* Update notify with new data */
                notify.update(active);
            }
        }
        //HeatingStatusPlugin.heatingStatusPlugin.dbLogger.saveNotifies(this.notifiesList);
    }

    public List<Inlet> getInletsList() {
        return inletsList;
    }

    public List<Outlet> getOutletsList() {
        return outletsList;
    }

    public List<Notify> getNotifiesList() {
        return notifiesList;
    }

    public Date getDate() {
        return date;
    }
}
