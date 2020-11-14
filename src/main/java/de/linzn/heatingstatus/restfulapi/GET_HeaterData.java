/*
 * Copyright (C) 2019. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.heatingstatus.restfulapi;

import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.heatingstatus.objects.Outlet;
import de.linzn.restfulapi.api.jsonapi.get.IGetJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GET_HeaterData implements IGetJSON {
    private final HeatingStatusPlugin heatingStatusPlugin;

    public GET_HeaterData(HeatingStatusPlugin heatingStatusPlugin) {
        this.heatingStatusPlugin = heatingStatusPlugin;
    }

    @Override
    public Object getRequestData(List<String> inputList) {
        List<Inlet> inlets = this.heatingStatusPlugin.heaterProcessor.getInletsList();
        List<Outlet> outlets = this.heatingStatusPlugin.heaterProcessor.getOutletsList();
        List<Notify> notifies = this.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        JSONObject jsonObject = new JSONObject();

        JSONArray inletsArray = new JSONArray();
        for (Inlet inlet : inlets) {
            JSONObject object = new JSONObject();
            object.put("name", inlet.getName());
            object.put("value", inlet.getValue());
            inletsArray.put(object);
        }

        JSONArray outletsArray = new JSONArray();
        for (Outlet outlet : outlets) {
            JSONObject object = new JSONObject();
            object.put("name", outlet.getName());
            object.put("status", outlet.isActive());
            outletsArray.put(object);
        }

        JSONArray notifiesArray = new JSONArray();
        for (Notify notify : notifies) {
            JSONObject object = new JSONObject();
            object.put("name", notify.getName());
            object.put("status", notify.isActive());
            notifiesArray.put(object);
        }

        jsonObject.put("inlets", inletsArray);
        jsonObject.put("outlets", outletsArray);
        jsonObject.put("notifies", notifiesArray);

        return jsonObject;
    }

    @Override
    public Object getGenericData() {
        return getRequestData(null);
    }

    @Override
    public String name() {
        return "heater-data";
    }
}
