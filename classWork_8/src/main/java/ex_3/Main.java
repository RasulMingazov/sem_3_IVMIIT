package ex_3;

import java.sql.*;
import java.time.LocalDate;

public class Main {

    private static String url = "jdbc:postgresql://127.0.0.1:5432/task8_db_pg";
    private static String name = "postgres";
    private static String password = "";

    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        сreateNewTable();
        insertIntoTable();
    }

    private static Connection getConnection() throws SQLException {
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
    Создаем таблицу
    */
    public static void сreateNewTable() throws SQLException {
        try {
        connection.prepareStatement("CREATE TABLE users(id serial primary key, login character varying (50) not null," +
                "password character varying (50) not null,name character varying (50) not null,  birthday date not null, city character varying (50) not null," +
                "address character varying (50) not null, email character varying (50) not null, phone character varying (50) not null)")
        .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }

    /*
    Заполняем таблицу
     */
    public static void insertIntoTable() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO \"users\" (login, password,name,birthday,city,address,email,phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
                preparedStatement.setString(1, "yourboss");
                preparedStatement.setString(2, "1000000");
                preparedStatement.setString(3,"Слава");
                preparedStatement.setDate(4, Date.valueOf(LocalDate.of(1987,7,12)));
                preparedStatement.setString(5,"Москва");
                preparedStatement.setString(6,"Музейная, 54");
                preparedStatement.setString(7,"ggg@gmail.com");
                preparedStatement.setString(8,"89567111111\n" + "89111777777");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "ccccccc");
                preparedStatement.setString(2, "admin");
                preparedStatement.setString(3,"Вася");
                preparedStatement.setDate(4, Date.valueOf(LocalDate.of(1990,5,14)));
                preparedStatement.setString(5,"Москва");
                preparedStatement.setString(6,"Агатова, 7-99");
                preparedStatement.setString(7,"aaa@mail.ru");
                preparedStatement.setString(8,"89567101010");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "cheburashka");
                preparedStatement.setString(2, "gena");
                preparedStatement.setString(3,"Николай");
                preparedStatement.setDate(4, Date.valueOf(LocalDate.of(2000,10,23)));
                preparedStatement.setString(5,"Казань");
                preparedStatement.setString(6,"Шурихина, 95");
                preparedStatement.setString(7,"cheb@mail.com");
                preparedStatement.setString(8,"89567222222\n" + "89222333333");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "igogo");
                preparedStatement.setString(2, "ogogi");
                preparedStatement.setString(3,"Дима");
                preparedStatement.setDate(4, Date.valueOf(LocalDate.of(1987,10,9)));
                preparedStatement.setString(5,"Ижевск");
                preparedStatement.setString(6,"Волкова, 10");
                preparedStatement.setString(7,"gogogo@gmail.com");
                preparedStatement.setString(8,"81111222222");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "bibigon");
                preparedStatement.setString(2, "chu777777");
                preparedStatement.setString(3,"Диляра");
                preparedStatement.setDate(4, Date.valueOf(LocalDate.of(2001,12,30)));
                preparedStatement.setString(5,"Казань");
                preparedStatement.setString(6,"Победы, 61");
                preparedStatement.setString(7,"bibi@inbox.ru");
                preparedStatement.setString(8,"89567777777\n" + "89777111111\n" + "89777222222");
                preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            connection.close();
        }
    }
}

