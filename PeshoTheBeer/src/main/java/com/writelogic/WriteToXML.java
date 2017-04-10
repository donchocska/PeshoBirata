/*
 * WriteToXML.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.writelogic;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.main.WayToWrite;
import com.readlogic.ReadXMLwithJAXB;
import com.root.JAXBRoot;


public class WriteToXML extends WayToWrite
{
    private static final Logger log = Logger.getLogger(WriteToXML.class);
    OutputStream out = null;


    @Override
    public void writeWith(Map<String, String> map, String file) throws IOException
    {

        try
        {
            fileOut = new File(file); // TODO change me
            out = new FileOutputStream(fileOut, false);

            log.debug("файлa " + fileOut.getPath());
            log.info("Запис на данни в xml файл: " + fileOut.getPath());

            JAXBContext context = JAXBContext.newInstance(JAXBRoot.class);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            JAXBRoot root = new JAXBRoot(map);

            marsh.marshal(root, out);

            log.info("Записaни данни: " + map);

        }
        catch (NullPointerException | JAXBException | IllegalArgumentException | FileNotFoundException | SecurityException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }

}
