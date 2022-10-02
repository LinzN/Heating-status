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

package de.linzn.pelltech.dblogger;


import de.linzn.pelltech.objects.Inlet;
import de.linzn.pelltech.objects.Notify;
import de.linzn.pelltech.objects.Outlet;
import de.stem.stemSystem.STEMSystemApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBLogger {

    public List<Inlet> loadInlets() {
        List<Inlet> inlets = new ArrayList<>();
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            String sqlquery = ("SELECT * FROM `addon_heater_inlets`");
            ResultSet rs = st.executeQuery(sqlquery);
            while (rs.next()) {
                int index = rs.getInt("index");
                String name = rs.getString("name");
                Inlet inlet = new Inlet(index, name);
                inlets.add(inlet);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
        return inlets;
    }

    public List<Outlet> loadOutlets() {
        List<Outlet> outlets = new ArrayList<>();
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            String sqlquery = ("SELECT * FROM `addon_heater_outlets`");
            ResultSet rs = st.executeQuery(sqlquery);
            while (rs.next()) {
                int index = rs.getInt("index");
                String name = rs.getString("name");
                Outlet outlet = new Outlet(index, name);
                outlets.add(outlet);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
        return outlets;
    }

    public List<Notify> loadNotifies() {
        List<Notify> notifies = new ArrayList<>();
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            String sqlquery = ("SELECT * FROM `addon_heater_notifies`");
            ResultSet rs = st.executeQuery(sqlquery);
            while (rs.next()) {
                int index = rs.getInt("index");
                String name = rs.getString("name");
                Notify notify = new Notify(index, name);
                notifies.add(notify);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
        return notifies;
    }

    public void saveInlets(List<Inlet> inlets) {
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            for (Inlet inlet : inlets) {
                String sqlquery = "SELECT `inlet_id` FROM `addon_heater_inlets` WHERE `index` = " + inlet.getIndex();
                int inletID = -1;
                ResultSet rs = st.executeQuery(sqlquery);
                if (rs.next()) {
                    inletID = rs.getInt("inlet_id");
                } else {
                    sqlquery = "INSERT INTO `addon_heater_inlets` (`index`, `name`) values ('" + inlet.getIndex() + "', '" + inlet.getName() + "')";
                    st.executeUpdate(sqlquery);

                    sqlquery = "SELECT `inlet_id` FROM `addon_heater_inlets` WHERE `index` = " + inlet.getIndex();
                    rs = st.executeQuery(sqlquery);
                    if (rs.next()) {
                        inletID = rs.getInt("inlet_id");
                    }
                }

                /* Add data */
                sqlquery = "INSERT INTO `addon_heater_inlets_data` (`inlet_id`, `health`, `value`, `timestamp`) values ('" + inletID + "', '" + (inlet.isHealth() ? 1 : 0) + "', '" + inlet.getValue() + "', '" + inlet.getDate() + "')";
                st.executeUpdate(sqlquery);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
    }

    public void saveOutlets(List<Outlet> outlets) {
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            for (Outlet outlet : outlets) {
                String sqlquery = "SELECT `outlet_id` FROM `addon_heater_outlets` WHERE `index` = " + outlet.getIndex();
                int outletID = -1;
                ResultSet rs = st.executeQuery(sqlquery);
                if (rs.next()) {
                    outletID = rs.getInt("outlet_id");
                } else {
                    sqlquery = "INSERT INTO `addon_heater_outlets` (`index`, `name`) values ('" + outlet.getIndex() + "', '" + outlet.getName() + "')";
                    st.executeUpdate(sqlquery);

                    sqlquery = "SELECT `outlet_id` FROM `addon_heater_outlets` WHERE `index` = " + outlet.getIndex();
                    rs = st.executeQuery(sqlquery);
                    if (rs.next()) {
                        outletID = rs.getInt("outlet_id");
                    }
                }

                /* Add data */
                sqlquery = "INSERT INTO `addon_heater_outlets_data` (`outlet_id`, `active`, `timestamp`) values ('" + outletID + "', '" + (outlet.isActive() ? 1 : 0) + "', '" + outlet.getDate() + "')";
                st.executeUpdate(sqlquery);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
    }

    public void saveNotifies(List<Notify> notifies) {
        try {
            Connection con = STEMSystemApp.getInstance().getDatabaseModule().getConnection();
            Statement st = con.createStatement();
            for (Notify notify : notifies) {
                String sqlquery = "SELECT `notify_id` FROM `addon_heater_notifies` WHERE `index` = " + notify.getIndex();
                int notifyID = -1;
                ResultSet rs = st.executeQuery(sqlquery);
                if (rs.next()) {
                    notifyID = rs.getInt("notify_id");
                } else {
                    sqlquery = "INSERT INTO `addon_heater_notifies` (`index`, `name`) values ('" + notify.getIndex() + "', '" + notify.getName() + "')";
                    st.executeUpdate(sqlquery);

                    sqlquery = "SELECT `notify_id` FROM `addon_heater_notifies` WHERE `index` = " + notify.getIndex();
                    rs = st.executeQuery(sqlquery);
                    if (rs.next()) {
                        notifyID = rs.getInt("notify_id");
                    }
                }

                /* Add data */
                sqlquery = "INSERT INTO `addon_heater_notifies_data` (`notify_id`, `active`, `timestamp`) values ('" + notifyID + "', '" + (notify.isActive() ? 1 : 0) + "', '" + notify.getDate() + "')";
                st.executeUpdate(sqlquery);
            }
            STEMSystemApp.getInstance().getDatabaseModule().releaseConnection(con);
        } catch (SQLException e) {
            STEMSystemApp.LOGGER.ERROR(e);
        }
    }
}
