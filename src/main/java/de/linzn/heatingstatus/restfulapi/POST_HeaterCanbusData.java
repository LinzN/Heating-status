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
import de.linzn.restfulapi.api.jsonapi.IRequest;
import de.linzn.restfulapi.api.jsonapi.RequestData;
import org.json.JSONObject;

public class POST_HeaterCanbusData implements IRequest {
    @Override
    public Object proceedRequestData(RequestData requestData) {
        String data = requestData.getPostQueryData().get("requestBody");
        JSONObject requestBody = new JSONObject(data);
        HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.process(requestBody);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", requestBody);
        jsonObject.put("valid", requestBody.length() > 0);
        return jsonObject;
    }

    @Override
    public String name() {
        return "heater-canbus-data";
    }

}
