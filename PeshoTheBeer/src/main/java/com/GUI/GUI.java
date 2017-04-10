/*
 * GUI.java
 *
 * created at Mar 13, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.GUI;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


public class GUI extends JFrame
{
    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = -6107427898281405734L;

    protected GridBagConstraints gbc = new GridBagConstraints(); // Инициализиране на GridBagConstraints за създаване на GridBagLayout

    protected TitledBorder border; // Бордер за панелите

    protected JButton in, out, start; // Бутони

    protected JPanel panel1, panel2, panel3, panel4, panel5; // Панели

    protected JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12; // Лейбъли

    public static JLabel labelTime;

    protected JComboBox combo;// Комбо

    public static JComboBox comboTranslate; //

    public static JTextField txt1, txt2, txt3, txt4, txt5; // Текстови полета

    public static JPasswordField txt6;

    protected JCheckBox check; // Чек бокс

    protected ButtonGroup radioGroup; // Радио бутони

    public static ButtonGroup filterGroup;

    protected JRadioButton radioTxt, radioDom, radioSax, radioStax, radioJaxb, radioDB; // Радио бутони

    public static JRadioButton radioCost, radioPrice;

    public static Locale local;  // Интернационализация
    public static ResourceBundle localBundle;

    public GUI()
    {

        //Задаване на Български език по-подразбиране
        local = new Locale("bg", "BG");
        localBundle = ResourceBundle.getBundle("AppLanguage", local);
        Locale.setDefault(local);

        // Деклариране на JFrame

        this.setSize(300, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Бирата на Пешо");

        // Панел Входен тип panel1
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(90, 180));
        border = new TitledBorder(localBundle.getString("panel1"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel1.setBorder(border);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 2, 0, 2);
        this.add(panel1, gbc);

        radioTxt = new JRadioButton("TXT");
        radioTxt.setSelected(true);
        radioTxt.setActionCommand("TXT");

        radioDom = new JRadioButton("DOM");
        radioDom.setActionCommand("DOM");

        radioSax = new JRadioButton("SAX");
        radioSax.setActionCommand("SAX");

        radioStax = new JRadioButton("StAX");
        radioStax.setActionCommand("StAX");

        radioJaxb = new JRadioButton("JAXB");
        radioJaxb.setActionCommand("JAXB");

        radioDB = new JRadioButton("DataBase");
        radioDB.setActionCommand("DataBase");

        radioGroup = new ButtonGroup();
        radioGroup.add(radioTxt);
        radioGroup.add(radioDom);
        radioGroup.add(radioSax);
        radioGroup.add(radioStax);
        radioGroup.add(radioJaxb);
        radioGroup.add(radioDB);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(radioTxt, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel1.add(radioDom, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel1.add(radioSax, gbc);
        gbc.gridx = 0;
        gbc.gridy = 15;
        panel1.add(radioStax, gbc);
        gbc.gridx = 0;
        gbc.gridy = 20;
        panel1.add(radioJaxb, gbc);
        gbc.gridx = 0;
        gbc.gridy = 25;
        panel1.add(radioDB, gbc);

        // Панел 2

        // Лейбъл изходен тип
        label1 = new JLabel(localBundle.getString("label1"), JLabel.LEFT);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        this.add(label1, gbc);

        // Комбо бокса
        String[] outputType = {"TXT", "XML", "DB", "PDF"};
        combo = new JComboBox(outputType);
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(7, 1, 0, 5);
        this.setVisible(true);
        this.add(combo, gbc);

        // Панел тип на филтъра panel3
        panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(172, 50));
        border = new TitledBorder(localBundle.getString("panel3"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel3.setBorder(border);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.insets = new Insets(3, 1, 5, 2);
        this.add(panel3, gbc);

        radioCost = new JRadioButton(localBundle.getString("radioCost"));
        radioCost.setActionCommand("разход");
        radioCost.setSelected(true);
        radioPrice = new JRadioButton(localBundle.getString("radioPrice"));
        radioPrice.setActionCommand("цена");

        filterGroup = new ButtonGroup();
        filterGroup.add(radioCost);
        filterGroup.add(radioPrice);

        panel3.add(radioCost);
        panel3.add(radioPrice);

        // Панел филтриране по
        label2 = new JLabel(localBundle.getString("label2"));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(3, 1, 7, 2);
        this.add(label2, gbc);

        // Текстово поле Стойност на филтруване
        txt1 = new JTextField();
        txt1.setText(localBundle.getString("txt1"));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(3, 1, 0, 2);
        this.add(txt1, gbc);

        // Панел от/в

        label3 = new JLabel(localBundle.getString("label3"));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(18, 5, 18, 15);
        this.add(label3, gbc);

        txt2 = new JTextField("16");
        txt2.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(18, 7, 18, 8);
        this.add(txt2, gbc);

        label4 = new JLabel(localBundle.getString("label4"));
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.insets = new Insets(18, 5, 18, 15);
        this.add(label4, gbc);

        txt3 = new JTextField("10");
        txt3.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.insets = new Insets(18, 10, 18, 25);
        this.add(txt3, gbc);

        // Панел Входен файл panel 4
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(275, 90));
        panel4.setLayout(new GridBagLayout());
        border = new TitledBorder(localBundle.getString("panel4"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel4.setBorder(border);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 8;
        gbc.gridheight = 1;
        gbc.insets = new Insets(1, 1, 1, 1);
        this.add(panel4, gbc);

         label5 = new JLabel(localBundle.getString("label5"));
         gbc.gridx = 0;
         gbc.gridy = 0;
         panel4.add(label5);

         label6 = new JLabel(localBundle.getString("label6"));
         label6.setPreferredSize(new Dimension(150,26));
         border = new TitledBorder("");
         label6.setBorder(border);
         gbc.gridx = 1;
         gbc.gridy = 0;
         panel4.add(label6);

         in = new JButton("...");
         in.setToolTipText(localBundle.getString("inToolTip"));
         gbc.gridx = 2;
         gbc.gridy = 0;
         panel4.add(in);

        /* Добавяне на нови текстови полета за вход в базата данни */
        label10 = new JLabel(localBundle.getString("label10"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel4.add(label10, gbc);
        label10.setVisible(false);

        txt4 = new JTextField("jdbc:h2:~/PeshoBira");
        txt4.setPreferredSize(new Dimension(120, 20));
        gbc.gridx = 8;
        gbc.gridy = 0;
        panel4.add(txt4, gbc);
        txt4.setVisible(false);

        label11 = new JLabel("username: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel4.add(label11, gbc);
        label11.setVisible(false);

        txt5 = new JTextField("");
        txt5.setPreferredSize(new Dimension(120, 20));
        gbc.gridx = 8;
        gbc.gridy = 1;
        panel4.add(txt5, gbc);
        txt5.setVisible(false);

        label12 = new JLabel("password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel4.add(label12, gbc);
        label12.setVisible(false);

        txt6 = new JPasswordField();
        txt6.setPreferredSize(new Dimension(120, 20));
        gbc.gridx = 8;
        gbc.gridy = 2;
        panel4.add(txt6, gbc);
        txt6.setVisible(false);

        // Панел Изходен файл panel5

        panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(275, 90));
        panel5.setLayout(new GridBagLayout());
        border = new TitledBorder(localBundle.getString("panel5"));
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        panel5.setBorder(border);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.add(panel5, gbc);

        label7 = new JLabel(localBundle.getString("label7"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel5.add(label7);

        label8 = new JLabel(localBundle.getString("label8"));
        label8.setPreferredSize(new Dimension(150, 26));
        border = new TitledBorder("");
        label8.setBorder(border);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel5.add(label8);

        out = new JButton("...");
        out.setToolTipText(localBundle.getString("outToolTip"));
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel5.add(out);

        // Отваряне на файла след запазване?

        // Чек бокс
        check = new JCheckBox();
        check.setSelected(false);
        check.setText(localBundle.getString("check"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 10, 0, 0);
        this.add(check, gbc);

        // Бутона старт

        start = new JButton(localBundle.getString("start"));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(12, 2, 5, 0);
        this.add(start, gbc);

        labelTime = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        this.add(labelTime, gbc);


        ImageIcon[] flag = {new ImageIcon("img/Bulgaria.png"), new ImageIcon("img/UK.png")};
        comboTranslate = new JComboBox(flag);
        comboTranslate.setPreferredSize(new Dimension(55, 29));
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        this.add(comboTranslate, gbc);

        this.setVisible(true);

    }


    /**
     * @return Returns value of txt4.
     */
    public JTextField getTxt4()
    {
        return txt4;
    }


    /**
     * @param txt4 New value for txt4.
     */
    public void setTxt4(JTextField txt4)
    {
        this.txt4 = txt4;
    }



}
