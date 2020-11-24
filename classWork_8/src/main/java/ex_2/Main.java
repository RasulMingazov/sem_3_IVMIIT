package ex_2;

import ex_2.pojo.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static String url = "jdbc:postgresql://127.0.0.1:5432/task8_db_pg";
    private static String name = "postgres";
    private static String password = "";

    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        printAllCities();
//        printFiveTheLargest();
//        printSortedCityNames();
//        printSumOfPopulation();
//        insertNewCities();
//        printCitiesMoreThanFourMillion();
//        printCitiesLikeM();
//        printCitiesDoubleWorlds();
    }


    /*
    1) организовать подключение к созданной базе данных
     */
    private static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private static ResultSet getResultSet(String code) throws SQLException {
        try {
            return connection.prepareStatement(code).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
        return null;
    }

    /*
    2) из таблицы городов вывести на консоль
    а) все данные
     */
    public static void printAllCities() throws SQLException {
        try {
            ResultSet resultSet = getResultSet("SELECT * FROM \"cities\"");
            while (resultSet.next()) {
                System.out.println(
                        new City(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("population")).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    б) 5 городов с наибольшей численностью населения
     */
    public static void printFiveTheLargest() throws SQLException {
        try {
            ResultSet resultSet = getResultSet("SELECT * FROM \"cities\" ORDER BY \"population\" DESC LIMIT 5");

            while (resultSet.next()) {
                System.out.println(
                        new City(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("population")).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    в) все названия городов в алфавитном порядке
     */
    public static void printSortedCityNames() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM \"cities\"ORDER BY \"name\" ");
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name"));
                }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

   /*
   г) общую суммарную численность населения
    */
    public static void printSumOfPopulation() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT SUM(population) FROM \"cities\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Summary population is " + resultSet.getString("sum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    3) программно в таблицу городов добавить 5 мировых столиц
     */
    public static void insertNewCities() throws SQLException {
      try {
          PreparedStatement preparedStatement = connection.prepareStatement(
                  "INSERT INTO \"cities\" (name, population) VALUES (?, ?);"
          );
          preparedStatement.setString(1, "Таллин");
          preparedStatement.setDouble(2, 426538);
          preparedStatement.executeUpdate();

          preparedStatement.setString(1, "Вашингтон");
          preparedStatement.setDouble(2, 705749);
          preparedStatement.executeUpdate();

          preparedStatement.setString(1, "Оттава");
          preparedStatement.setDouble(2, 994837);
          preparedStatement.executeUpdate();

          preparedStatement.setString(1, "Вена");
          preparedStatement.setDouble(2, 1897000);
          preparedStatement.executeUpdate();

          preparedStatement.setString(1, "Канберра");
          preparedStatement.setDouble(2, 395790);
          preparedStatement.executeUpdate();
      } catch (Exception e) {
          e.printStackTrace();
          System.exit(0);
      } finally {
          connection.close();
      }

    }

    /*
    4) для измененных данных вывести
    а) количество городов с населением более 4 млн. человек
     */
    public static void printCitiesMoreThanFourMillion() throws SQLException {
      try {
          PreparedStatement preparedStatement = connection.prepareStatement(
                  "SELECT COUNT(*) FROM \"cities\" WHERE \"population\" > 4000000");
          ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("count")); }
      } catch (Exception e) {
          e.printStackTrace();
          System.exit(0);
      } finally {
          connection.close();
      }
    }

    /*
    б) все города на букву ‘М’
     */
    public static void printCitiesLikeM() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"cities\" where name LIKE \'М%\'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(
                        new City(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("population")).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    в) названия городов, состоящие из двух слов
     */
    public static void printCitiesDoubleWorlds() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name from \"cities\" WHERE name like \'% %\'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("name = " + resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }
}

