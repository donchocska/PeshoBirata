/*
 * SourceForUse.java
 *
 * created at Mar 2, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.files;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.main.PdfChartHelper;
import com.main.Sources;
import com.main.WayToRead;
import com.main.WayToWrite;

public class SourceForUse extends Sources
{
    private static final Logger log = Logger.getLogger(SourceForUse.class);
    public SourceForUse(WayToRead r)
    {
        super(r);
    }

    public SourceForUse(WayToWrite w)
    {
        super(w);
    }

    public SourceForUse(PdfChartHelper pdf){
        super(pdf);
    }


    @Override
    public void applyReading(String cost, String file, int from, int to)
    {
        log.debug("Резултата ще бъде прочетен с: ");
        wayToRead.readWith(cost, file, from, to);
    }

    @Override
    public void applyWriting(Map<String, String> map, String file) throws IOException
    {
        log.debug("Резултата ще бъде записан във");
        wayToWrite.writeWith(map, file);

    }


    @Override
    public void applyPDFWriting(Map<String, String> map, String file, float yes, float no) throws IOException
    {
        pdfHelper.writeWith(map, file, yes, no);
    }

}



