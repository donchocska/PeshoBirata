/*
 * Wayyy.java
 *
 * created at Mar 2, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.main;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class WayToRead
{
     public String typ;
     public String val;
     public File fileIn;
     public float yes, no;
     public String validNumber;
     public Map<String, String> map = new IdentityHashMap<String, String>();

    public abstract void readWith(String type, String file, int from, int to);

    public abstract String getTyp();
    public abstract String getVal();

    public abstract void setTyp(String t);
    public abstract void setVal(String v);

    public abstract Float getYes();
    public abstract Float getNo();
    public abstract void setYes(float y);
    public abstract void setNo(float n);
}



