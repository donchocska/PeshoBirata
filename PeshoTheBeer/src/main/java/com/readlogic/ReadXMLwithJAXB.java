/*
 * ReadXMLwithJAXB.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.root.JAXBRoot;
import com.strategy.XMLStrategy;
import com.validation.FileValidator;


public class ReadXMLwithJAXB extends XMLStrategy
{
    private static final Logger log = Logger.getLogger(ReadXMLwithJAXB.class);


    @Override
    public void readWith(String type, String file, int from, int to)
    {
        log.debug("JAXB");

        fileIn = new File(file); // TODO change me
        InputStream in = null;
        final FileValidator validator = new FileValidator();
        try
        {
            in = new FileInputStream(fileIn);

            final JAXBContext context = JAXBContext.newInstance(JAXBRoot.class);
            final Unmarshaller unmarsh = context.createUnmarshaller();

            final JAXBRoot r = (JAXBRoot)unmarsh.unmarshal(in);

            yes = 0;
            no = 0;
            for (Map.Entry<String, String> m : r.getMap().entrySet())
            {

                if (m.getKey().equalsIgnoreCase(type))
                {
                    validNumber = validator.isValid(m.getValue(), from, to);
                    if (validNumber != null)
                    {

                        map.put(m.getKey() + "", validNumber.toUpperCase());
                        log.debug(validNumber.toUpperCase() + " " + m.getKey());
                        yes++;
                    }
                    else if (validNumber == null)
                    {
                        // log.debug("Пропуснати стойности: " + " Индекс: " + " [Оригинал: " + valueFromText + "]");
                           no++;
                    }
                }
            }
            setYes(yes);
            setNo(no);

        }
        catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException | SecurityException | FileNotFoundException | JAXBException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
                }
            }
        }

    }

}
