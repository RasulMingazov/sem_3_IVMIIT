package ex_4;

import java.sql.*;

public class Main {

    private static String url = "jdbc:postgresql://127.0.0.1:5432/task8_db_pg";
    private static String name = "postgres";
    private static String password = "jean(2001)";

    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
//        printUsers();
//        printUsersFromKazan();
//        printUserNameAndAge();
        printNameAndCountOfPhones();
    }

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

    /*
    а) Все данные пользователей аналогично тому, как они представлены в примере
     */
    public static void printUsers() throws SQLException {
       try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"users\"");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

                    System.out.println(resultSet.getInt("id") + "\t" +
                            resultSet.getString("login") + "\t" +
                            resultSet.getString("password") + "\t" +
                            resultSet.getString("name") + "\t" +
                            resultSet.getDate("birthday") + "\t" +
                            resultSet.getString("city") + "\t" +
                            resultSet.getString("address") + "\t" +
                            resultSet.getString("phone") + "\t" +
                            resultSet.getString("email") + "\t");
                }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
           connection.close();
       }
    }
    /*
    б) имена и адреса всех пользователей из Казани
     */
    public static void printUsersFromKazan() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select name, address FROM users WHERE city = \'Казань\'");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name") + "\t" +
                            resultSet.getString("address"));
                }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    в) имя и возраст каждого пользователя
     */
    public static void printUserNameAndAge() throws SQLException {
        try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age(CURRENT_DATE ,birthday) FROM \"users\"");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String age = resultSet.getString("age");
                    System.out.println(name + "\t" + age);
                }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    г) имя и количество телефонов каждого пользователя
     */
    public static void printNameAndCountOfPhones() throws SQLException {
        try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, phone FROM \"users\"");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name") + "\t" + resultSet.getString("phone").split("\n").length);
                }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    д) количество пользователей с электронной почтой от google (на gmail.com)
     */
    public static void printCountOfUsersGmail() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Count(*) as count FROM users WHERE email LIKE '%gmail.com'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }
}

