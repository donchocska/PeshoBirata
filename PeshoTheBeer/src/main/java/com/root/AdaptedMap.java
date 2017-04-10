/*
 * AdaptedMap.java
 *
 * created at Mar 20, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.root;


import javax.xml.bind.annotation.XmlAnyElement;


public class AdaptedMap
{
    private Object value;


    @XmlAnyElement
    public Object getValue()
    {
        return value;
    }


    public void setValue(Object value)
    {
        this.value = value;
    }
}
