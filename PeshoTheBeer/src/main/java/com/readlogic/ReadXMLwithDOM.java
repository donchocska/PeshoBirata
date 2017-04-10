/*
 * ReadXMLwithDOM.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.strategy.XMLStrategy;
import com.validation.FileValidator;


public class ReadXMLwithDOM extends XMLStrategy
{
    private static final Logger log = Logger.getLogger(ReadXMLwithDOM.class);


    @Override
    public void readWith(String type, String file, int from, int to)
    {
        final FileValidator validator = new FileValidator();
        try
        {
            fileIn = new File(file); // TODO change me

            log.debug("DOM Parser.");
            log.info("Извърши се прочит на xml файл чрез DOM Parser." + fileIn.getPath() + " за посочен разход ==> " + type);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileIn);

            NodeList nList = doc.getElementsByTagName(type);

            yes = 0;
            no = 0;
            for (int i = 0; i < nList.getLength(); i++)
            {

                Element element = (Element)nList.item(i);

                validNumber = validator.isValid(element.getTextContent(), from, to);
                if (validNumber != null)
                {
                    setTyp(type);
                    setVal(validNumber);

                    this.map.put(type+"", validNumber.toUpperCase());

                    log.debug("Прочетена стойност: " + validNumber.toUpperCase());
                    log.info("Прочетена стойност: " + validNumber.toUpperCase());

                    // log.debug("Прихванати стойности: (" + validNumber.toUpperCase() + ") Индекс: " + " [Оригинал: " + valueFromText +
                    // "]");
                    yes++;
                }
                else if (validNumber == null)
                {
                    // log.debug("Пропуснати стойности: " + " Индекс: " + " [Оригинал: " + valueFromText + "]");
                    no++;
                }
            }
            setYes(yes);
            setNo(no);

        }
        catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException | FactoryConfigurationError | ParserConfigurationException | DOMException | SAXException | IOException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
        }
    }

}
