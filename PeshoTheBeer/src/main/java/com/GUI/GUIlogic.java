/*
 * GUIvalues.java
 *
 * created at Mar 14, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.GUI;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.files.SourceForUse;
import com.main.Sources;
import com.readlogic.ReadFromDB;
import com.readlogic.ReadFromTxtFile;
import com.strategy.XMLStrategy;
import com.writelogic.WriteToDB;
import com.writelogic.WriteToPDF;
import com.writelogic.WriteToTXT;
import com.writelogic.WriteToXML;


public class GUIlogic extends GUI
{

    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = 6835510252903491792L;

    private static final Logger log = Logger.getLogger(GUI.class);

    private Sources read = null;
    private Sources write = null;
    private FileNameExtensionFilter filterIn, filterOut = null;
    private JFileChooser fileChooserIn, fileChooserOut = null;
    private File fileIn, fileOut = null;
    private Desktop desktop = null;

    private String inSource;
    private String comboOutSource;
    private String txtType;
    private int from, to;

    public void enterGUI()
    {

        log.info("========== Начало =============");

        fileChooserIn = new JFileChooser();
        fileChooserOut = new JFileChooser();

        filterIn = new FileNameExtensionFilter("Входен файл(txt, xml)", "txt", "xml");
        filterOut = new FileNameExtensionFilter("Изходен файл(txt, xml, pdf)", "txt", "xml", "pdf");

        fileChooserIn.setFileFilter(filterIn);
        fileChooserIn.setDialogTitle("Изберете входен файл");
        fileChooserOut.setFileFilter(filterOut);
        fileChooserOut.setDialogTitle("Изберете изходен файл");

        in.addActionListener(new ActionListener() // Бутона за избор на входен файл
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileChooserIn.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    setFileIn(fileChooserIn.getSelectedFile());
                    label6.setText(getFileIn().getName());
                    label6.setToolTipText(getFileIn().getAbsolutePath());
                    log.info("Входен файл: " + getFileIn().getAbsolutePath());
                }
            }
        });

        out.addActionListener(new ActionListener() // Бутона за избор на изходен файл
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileChooserOut.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    setFileOut(fileChooserOut.getSelectedFile());
                    label8.setText(getFileOut().getName());
                    label8.setToolTipText(getFileOut().getAbsolutePath());
                    log.info("Изходен файл: " + getFileOut().getAbsolutePath());
                }
            }
        });

        start.addActionListener(new ActionListener() // Старт бутона
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setInSource(radioGroup.getSelection().getActionCommand());
                setComboOutSource(combo.getSelectedItem().toString());
                setTxtType(txt1.getText());
                setFrom(Integer.parseInt(txt2.getText()));
                setTo(Integer.parseInt(txt3.getText()));

                try
                {
                    if (txt1.getText() == null || !txt1.getText().equals(localBundle.getString("inputTxtValue")))
                    {

                        if (getInSource().equalsIgnoreCase("txt"))
                        {
                            read = new SourceForUse(new ReadFromTxtFile());
                            read.applyReading(getTxtType(), getFileIn().getAbsolutePath(), getFrom(), getTo());
                        }
                        else if (getInSource().equalsIgnoreCase("dom") || getInSource().equalsIgnoreCase("stax") || getInSource().equalsIgnoreCase("sax") || getInSource().equalsIgnoreCase("jaxb"))
                        {
                            read = new SourceForUse(new XMLStrategy().readXMLgui(getInSource()));
                            read.applyReading(getTxtType(), getFileIn().getAbsolutePath(), getFrom(), getTo());
                        }
                        else if (getInSource().equalsIgnoreCase("DataBase"))
                        {
                            read = new SourceForUse(new ReadFromDB());
                            read.applyReading(getTxtType(), "", getFrom(), getTo());
                        }
                        writeToDifferentSources();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Попълнете полето /Стойност на филтриране/");
                    }

                    log.debug("Четене от: " + getInSource() + " Запис в: " + getComboOutSource() + " Тип разход: " + getTxtType());
                    log.info("От: " + getInSource() + " До: " + getComboOutSource() + " Тип разход: " + getTxtType());
                    log.info("========== Край =============");
                }
                catch (IOException e2)
                {
                    e2.printStackTrace();
                    log.log(Level.ERROR, e2.getClass() + " " + e2.getMessage() + " " + e2);
                    log.log(Level.DEBUG, e2.getClass() + " " + e2.getMessage());

                }
                catch (NullPointerException e3)
                {

                    log.log(Level.ERROR, e3.getClass() + " " + e3.getMessage() + " " + e3);
                    log.log(Level.DEBUG, e3.getClass() + " " + e3.getMessage());
                    JOptionPane.showMessageDialog(null, "Изберете входящ и изходящ файл");
                }
            }

        });

        radioDB.addItemListener(new ItemListener() // Disable панела входен файл, ако радио бутона DataBase е натиснат
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {

                if (e.getStateChange() == 1)
                {
                    for (Component c : panel4.getComponents())
                    {
                        c.setVisible(false);

                        label10.setVisible(true);
                        txt4.setVisible(true);
                        label11.setVisible(true);
                        txt5.setVisible(true);
                        label12.setVisible(true);
                        txt6.setVisible(true);

                        border = new TitledBorder(localBundle.getString("dataBaseLogin"));
                        panel4.setBorder(border);
                    }
                }
                else
                {
                    for (Component c : panel4.getComponents())
                    {
                        c.setVisible(true);

                        label10.setVisible(false);
                        txt4.setVisible(false);
                        label11.setVisible(false);
                        txt5.setVisible(false);
                        label12.setVisible(false);
                        txt6.setVisible(false);

                        border = new TitledBorder("Входен файл");
                        panel4.setBorder(border);
                    }
                }
            }
        });

        combo.addItemListener(new ItemListener() // Disable панела изходен файл, ако радио DB от комбо бокса е селектиран
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (combo.getSelectedItem().toString().equalsIgnoreCase("DB"))
                {
                    for (Component c : panel5.getComponents())
                    {
                        c.setEnabled(false);
                    }
                }
                else
                {
                    for (Component c : panel5.getComponents())
                    {
                        c.setEnabled(true);
                    }
                }

            }
        });

        comboTranslate.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {

                if (comboTranslate.getSelectedItem().toString().equalsIgnoreCase("img/UK.png"))
                {

                    local = new Locale("en", "UK");
                    localBundle = ResourceBundle.getBundle("AppLanguage", local);
                    setTranslation();
                }

                if (comboTranslate.getSelectedItem().toString().equalsIgnoreCase("img/Bulgaria.png"))
                {

                    localBundle = ResourceBundle.getBundle("AppLanguage", Locale.getDefault());
                    setTranslation();
                }

            }
        });
        clock();
    }


    private void chooseWhereToWriteGUI(Map<String, String> m, String file, float yes, float no) throws IOException // Метод за посочване на
                                                                                                                   // логиката за запис на
    // изходните данни
    {
        setComboOutSource(combo.getSelectedItem().toString());

        if (getComboOutSource().equalsIgnoreCase("TXT"))
        {
            write = new SourceForUse(new WriteToTXT());
            write.applyWriting(m, file);
            openResultFile();
        }
        else if (getComboOutSource().equalsIgnoreCase("XML"))
        {
            write = new SourceForUse(new WriteToXML());
            write.applyWriting(m, file);
            openResultFile();
        }
        else if (getComboOutSource().equalsIgnoreCase("DB"))
        {
            write = new SourceForUse(new WriteToDB());
            write.applyWriting(m, "");
        }
        else if (getComboOutSource().equalsIgnoreCase("PDF"))
        {
            write = new SourceForUse(new WriteToPDF());
            write.applyPDFWriting(m, file, yes, no);
            openResultFile();
        }
    }


    private void writeToDifferentSources() throws IOException // Логика за запис в различни източници (DB, XML)
    {

        if (combo.getSelectedItem().toString().equalsIgnoreCase("DB"))
        {
            chooseWhereToWriteGUI(read.wayToRead.map, "", read.wayToRead.yes, read.wayToRead.no); // Метод за посочване на логиката
            // за запис на изходните данни
        }
        else if (!combo.getSelectedItem().toString().equalsIgnoreCase("DB"))
        {
            chooseWhereToWriteGUI(read.wayToRead.map, getFileOut().getAbsolutePath(), read.wayToRead.yes, read.wayToRead.no);
        }
    }


    private void openResultFile() throws IOException // Отваряне на файла след извършване на прочит и запис
    {

        desktop = Desktop.getDesktop();

        if (getFileOut().exists() && check.isSelected())
        {
            desktop.open(fileOut);
        }
    }


    private void setTranslation() // Метод прилагащ превода
    {

        border = new TitledBorder(localBundle.getString("panel1"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel1.setBorder(border);

        label1.setText(localBundle.getString("label1"));

        border = new TitledBorder(localBundle.getString("panel3"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel3.setBorder(border);

        radioCost.setText(localBundle.getString("radioCost"));
        radioPrice.setText(localBundle.getString("radioPrice"));
        label2.setText(localBundle.getString("label2"));
        txt1.setText(localBundle.getString("txt1"));
        label3.setText(localBundle.getString("label3"));
        label4.setText(localBundle.getString("label4"));

        border = new TitledBorder(localBundle.getString("panel4"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel4.setBorder(border);

        label5.setText(localBundle.getString("label5"));
        label6.setText(localBundle.getString("label6"));
        in.setToolTipText(localBundle.getString("inToolTip"));
        label10.setText(localBundle.getString("label10"));

        border = new TitledBorder(localBundle.getString("panel5"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel5.setBorder(border);

        label7.setText(localBundle.getString("label7"));
        label8.setText(localBundle.getString("label8"));
        out.setToolTipText(localBundle.getString("outToolTip"));
        check.setText(localBundle.getString("check"));
        start.setText(localBundle.getString("start"));
    }




    public void clock() // Часовника
    {
        Thread clock = new Thread("Clock")

        {

            public void run()
            {
                try
                {
                    while (true)
                    {
                        Calendar calendar = new GregorianCalendar();

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);

                        int year = calendar.get(Calendar.YEAR);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);

                        labelTime.setText(hour + ":" + minute + ":" + second + " / " + day + "." + month + "." + year);

                        sleep(1000);
                    }
                }
                catch (IllegalMonitorStateException | InterruptedException e)
                {
                    e.printStackTrace();
                    log.log(Level.ERROR, e.getClass() + " " + e.getMessage() + " " + e);
                    log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
                }
            }
        };

        clock.setPriority(10);
        clock.start();

    }


    public String getInSource()
    {
        return inSource;
    }


    public void setInSource(String inSource)
    {
        this.inSource = inSource;
    }


    public String getComboOutSource()
    {
        return comboOutSource;
    }


    public void setComboOutSource(String comboOutSource)
    {
        this.comboOutSource = comboOutSource;
    }


    public String getTxtType()
    {
        return txtType;
    }


    public void setTxtType(String txtType)
    {
        this.txtType = txtType;
    }


    public File getFileIn()
    {
        return fileIn;
    }


    public void setFileIn(File fileIn)
    {
        this.fileIn = fileIn;
    }


    public File getFileOut()
    {
        return fileOut;
    }


    public void setFileOut(File fileOut)
    {
        this.fileOut = fileOut;
    }


    public int getFrom()
    {
        return from;
    }


    public void setFrom(int from)
    {
        this.from = from;
    }


    public int getTo()
    {
        return to;
    }


    public void setTo(int to)
    {
        this.to = to;
    }

}
