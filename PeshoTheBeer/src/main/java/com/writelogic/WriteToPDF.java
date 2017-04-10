/*
 * WriteToPDF.java
 *
 * created at Mar 9, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.writelogic;


import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import com.GUI.GUI;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.main.PdfChartHelper;


public class WriteToPDF extends PdfChartHelper
{
    private final Logger log = Logger.getLogger(WriteToPDF.class);

    OutputStream out = null;
    PdfWriter pdfWriter = null;
    Document doc = null;
    public static final String FONT = "Fonts/FreeSans.ttf";


    public WriteToPDF()
    {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void writeWith(Map<String, String> map, String file, float yes, float no) throws IOException
    {
        try
        {

            fileOut = new File(file);

            int width = 640; // Ширината на графиката
            int height = 480; // Височината на графиката
            doc = new Document(new Rectangle(width, height));

            out = new FileOutputStream(fileOut, true);
            pdfWriter = PdfWriter.getInstance(doc, out);

            doc.open();

            BaseFont bf = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
            Font bul = new Font(bf, 12);

            /******** Графиката 3D ***********/

            DefaultPieDataset dataset = new DefaultPieDataset();
            // dataset.setValue(("Прочетени: "+(yes+no)), (yes+no));
            dataset.setValue((GUI.localBundle.getString("validated") + (yes)), yes);
            dataset.setValue((GUI.localBundle.getString("missed") + (no)), no);

            JFreeChart chart = ChartFactory.createPieChart3D(GUI.localBundle.getString("chartTitle"), dataset, true, true, true);
            // chart.setBackgroundPaint(new Color(0, 0, 0, 100));

            PiePlot3D plot = (PiePlot3D)chart.getPlot();
            plot.setStartAngle(270);
            plot.setForegroundAlpha(0.60f);
            plot.setInteriorGap(0.02);
            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}")); // процентно съотношение в легендата
            plot.setNoDataMessage(GUI.localBundle.getString("noResultsChart"));
            plot.setBackgroundPaint(Color.WHITE);
            plot.setSectionPaint(0, Color.green);
            plot.setSectionPaint(1, Color.ORANGE);

            File pieChart3D = new File("img/outputChart.jpeg");
            ChartUtilities.saveChartAsJPEG(pieChart3D, chart, width, height);

            Image img = Image.getInstance("img/outputChart.jpeg");
            img.setAbsolutePosition(140f, 210f);
            img.scaleAbsolute(340f, 260f);
            doc.add(img);
            for (int i = 0; i < 14; i++)
            {
                doc.add(new Paragraph(System.lineSeparator())); // Добавяне на нов ред за да се измести текста под изображението
            }

            /******** Графика 3D Край ***********/

            for (Map.Entry<String, String> m : map.entrySet())
            {

                doc.add(new Paragraph(GUI.localBundle.getString("costValue") + m.getKey() + "  " + GUI.localBundle.getString("valueValue") + m.getValue() + System.lineSeparator(), bul));
                log.info("Записани стойности: Разход- " + m.getKey() + " Стойност: " + m.getValue());
            }

            doc.add(new Paragraph("\n" + GUI.localBundle.getString("readValues") + (yes + no) + "\n" + GUI.localBundle.getString("yesValues") + yes + "(" + (yes * 100) / (yes + no) + "%)\n" + GUI.localBundle.getString("noValues") + no + "(" + (no * 100) / (yes + no) + "%)", bul));
            doc.add(new Paragraph(GUI.localBundle.getString("dateTime") + GUI.labelTime.getText()));
            /******** Графиката 1 ***********/

            // DefaultPieDataset dataset = new DefaultPieDataset();
            // dataset.setValue("Accept", yes);
            // dataset.setValue("Disabled", no);
            //
            // JFreeChart chart = ChartFactory.createPieChart("Разходите на Пешо", dataset, true, true, false);
            //
            // PdfContentByte content = pdfWriter.getDirectContent();
            // PdfTemplate template = content.createTemplate(width, height);
            //
            // Graphics2D graphics2D = template.createGraphics(width, height, new DefaultFontMapper());
            // Rectangle2D rectangle2D = new Rectangle2D.Double(140, 250, 320, 220);
            //
            // chart.draw(graphics2D, rectangle2D);
            // graphics2D.dispose();
            // content.addTemplate(template, 0, 0);

            /******** Графика 1 Край ***********/

            log.info("Запис на данни в PDF файл в Директория" + fileOut.getPath());
            log.debug("PDF файл в Директория " + fileOut.getAbsolutePath());
            log.debug("Yes: " + yes + " No: " + no);

        }
        catch (FileNotFoundException | NullPointerException | DocumentException e)
        {
            log.log(Level.DEBUG, e.getClass() + " " + e.getMessage());
        }
        finally
        {

            if (doc != null)
            {
                doc.close();
            }
            if (out != null)
            {
                out.close();
            }
            if (pdfWriter != null)
            {
                pdfWriter.close();
            }
        }
    }

}
