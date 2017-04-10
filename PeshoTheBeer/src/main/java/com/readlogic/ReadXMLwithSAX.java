/*
 * ReadXMLwithSAX.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.strategy.XMLStrategy;


public class ReadXMLwithSAX extends XMLStrategy
{
    private static final Logger log = Logger.getLogger(ReadXMLwithSAX.class);


    @Override
    public void readWith(String type,String file, int from, int to)
    {
        fileIn = new File("Files/CostXML.xml"); // TODO change me
        log.info("Прочит на xml файл чрез SAX Parser"+ fileIn.getPath() + " за посочен разход ==> " + type);
        log.debug("SAX Parser");

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try
        {
            SAXParser saxParser = saxFactory.newSAXParser();
            ReadXMLwithSAXhandler defaultHandler = new ReadXMLwithSAXhandler(type, from, to);
            saxParser.parse(fileIn, defaultHandler);
            //System.exit(0);
        }
        catch (NullPointerException | IllegalArgumentException | ParserConfigurationException | SAXException | IOException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
        }

    }

}
