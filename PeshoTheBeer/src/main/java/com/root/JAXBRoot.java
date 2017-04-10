/*
 * JAXBRoot.java
 *
 * created at Mar 20, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.root;


import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="root")
public class JAXBRoot
{

    private Map<String, String> map;


    public JAXBRoot()
    {
        map = new HashMap<String, String>();
    }




    public JAXBRoot(Map<String, String> map)
    {
        super();
        this.map = map;
    }


    /**
     * @return Returns value of map.
     */
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getMap()
    {
        return map;
    }


    /**
     * @param map New value for map.
     */
    public void setMap(Map<String, String> map)
    {
        this.map = map;
    }

}
