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

package de.linzn.pelltech.events;

import de.stem.stemSystem.modules.eventModule.CancelableEvent;
import org.json.JSONObject;

public class MQTTCanbusReceiveEvent extends CancelableEvent {

    private JSONObject jsonPayload;

    public MQTTCanbusReceiveEvent(JSONObject jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    public JSONObject getJsonPayload() {
        return jsonPayload;
    }
}
