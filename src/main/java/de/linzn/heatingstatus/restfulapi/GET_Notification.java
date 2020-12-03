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

package de.linzn.heatingstatus.restfulapi;

import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.restfulapi.api.jsonapi.IRequest;
import de.linzn.restfulapi.api.jsonapi.RequestData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GET_Notification implements IRequest {

    private final HeatingStatusPlugin heatingStatusPlugin;

    public GET_Notification(HeatingStatusPlugin heatingStatusPlugin) {
        this.heatingStatusPlugin = heatingStatusPlugin;
    }

    @Override
    public Object proceedRequestData(RequestData requestData) {
        JSONArray jsonArray = new JSONArray();

        List<Notify> notifies = this.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        for (Notify notify : notifies) {
            if (notify.isActive()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("source", "Heizung");
                jsonObject.put("notification", notify.getName());
                jsonArray.put(jsonObject);
            }
        }

        return jsonArray;
    }

    @Override
    public Object genericData() {
        return proceedRequestData(null);
    }

    @Override
    public String name() {
        return "notification";
    }
}
