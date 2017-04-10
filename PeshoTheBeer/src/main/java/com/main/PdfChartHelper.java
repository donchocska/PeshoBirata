/*
 * PdfChartHelper.java
 *
 * created at Mar 21, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.main;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class PdfChartHelper
{
    public File fileOut;
    public abstract void writeWith(Map<String, String> map, String file, float yes, float no) throws IOException;

}