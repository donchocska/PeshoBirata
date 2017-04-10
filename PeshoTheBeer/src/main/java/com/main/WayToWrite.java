/*
 * WayToWrite.java
 *
 * created at Mar 2, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.main;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class WayToWrite
{
    public File fileOut;
    public abstract void writeWith(Map<String, String> map, String file) throws IOException;
}
