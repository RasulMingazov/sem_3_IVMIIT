import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Class jFreeClass = Class.forName("org.jfree.chart.JFreeChart");

        Class<?>[] jFreeInterfaces = jFreeClass.getInterfaces();
        System.out.println(Arrays.toString(jFreeInterfaces));

        Field[] fields = jFreeClass.getFields();

        for (Field field: fields) {
            System.out.println("Поле: " + field.getName());
            System.out.println("Тип: " + field.getType().getName());
        }
    }
}
