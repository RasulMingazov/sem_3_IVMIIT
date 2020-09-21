package ex_2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        XYSeries series1 = new XYSeries("cos(x)");
        for (float i = 0; i < 3 * Math.PI; i += 0.1) {
            series1.add(i, Math.cos(i));
        }

        XYSeries series2 = new XYSeries("2sin(x)");
        for (float i = 0; i < 3 * Math.PI; i += 0.1) {
            series2.add(i, 2 * Math.sin(i));
        }

        XYSeries series3 = new XYSeries("-xcosx");
        for (float i = 0; i < 3 * Math.PI; i += 0.1) {
            series3.add(i, -i * Math.cos(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);


        JFreeChart chart = ChartFactory.createXYLineChart("ex_2", "x","y",dataset,
                PlotOrientation.VERTICAL, false, true, true);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,400);

        frame.getContentPane().add(new ChartPanel(chart));
        frame.setVisible(true);
    }
}