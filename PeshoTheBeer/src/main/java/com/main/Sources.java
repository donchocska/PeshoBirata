/*
 * Sources.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.main;

import java.io.IOException;
import java.util.Map;

public abstract class Sources
{
    public WayToRead wayToRead;
    public WayToWrite wayToWrite;
    public PdfChartHelper pdfHelper;

    public Sources(WayToRead r)
    {
        this.wayToRead = r;
    }


    public Sources(WayToWrite w)
    {
        this.wayToWrite = w;
    }

    public Sources(PdfChartHelper pdf){
        this.pdfHelper = pdf;
    }

    public abstract void applyReading(String cost, String file, int from, int to);


    public abstract void applyWriting(Map<String, String> map, String file) throws IOException;

    public abstract void applyPDFWriting(Map<String, String> map, String file, float yes, float no) throws IOException;
}
