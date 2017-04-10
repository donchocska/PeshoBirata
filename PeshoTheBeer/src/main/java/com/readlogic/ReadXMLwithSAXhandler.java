/*
 * ReadXMLwithSAXhandler.java
 *
 * created at Mar 7, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.validation.FileValidator;


public class ReadXMLwithSAXhandler extends DefaultHandler
{
    private static final Logger log = Logger.getLogger(ReadXMLwithSAXhandler.class);

    final FileValidator validator = new FileValidator();

    private boolean bType;

    private String type;
    private int form, to;

    public ReadXMLwithSAXhandler(String type, int form, int to)
    {
        super();
        this.type = type;
        this.form = form;
        this.to = to;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equalsIgnoreCase(getType()))
        {
            bType = true;
        }
        else
        {
            bType = false;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (bType == true)
        {

            String val = new String(ch, start, length).trim();
            String validNumber = validator.isValid(val, getForm(), getTo());
            if (validNumber != null)
            {

                log.debug("Прочетени стойности: " + validNumber.toUpperCase());
                log.info("Прочетени стойности: " + validNumber.toUpperCase());
            }
        }
        else
        {
            bType = false;
        }
    }


    public String getType()
    {
        return type;
    }


    public void setType(String type)
    {
        this.type = type;
    }


    /**
     * @return Returns value of form.
     */
    public int getForm()
    {
        return form;
    }


    /**
     * @param form New value for form.
     */
    public void setForm(int form)
    {
        this.form = form;
    }


    /**
     * @return Returns value of to.
     */
    public int getTo()
    {
        return to;
    }


    /**
     * @param to New value for to.
     */
    public void setTo(int to)
    {
        this.to = to;
    }



}
