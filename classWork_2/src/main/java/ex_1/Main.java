package ex_1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Class jFreeClass = Class.forName("org.jfree.chart.JFreeChart");

        printSuperclass(jFreeClass); // имя родительского класса (суперкласса)
        System.out.println("\n");

        printInterfaces(jFreeClass); //список реализуемых интерфейсов
        System.out.println("\n");

        printFieldAndType(jFreeClass); //список полей и их типы
        System.out.println("\n");

        printOnlyStaticFields(jFreeClass); //список только статических полей
        System.out.println("\n");

        printMethods(jFreeClass); // список всех методов с указанием  количества и типов их параметров
        System.out.println("\n");


    }
    static void printSuperclass(Class c) {
        System.out.println("SuperClass - " + c.getSuperclass().getName());
    }

    static void printInterfaces(Class c) {
        Class<?>[] interfaces = c.getInterfaces();
        int i = 0;
        for (Class interfaceO: interfaces) {
            System.out.println("Interface number " + i + " - " + interfaceO.getName());
            i++;
        }
    }

    static void printFieldAndType(Class c) {
        Field[] fields = c.getDeclaredFields();
        int i = 0;
        for (Field field: fields) {
            System.out.println("Field number " + i + " : " + field.getName() + ",   Type: " + field.getType().getName() + ";");
            i++;
        }
    }
    static void printOnlyStaticFields(Class c) {
        Field[] fields = c.getDeclaredFields();
        int i = 0;
        for (Field field: fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                System.out.println("Static field number " + i + " : " + field.getName() + ",   Type: " + field.getType().getName() + ";");
                i++;
            }
        }
    }
    static void printMethods(Class c) {
        Method[] methods = c.getMethods();
        int i = 0;
        for (Method method: methods) {
            Parameter[] parameters = method.getParameters();
            Type[] types = new Type[parameters.length];
            int j = 0;
            for (Parameter parameter: parameters) {
                types[j] = parameter.getType();
                j++;
            }
            System.out.print("Method number " + i + " : " + method.getName());
            System.out.print(",    Quantity of parameters: " + parameters.length);
            System.out.print(",    Types of parameters: ");
            for (Type type: types) {
                System.out.print(type.getTypeName());
            }
            System.out.println();
            i++;
        }
    }
}