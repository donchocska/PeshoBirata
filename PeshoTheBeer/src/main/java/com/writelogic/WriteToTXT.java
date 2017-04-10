/*
 * WriteToTXT.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.writelogic;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.main.WayToWrite;


public class WriteToTXT extends WayToWrite
{

    Writer writeToFile = null;
    BufferedWriter out = null;

    private static final Logger log = Logger.getLogger(WriteToTXT.class);


    @Override
    public void writeWith(Map<String, String> map, String file) throws IOException
    {
        try
        {
            fileOut = new File(file); // TODO change me
            writeToFile = new FileWriter(fileOut, false); // Използване на writer защото записва 16-bits (character stream)
            out = new BufferedWriter(writeToFile);

            log.debug("Текстов файл " + fileOut.getPath());
            log.info("Запис на данни в текстов файл: " + fileOut.getPath());

            for (Map.Entry<String, String> m : map.entrySet())
            {
                out.write("Разход: " + m.getKey() + " Стойност: " + m.getValue() + System.lineSeparator());
                log.info("Записана стойност: " + fileOut.getPath());
            }

            out.flush();

        }
        catch (NullPointerException | IOException | IllegalStateException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
        }
        finally
        {
            if (writeToFile != null)
            {
                writeToFile.close();
            }
            if (out != null)
            {
                out.close();
            }
        }
    }
}
