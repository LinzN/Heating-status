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

    public void process(JSONObject rawJsonObject) {
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

            /* Update inlet with new data */
            if (inlet != null) {
                inlet.update(health, value);
            }
        }
    }

    private void updateOutlets(JSONArray outlets) {
        for (int i = 0; i < outlets.length(); i++) {
            JSONObject jsonOutlet = outlets.getJSONObject(i);

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

            /* Update outlet with new data */
            if (outlet != null) {
                outlet.update(active);
            }
        }
    }

    private void updateNotifies(JSONArray notifies) {
        for (int i = 0; i < notifies.length(); i++) {
            JSONObject jsonNotifies = notifies.getJSONObject(i);

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

            /* Update notify with new data */
            if (notify != null) {
                notify.update(active);
            }
        }
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
