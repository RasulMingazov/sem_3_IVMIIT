package ex_2;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.Arrays;

public class MainSin {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        XYSeries xySeries;
        Class clazz = Class.forName(XYSeries.class.getName());
        xySeries = (XYSeries) clazz.getConstructor(Comparable.class).newInstance("sin(a)");
        Method[] methods = xySeries.getClass().getMethods();
        Method addMethod = null;
        for (Method method: methods) {
            if (method.getName().equals("add")) {
                Parameter[] parameters = method.getParameters();
                Type[] types = new Type[parameters.length];
                int j = 0;
                for (Parameter parameter: parameters) {
                    types[j] = parameter.getType();
                    j++;
                }
                if (Arrays.equals(types, new Class[]{double.class, double.class})) {
                    addMethod = method;
                    break;
                }
            }
        }
        addMethod.setAccessible(true);
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            addMethod.invoke(xySeries, i, Math.sin(i));

        }
        XYDataset dataset;
        Class dataseriesClass = Class.forName(XYSeriesCollection.class.getName());
        dataset = (XYSeriesCollection) dataseriesClass.getConstructor(XYSeries.class).newInstance(xySeries);
        Method m = ChartFactory.class.getMethod("createXYLineChart", String.class, String.class, String.class, XYDataset.class);
        m.setAccessible(true);
        JFreeChart chart = (JFreeChart) m.invoke(null, "y = sin(x)", "x", "y", dataset);
        JFrame frame = new JFrame("SinChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setVisible(true);
    }
}
