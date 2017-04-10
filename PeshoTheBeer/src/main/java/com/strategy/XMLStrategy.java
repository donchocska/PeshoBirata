/*
 * TestStrategy.java
 *
 * created at Mar 6, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.strategy;


import java.util.Scanner;

import org.apache.log4j.Logger;

import com.main.WayToRead;
import com.readlogic.ReadXMLwithDOM;
import com.readlogic.ReadXMLwithJAXB;
import com.readlogic.ReadXMLwithSAX;
import com.readlogic.ReadXMLwithStAX;


public class XMLStrategy extends WayToRead
{
    private static final Logger log = Logger.getLogger(XMLStrategy.class);
    Scanner scan = null;


    public XMLStrategy readXMLgui(String xmlReadingMethod)
    {

         XMLStrategy res= null;

         switch (xmlReadingMethod)
         {
         case "DOM":
         res = new ReadXMLwithDOM();
         break;

         case "SAX":
         res = new ReadXMLwithSAX();
         break;

         case "StAX":
         res = new ReadXMLwithStAX();
         break;

         case "JAXB":
         res = new ReadXMLwithJAXB();
         break;

         default:
         return new ReadXMLwithDOM();
         }
         return res;
    }


    public XMLStrategy readXML()
    {
        scan = new Scanner(System.in);
        log.debug("Въведете подхода по който да се прочете XML файла: ");
        log.debug("[1] DOM");
        log.debug("[2] SAX");
        log.debug("[3] STAX");
        log.debug("[4] JAXB");
        log.debug("Избор: ");
        int selection = scan.nextInt();
        if (selection == 1)
        {
            return new ReadXMLwithDOM();
        }
        else if (selection == 2)
        {
            return new ReadXMLwithSAX();
        }
        else if (selection == 3)
        {
            return new ReadXMLwithStAX();
        }
        else if (selection == 4)
        {
            return new ReadXMLwithJAXB();
        }
        scan.close();
        return new ReadXMLwithDOM();
    }


    public void add(WayToRead w)
    {

    }


    @Override
    public void readWith(String type, String file, int from, int to)
    {

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
    public void setTyp(String typ)
    {
        this.typ = typ;

    }


    @Override
    public void setVal(String val)
    {
        this.val = val;

    }


    @Override
    public Float getYes()
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Float getNo()
    {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public void setYes(float y)
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNo(float n)
    {
        // TODO Auto-generated method stub

    }

}
