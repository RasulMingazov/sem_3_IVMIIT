package ex_2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;


public class Main {
    private final static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args){
        /*
        Коллекцию java-объектов, полученную в результате выполнения Заданий 2.1.-2.4 из Лабораторной работы 3.3.
        сохранить в json-файл, используя механизм Data binding/mapping какого-либо json-парсера
         */
//        feelJSON(feelSportsmen());

        /*
        Произвести обратное чтение из нового файла и отображение json-данных в java-объекты.
        Вывести их на экран консоли, отсортировав по Имени спортсмена.
         */
        readJson();

        /*
        Изменить настройки отображения и сериализации так, чтобы в json-файл сохранялись только личные данные спортсменов (имя, дата рождения, пол)
        и не сохранялась информация о соревнованиях и их результатах.
        Сохранить такие данные в отдельный json-файл
         */
        feelSelfJSON();
    }

    static void feelSelfJSON() {

        ArrayList<Sportsman> sportsmenAL = readJson();
        ArrayList<SportsmanSelf> sportsmanSelfAL = new ArrayList<>();

        for (Sportsman sportsman : sportsmenAL) {
            sportsmanSelfAL.add(new SportsmanSelf(sportsman.getName(), sportsman.getS(), sportsman.getBirthday()));
        }
        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_2/fileSelf.json").toFile(), sportsmanSelfAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Sportsman> feelSportsmen() {
        ArrayList<Sportsman> sportsmenAL = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_3/src/main/java/ex_2/file.xml"));
            Node root = document.getDocumentElement();
            NodeList sportsmanNL = ((Element) root).getElementsByTagName("sportsman");
            for (int i = 0; i < sportsmanNL.getLength(); i++) {
                Node sportsmanNode = sportsmanNL.item(i);
                NamedNodeMap sportsmenAttr = sportsmanNode.getAttributes();
                Sportsman sportsman = new Sportsman(sportsmenAttr.getNamedItem("name").getNodeValue(),
                        sportsmenAttr.getNamedItem("s").getNodeValue().charAt(0),
                        sportsmenAttr.getNamedItem("birthday").getNodeValue());
                NodeList eventsNL = ((Element) sportsmanNode).getElementsByTagName("event");
                for (int j = 0; j < eventsNL.getLength(); j++) {
                    Node eventNode = eventsNL.item(j);
                    NamedNodeMap eventAttr = eventNode.getAttributes();
                    String place = eventAttr.getNamedItem("place").getNodeValue();
                    int year = Integer.parseInt(eventAttr.getNamedItem("year").getNodeValue());
                    int result = Integer.parseInt(((Element)eventNode).getElementsByTagName("result").item(0).getTextContent());
                    String award = ((Element)eventNode).getElementsByTagName("award").item(0).getTextContent();
                    sportsman.addEvent(place, year, result, award);
                }
                sportsmenAL.add(sportsman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sportsmenAL;
    }

    static void feelJSON(ArrayList<Sportsman> sportsmenAL) {
        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
               writer.writeValue(Paths.get("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_2/file.json").toFile(), sportsmenAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    static ArrayList<Sportsman> readJson() {
        ArrayList<Sportsman> sportsmenAL = new ArrayList<Sportsman>();

        try {
            JsonNode rootArr = mapper.readTree(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_2/file.json"));
            for (JsonNode root : rootArr) {
                Sportsman sportsman = new Sportsman(root.path("name").asText(), root.path("s").asText().charAt(0), root.path("birthday").asText());

                JsonNode eventsArr = root.path("events");
                for (JsonNode event: eventsArr) {
                    sportsman.addEvent(event.path("place").asText(), event.path("year").asInt(), event.path("result").asInt(), event.path("award").asText());
                }
                sportsmenAL.add(sportsman);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(sportsmenAL);
        return sportsmenAL;
    }
}

class SportsmanSelf {
    String name;
    char s;
    String birthday;

    public SportsmanSelf(String name, char s, String birthday) {
        this.name = name;
        this.s = s;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getS() {
        return s;
    }

    public void setS(char s) {
        this.s = s;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

class Sportsman implements Comparable<Sportsman> {
    String name;
    char s;
    String birthday;
    ArrayList<Event> events;

    public Sportsman(String name, char s, String birthday) {
        this.name = name;
        this.s = s;
        this.birthday = birthday;
        this.events = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public char getS() {
        return s;
    }

    public String getBirthday() {
        return birthday;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(String place, int year, int result, String award) {
        events.add(new Event(place, year, result, award));
    }

    @Override
    public String toString() {
        return "name: " + name + "\n" + "sex: " + s + "\n" + "birthday: " + birthday + "\n"
                + events.toString() + "\n";
    }

    @Override
    public int compareTo(Sportsman o) {
        return this.getName().compareTo(o.getName());
    }
}
class Event {
    String place;
    int year;
    int result;
    String award;

    public Event(String place, int year, int result, String award) {
        this.place = place;
        this.year = year;
        this.result = result;
        this.award = award;
    }

    public String getPlace() {
        return place;
    }

    public int getYear() {
        return year;
    }

    public int getResult() {
        return result;
    }

    public String getAward() {
        return award;
    }

    @Override
    public String toString() {
        return "\n" + "place: " + place + "\n" + "year:" + year + "\n" + "result:" + result + "\n"
                + "award: " + award + "\n";
    }
}
