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

import de.linzn.heatingstatus.events.MQTTCanbusReceiveEvent;
import de.stem.stemSystem.STEMSystemApp;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MqttCanbusListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        JSONObject jsonPayload = new JSONObject(new String(mqttMessage.getPayload()));
        MQTTCanbusReceiveEvent mqttCanbusReceiveEvent = new MQTTCanbusReceiveEvent(jsonPayload);
        STEMSystemApp.getInstance().getEventModule().getStemEventBus().fireEvent(mqttCanbusReceiveEvent);

        if (!mqttCanbusReceiveEvent.isCancelable()) {
            HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.process(jsonPayload);
        }
    }
}
