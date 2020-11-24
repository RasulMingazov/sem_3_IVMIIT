package ex_5;

import java.sql.*;
import java.time.LocalDate;

public class Main {

    private static String url = "jdbc:postgresql://127.0.0.1:5432/task8_db_pg";
    private static String name = "postgres";
    private static String password = "jean(2001)";

    private static Connection connection = null;

    public static void main(String[] args) {
        connection = getConnection();
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
    1) Всем пользователям из Москвы добавить в имя суффикс ‘_mmm’
     */
    public static void addS() throws SQLException {
        try {
            connection.prepareStatement("UPDATE users SET name = CONCAT ( name, '_mmm') WHERE city = 'Москва'").executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    2)Удалить пользователя из Ижевска
     */
    public static void deleteUserFromIzhevsk() throws SQLException {
        try {
            connection.prepareStatement("UPDATE users SET name = CONCAT ( name, '_mmm') WHERE city = 'Москва'").executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
   3) Добавить пользователя
    */
    public static void addNewUser() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO \"users\" (login, password,name,birthday,city,address,email,phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
            preparedStatement.setString(1, "iminmax ");
            preparedStatement.setString(2, "a0000001");
            preparedStatement.setString(3,"Наташа");
            preparedStatement.setDate(4, Date.valueOf(LocalDate.of(1985,1,4)));
            preparedStatement.setString(5,"Бобруйск");
            preparedStatement.setString(6,"Кушкова, 37");
            preparedStatement.setString(7,"mmm@gmail.com");
            preparedStatement.setString(8,"89567333333");
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }
}

