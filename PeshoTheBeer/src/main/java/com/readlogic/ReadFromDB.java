/*
 * ReadFromDB.java
 *
 * created at Mar 2, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.database.DatabaseConnection;
import com.main.WayToRead;
import com.validation.FileValidator;


public class ReadFromDB extends WayToRead
{
    private static final Logger log = Logger.getLogger(ReadFromDB.class);
    final FileValidator validator = new FileValidator();


    @Override
    public void readWith(String type, String sql, int from, int to)
    {
        log.debug("Логика за четене от базата данни");
        log.info("Прочит на данни от база данни H2 за посочен разход ==> " + type);

        final String query = "SELECT value FROM PESHOBIRATA WHERE type=?";
        PreparedStatement prs = null;
        try
        {
            final Connection connection = DatabaseConnection.getInstanse().getConnection();
            prs = connection.prepareStatement(query);
            prs.setString(1, type);
            ResultSet result = prs.executeQuery();

            yes = 0;
            no = 0;
            while (result.next())
            {
                String resultValue = result.getString("value");
                validNumber = validator.isValid(resultValue, from, to);
                    if (validNumber != null)
                    {
                        setTyp(type);
                        setVal(validNumber);

                        map.put(type + "", validNumber.toUpperCase());

                        log.debug("Прочетена стойност: " + validNumber.toUpperCase());
                        log.info("Прочетена стойност: " + validNumber.toUpperCase());

//                        log.debug("Прихванати стойности: (" + validNumber.toUpperCase() + ") Индекс: " + " [Оригинал: " + valueFromText + "]");
                          yes++;
                    }
                    else if (validNumber == null)
                    {
//                        log.debug("Пропуснати стойности: " + " Индекс: " + " [Оригинал: " + valueFromText + "]");
                          no++;
                    }
            }
            setYes(yes);
            setNo(no);
        }
        catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException | SQLException e)
        {
            log.log(Level.ERROR, e.getClass() + " " + e.getMessage());
        }
        finally
        {
            if (prs != null)
            {
                try
                {
                    prs.close();
                }
                catch (SQLException e)
                {
                    log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
                }
            }
        }

    }


    @Override
    public String getTyp()
    {

        return typ;
    }


    @Override
    public String getVal()
    {
        return val;
    }


    @Override
    public void setTyp(String t)
    {
        this.typ = t;

    }


    @Override
    public void setVal(String val)
    {
        this.val = val;

    }



    @Override
    public Float getYes()
    {
        return yes;
    }


    @Override
    public Float getNo()
    {

        return no;
    }


    @Override
    public void setYes(float y)
    {
        this.yes = y;

    }


    @Override
    public void setNo(float n)
    {
        this.no = n;

    }

}
