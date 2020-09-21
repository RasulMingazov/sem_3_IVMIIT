package ex_3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("ex_3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 500);
        JTabbedPane tabbedPane = new JTabbedPane();


        tabbedPane.addTab("Рост - Вес", heightAndWeightPanel());
        tabbedPane.addTab("Расходы Пети", PetyaPaymentsPanel());
        tabbedPane.addTab("Погода", weatherDuringDay());
        frame.add(tabbedPane);
        frame.setVisible(true);
    }


    static JPanel heightAndWeightPanel() {
        String[] header = {"Фамилия", "Рост", "Вес"};
        String[][] allKids = {
                {"Шишов", "142", "41"},
                {"Мокроносов", "168", "60"},
                {"Кулебякина", "150", "44"},
                {"Букоедов", "180", "75"}
        };
        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        dataset.addValue(Integer.parseInt(allKids[0][1]), header[1], allKids[0][0]);
        dataset.addValue(Integer.parseInt(allKids[1][1]), header[1], allKids[1][0]);
        dataset.addValue(Integer.parseInt(allKids[2][1]), header[1], allKids[2][0]);
        dataset.addValue(Integer.parseInt(allKids[3][1]), header[1], allKids[3][0]);

        dataset.addValue(Integer.parseInt(allKids[0][2]), header[2], allKids[0][0]);
        dataset.addValue(Integer.parseInt(allKids[1][2]), header[2], allKids[1][0]);
        dataset.addValue(Integer.parseInt(allKids[2][2]), header[2], allKids[2][0]);
        dataset.addValue(Integer.parseInt(allKids[3][2]), header[2], allKids[3][0]);

        JFreeChart freeChart = ChartFactory.createBarChart("","",
                "", dataset, PlotOrientation.VERTICAL, true, true,false);
        ChartPanel chartPanel = new ChartPanel(freeChart);

        JTable table = new JTable(allKids, header);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        JPanel panelL = new JPanel();
        panelL.add(scrollPane, BorderLayout.WEST);
        panelL.add(chartPanel, BorderLayout.EAST);
        panel.add(panelL);

        return panel;
    }

    static JPanel PetyaPaymentsPanel() {
        String[] header = {"Виды расходов", "Размер, руб."};
        String[][] allP = {
                {"Питание", "2000"},
                {"Одежда","3500"},
                {"Транспорт","1000"},
                {"Жилье","1500"},
                {"Развлечение","500"}
        };

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue(allP[0][0],Integer.parseInt(allP[0][1]));
        dataset.setValue(allP[1][0],Integer.parseInt(allP[1][1]));
        dataset.setValue(allP[2][0],Integer.parseInt(allP[2][1]));
        dataset.setValue(allP[3][0],Integer.parseInt(allP[3][1]));
        dataset.setValue(allP[4][0],Integer.parseInt(allP[4][1]));


        JFreeChart freeChart = ChartFactory.createPieChart("", dataset);
        ChartPanel chartPanel = new ChartPanel(freeChart);


        JTable table = new JTable(allP, header);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        JPanel panelL = new JPanel();
        panelL.add(scrollPane, BorderLayout.WEST);
        panelL.add(chartPanel, BorderLayout.EAST);
        panel.add(panelL);

        return panel;
    }
    static JPanel weatherDuringDay() {
        String[] header = {"Время", "Температура"};
        String[] tems = {"-10","-12","-12","-13","-14","-11","-11","-7","-6","-7","-5","-4","-1","1","4",
                "2","3","0","-1","-1","-3","-2","-2","-4"};
        String[][] allT = new String[tems.length][2];

        for (int i = 0; i < tems.length; i++) {
            allT[i][0] = i + ":00";
            allT[i][1] = tems[i];
        }

        TimeSeries series = new TimeSeries("");
        for (int i = 0; i < tems.length; i++) {
            series.add(new Minute(0, i, 1, 1, 1999), Integer.parseInt(tems[i]));
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        JFreeChart freeСhart = ChartFactory.createTimeSeriesChart("",
                "Время",
                "Температура",
                dataset, true, true, false);

        XYPlot plot = (XYPlot) freeСhart.getPlot();
        plot.setBackgroundPaint    (Color.lightGray);
        plot.setDomainGridlinePaint(Color.white    );
        plot.setRangeGridlinePaint (Color.white    );
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible   (true);
            renderer.setBaseShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH"));

        ChartPanel chartPanel = new ChartPanel(freeСhart);

        JTable table = new JTable(allT, header);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        JPanel panelL = new JPanel();
        panelL.add(scrollPane, BorderLayout.WEST);
        panelL.add(chartPanel, BorderLayout.EAST);
        panel.add(panelL);

        return panel;

    }
}
