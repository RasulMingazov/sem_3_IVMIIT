package ex_4;

import java.sql.*;
import java.time.LocalDate;
public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        String url = "jdbc:postgresql://127.0.0.1:5432/firstDB";
        String name = "postgres";
        String password = "Когда:\n";

        String[] names = {"Шариков Гавриил", "Ежиков Леопольд", "Живодерова Беатриса", "Кошкин Гордей", "Куроедов Андрей", " Лошакин Сидор",
        "Мухобоев Пахом", "Мымриков Даниил", "Обжоркин Рубен", "Перышкин Панкрат", "Пипеткин Рудольф", "Пыжиков Сергей", "Совков Фрол",
        "Сушкин Кузьма", "Тюленькина Ольга", "Чижиков"};
        int[] years = {1971, 1981,1963,1954,1973,1978,1948,1945,1965,1977,1967,1967,1962,1951,1959,1979};
        int[] mouths = {11,3,12,4,10,2,2,7,1,9,6,5,12,10,8,10};
        int[] days = {16,6,13,21,23,28,17,30,16,27,14,12,25,26,14,1};
        LocalDate[] dates = new LocalDate[16];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = LocalDate.of(years[i],mouths[i],days[i]);
        }
        String[] positions = {"Зам.директ ора","Рабочий","Ботаник","Электрик","Орнитолог", "Конюх", "Энтомолог",
        "Директор","Рабочий","Инженер","Вет.врач","Зоолог","Кассир","Плотник","Зоолог","Рабочий"};

        int[] offers = {200000,30000,50000,40000,55000,100000,35000,300000,15000,70000,75000,60000,50000,45000,60000,37000};

        String[] notes = {"Отслужив в ВДВ, приобрел полезные навыки по работе с клиентами. Прослушал спецкурс по европейской торговле при Институте Международных Отношений.",
        null, "Очень хорошая сотрудница - старательная, добрая, отзывчивая, технически грамотная и во всех отношениях аккуратная. Еще не было ни одного начальника, который был бы хоть чем-то недоволен...",
        null,null,"Профессиональный психолог. Занимается анализом работы трудового коллектива и вопросами оптимизации его работы.",
        "Окончил с отличием биологический факультет Московского университета. Защитил кандидатскую диссертацию",
        "В 1995 г. защитил докторскую диссертацию. Работал в Министерстве на различных должностях вплоть до зам.министра. Огромный опыт. Говорит по-английски, по-франзузски, по- китайски и понимает по-японски.",
        null,"Окончил Ташкентский университет и Лондонский университет. В течении двух лет проходил стажировку в Японии, в результате чего прекрасно выучил японский язык.",
        null,"Выпускник Московского Университета, Андрей долгое время занимался вопросами химического анализа пищи, опубликовал несколько статей и книг по данному вопросу.",
        null,null,"В 1988 г. закончила Киевский университет по специальности \"Зоология\". Окончила специальные курсы",
        "Окончил Техникум Пищевой промышленности в 1999 г. Отличается исключительны"};

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, name, password);


            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into employees(name, birthday, position, offer, notes) values(?,?,?,?,?)");
            for (int i = 0; i < 16; i++) {
                preparedStatement.setString(1,names[i]);
                preparedStatement.setDate(2, Date.valueOf(dates[i]));
                preparedStatement.setString(3,positions[i]);
                preparedStatement.setInt(4,offers[i]);
                preparedStatement.setString(5, notes[i]);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}