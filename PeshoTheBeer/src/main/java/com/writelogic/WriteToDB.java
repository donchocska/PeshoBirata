/*
 * WriteToDB.java
 *
 * created at Mar 9, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.writelogic;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.database.DatabaseConnection;
import com.main.WayToWrite;


public class WriteToDB extends WayToWrite
{
    private static final Logger log = Logger.getLogger(WriteToDB.class);


    @Override
    public void writeWith(Map<String, String> map, String file) throws IOException
    {
        log.info("Запис на данни в база данни H2 таблица PESHOBIRATAOUT");
        log.debug("базата данни в таблица PESHOBIRATAOUT");

        PreparedStatement prs = null;
        String sql = "INSERT INTO PESHOBIRATAOUT(type, value) VALUES(?, ?)";
        try
        {
            final Connection connection = DatabaseConnection.getInstanse().getConnection();
            prs = connection.prepareStatement(sql);

            for (Map.Entry<String, String> m : map.entrySet())
            {
                prs.setString(1, m.getKey());
                prs.setString(2, m.getValue());
                prs.execute();
            }
        }
        catch (IllegalStateException | SQLException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
        }
    }
}
