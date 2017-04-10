/*
 * ReadXMLwithStAX.java
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

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.strategy.XMLStrategy;
import com.validation.FileValidator;


public class ReadXMLwithStAX extends XMLStrategy
{
    private static final Logger log = Logger.getLogger(ReadXMLwithStAX.class);

    final FileValidator validator = new FileValidator();


    @Override
    public void readWith(String type, String file, int from, int to)
    {


        fileIn = new File("Files/CostXML.xml"); // TODO change me
        InputStream in = null;

        log.info("Извърши се прочит на xml файл чрез StAX Parser." + fileIn.getPath() + " за посочен разход ==> " + type);
        log.debug("StAX Parser.");

        try
        {
            in = new FileInputStream(fileIn);

            XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = xmlFactory.createXMLEventReader(in);

            yes = 0;
            no = 0;
            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement())
                {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase(type))
                    {
                        event = eventReader.nextEvent();
                        String s = event.asCharacters().getData();
                        validNumber = validator.isValid(s, from, to);
                        if (validNumber != null)
                        {

                            map.put(startElement.getName().getLocalPart() + "", validNumber.toUpperCase());

                            log.debug("Прочетена стойност: " + validNumber.toUpperCase());
                            log.info("Прочетена стойност: " + validNumber.toUpperCase());
                            yes++;
                        }
                        else if(validNumber == null){
                            no++;
                        }

                    }
                }
            }
            setYes(yes);
            setNo(no);

        }
        catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException | XMLStreamException | FileNotFoundException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
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
                    e.printStackTrace();
                }
            }
        }
    }

}
