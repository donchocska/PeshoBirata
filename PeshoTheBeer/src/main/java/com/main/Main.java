/*
 * Main.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.main;


import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.GUI.GUIlogic;
import com.files.SourceForUse;
import com.readlogic.ReadFromDB;
import com.readlogic.ReadFromTxtFile;
import com.strategy.XMLStrategy;
import com.writelogic.WriteToDB;
import com.writelogic.WriteToPDF;
import com.writelogic.WriteToTXT;
import com.writelogic.WriteToXML;


public class Main
{

    private static final Logger log = Logger.getLogger(Main.class);

    private static Scanner scan = null;

    private static Sources read = null;
    private static Sources write = null;

    public static void main(String[] args)
    {
        PropertyConfigurator.configure("log4j.properties");

        /* Работа с данни от GUI */

        GUIlogic gl = new GUIlogic();
        gl.enterGUI();

        /* Край на GUI */

        /* Сканиране на данни от конзолата */

        log.debug("===========Начало============");
        log.debug("Добре дошли в програмата на Пешо!!!");
        log.debug("Изберете мястото от където ще извличате данни:");

        int selection;
        String fileIn;
        int from, to;

        log.debug("[1] Четене от текстовият файл");
        log.debug("[2] Четене от XML файла");
        log.debug("[3] Четене от базата данни");
        log.debug("[4] Изход");
        log.debug("Избор: ");
        try
        {
            scan = new Scanner(System.in);
            selection = scan.nextInt();

            log.debug("Въведете пътя до файла, който ще се чете: ");
            fileIn = scan.next();
            String type = null;

            switch (selection)
            {
                case 1: // Четене от текстовият файл
                    log.debug("Избрахте 1:");
                    log.debug("Въведете типа на търсения разход (Пр. bira): ");
                    type = scan.next();
                    log.debug("Въведете типа на входното число: ");
                    from = scan.nextInt();
                    log.debug("Въведете типа на изходното число: ");
                    to = scan.nextInt();
                    read = new SourceForUse(new ReadFromTxtFile());
                    read.applyReading(type, fileIn, from, to);

                    choiceWhereToWrite(selection); // избор къде да бъде записан резултата от txt файла

                    break;
                case 2: // Четене от XML файла
                    log.debug("Избрахте 2: ");
                    log.debug("Въведете типа на търсения разход (Пр. bira): ");
                    type = scan.next();
                    log.debug("Въведете типа на входното число: ");
                    from = scan.nextInt();
                    log.debug("Въведете типа на изходното число: ");
                    to = scan.nextInt();
                    read = new SourceForUse(new XMLStrategy().readXML());
                    read.applyReading(type, fileIn, from, to);

                    choiceWhereToWrite(selection); // избор къде да бъде записан резултата от xml файла

                    break;
                case 3:
                    log.debug("Избрахте 3: ");
                    log.debug("Въведете типа на търсения разход (Пр. bira): ");
                    type = scan.next();
                    log.debug("Въведете типа на входното число: ");
                    from = scan.nextInt();
                    log.debug("Въведете типа на изходното число: ");
                    to = scan.nextInt();
                    read = new SourceForUse(new ReadFromDB());
                    read.applyReading(type, fileIn, from, to);

                    choiceWhereToWrite(selection); // избор къде да бъде записан резултата получен от базата данни

                    break;
                case 4:
                    log.debug("Програмата завърши хода си!");
                    Runtime.getRuntime().exit(0);
                    break;
                default:
                    break;
            }
        }
        catch (IOException e)
        {
            log.log(Level.ERROR, e.getClass() + " " + e.getMessage());
        }

        if (scan != null)
        {
            scan.close();
        }

    }


    private static void choiceWhereToWrite(int selection) throws IOException
    {
        String fileOut;

        log.debug("Въведете тип на изходните данни(txt, xml, dtb):");
        log.debug("[1] Запис в текстов файл");
        log.debug("[2] Запис в xml файл");
        log.debug("[3] Запис в база данни");
        log.debug("[4] Запис в PDF файл");
        log.debug("Избор: ");
        selection = scan.nextInt();

        log.debug("Въведете пътя до изходния файл: ");
        fileOut = scan.next();

        if (selection == 1)
        {

            write = new SourceForUse(new WriteToTXT());
            write.applyWriting(read.wayToRead.map, fileOut);

        }
        else if (selection == 2)
        {
            write = new SourceForUse(new WriteToXML());
            write.applyWriting(read.wayToRead.map, fileOut);
        }
        else if (selection == 3)
        {
            write = new SourceForUse(new WriteToDB());
            write.applyWriting(read.wayToRead.map, fileOut);
        }
        else if (selection == 4)
        {
            write = new SourceForUse(new WriteToPDF());
            write.applyPDFWriting(read.wayToRead.map, fileOut, read.wayToRead.yes, read.wayToRead.no);
        }
        else
        {
            log.debug("Посочете цифра от 1 до 4");
        }

        log.debug("Програмата завърши хода си!");
        log.debug("===========Край============");
    }

}
