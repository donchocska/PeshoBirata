/*
 * DatabaseConnection.java

 *
 * created at Mar 9, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */

package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.GUI.GUI;

public final class DatabaseConnection {
    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = -6910178683455200981L;


    private static final String DRIVER = "org.h2.Driver";
    private final String URL = GUI.txt4.getText();
    private static final String USER_NAME = GUI.txt5.getText();
    private static final String PASS = GUI.txt6.getText();
    private static DatabaseConnection instance;
    private static Connection conn;

    private static final Logger log = Logger.getLogger(DatabaseConnection.class);

    private DatabaseConnection() {

    }

    public static DatabaseConnection getInstanse() {
        if (null == instance) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        connect();
        return conn;

    }

    private void connect() throws SQLException {
        if (null == conn) {
            try {
                Class.forName(DRIVER);
            } catch (final ClassNotFoundException e) {
                System.out.println("Driver not found.");
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(URL, USER_NAME, PASS);

        }
    }

    public void disconnect() {
        if (null != conn) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}