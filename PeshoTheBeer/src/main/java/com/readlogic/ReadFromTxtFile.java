/*
 * ReadTxtFileWithInputStream.java
 *
 * created at Mar 1, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.readlogic;


import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.GUI.GUI;
import com.main.WayToRead;
import com.validation.FileValidator;


public class ReadFromTxtFile extends WayToRead
{
    private static final Logger log = Logger.getLogger(ReadFromTxtFile.class);

    Scanner scanText = null;
    final FileValidator validator = new FileValidator();
    private boolean flag = false;

    public ReadFromTxtFile()
    {

    }


    @Override
    public void readWith(String type, String file, int from, int to)
    {
        try
        {
            fileIn = new File(file); // TODO change me

            log.debug("Логика от Класа /ReadFromTxtFile/ за посочен разход ==> " + type);
            log.info("Извърши се прочитане на текстов файл " + fileIn.getPath() + " за посочен разход  " + type);

            scanText = new Scanner(fileIn, "UTF-8"); // Отваряне на Scanner необходим да се сканира текста линия по линия

            yes = 0;
            no = 0;
            String col[];
            String typeFromText, valueFromText;


            while (scanText.hasNextLine()) // Четене линия по-линия
            {

                String line = scanText.nextLine(); // Прочитане на текста от всяка линия и обработката му (използване на split)

                col = line.split(" ", 2);
                typeFromText = col[0];
                valueFromText = col[1];

                try
                {
                    String radioFilterType = GUI.filterGroup.getSelection().getActionCommand();
                    validNumber = validator.isValid(valueFromText, from, to);
                    switch (radioFilterType)
                    {
                        case "разход":

                            if (typeFromText.equalsIgnoreCase(type)) // Работа само с посочения от конзолата тип
                            {

                                if (validNumber != null)
                                {
                                    setTyp(typeFromText);
                                    setVal(validNumber);

                                    this.map.put(typeFromText + "", validNumber.toUpperCase());

                                    log.debug("Прочетена стойност: " + validNumber.toUpperCase());
                                    log.info("Прочетена стойност: " + validNumber.toUpperCase());

                                    yes++;
                                    flag=true;
                                }
                                else if (validNumber == null)
                                {
                                    // log.debug("Пропуснати стойности: " + " Индекс: " + " [Оригинал: " + valueFromText + "]");
                                    no++;
                                }
                            }
                            break;

                        case "цена":
                            if (valueFromText.equalsIgnoreCase(type)) // Работа само с посочения от конзолата тип
                            {
                                setTyp(typeFromText);
                                setVal(validNumber);

                                this.map.put(typeFromText + "", validNumber.toUpperCase());

                                log.debug("Прочетена стойност: " + validNumber.toUpperCase());
                                log.info("Прочетена стойност: " + validNumber.toUpperCase());

                                flag=true;
                            }
                        default:
                            break;
                    }
                }
                catch (NullPointerException n)
                {

                }

            }

            if(flag==false)
            {
                JOptionPane.showMessageDialog(null, "Търсената от Вас стойност не фигурира в този файл");
            }

            setYes(yes);
            setNo(no);
            // log.debug("Yes: " + getYes() + " No: " + getNo());

        }
        catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException | IOException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            if (scanText != null)
            {
                scanText.close();
            }
        }

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
    public void setTyp(String t)
    {
        this.typ = t;

    }


    @Override
    public void setVal(String val)
    {
        this.val = val;

    }


    @Override
    public Float getYes()
    {
        return yes;
    }


    @Override
    public Float getNo()
    {

        return no;
    }


    @Override
    public void setYes(float y)
    {
        this.yes = y;

    }


    @Override
    public void setNo(float n)
    {
        this.no = n;

    }

}
