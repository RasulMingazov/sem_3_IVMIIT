package ex_2;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
         /*
         Читаем все данные из xml-файла и размещаем их в коллекцию объектов Спортсмен.
         */
        ArrayList<Sportsman> sportsmenAL = feelSportsmen();
        /*
        Выводим на экран:
         */
        System.out.println("---------------------");
        System.out.println("Имена и даты рождения всех мужчин: " + "\n");
        printMenNamesAndBirthdays(sportsmenAL);
        System.out.println("---------------------");

        System.out.println("Имена, даты рождения и количество медалей у женщин старше 1985 г.р:" + "\n");
        printWomenNamesBirthdaysAndQofAwards(sportsmenAL);
        System.out.println("---------------------");
        System.out.println("Имена и результаты спортсменов, участвовавших в 2002 в соревнованиях в Москве:" + "\n");
        printSportsmenWhichParticipatedIn(sportsmenAL);
        /*
        Запросить у пользователя, ввести с консоли и добавить в коллекцию данные еще одного спортсмена
         */
        System.out.println("Введите данные спортсмена");
        sportsmenAL.add(addNewSportsman());
        /*
         Любым способом создать новых xml-файл,хранящий для каждого спортсмена только  имя,
         количество проведенных  соревнованиии, сумм всех его очков
         */
        createNewDocument(sportsmenAL);
    }

    static void createNewDocument(ArrayList<Sportsman> sportsmenAL) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("team");
            document.appendChild(root);
            for (int i = 0; i < sportsmenAL.size(); i++) {
                Element sportsman = document.createElement("sportsman");
                sportsman.setAttribute("name", sportsmenAL.get(i).getName());
                root.appendChild(sportsman);
                ArrayList<Event> eventsAL = sportsmenAL.get(i).getEvents();
                Element quantityOfEvents = document.createElement("quantityOfEvents");
                quantityOfEvents.appendChild(document.createTextNode(String.valueOf(eventsAL.size())));
                sportsman.appendChild(quantityOfEvents);
                int sum = 0;
                for (int j = 0; j < eventsAL.size(); j++) {
                    sum+= eventsAL.get(j).getResult();
                }
                Element sumOfResults = document.createElement("sumOfResults");
                sumOfResults.appendChild(document.createTextNode(String.valueOf(sum)));
                sportsman.appendChild(sumOfResults);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_3/src/main/java/ex_2/output.xml"));
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Sportsman addNewSportsman() {
        Scanner inp = new Scanner(System.in);
        System.out.println("Name: ");
        String name = inp.nextLine();
        System.out.println("Birthday (year-month-day): ");
        String[] birthdayStr = inp.nextLine().split("-");
        LocalDate birthday = LocalDate.of(Integer.parseInt(birthdayStr[0]), Integer.parseInt(birthdayStr[1]), Integer.parseInt(birthdayStr[2]));
        System.out.println("Sex: ");
        char s = inp.nextLine().charAt(0);
        Sportsman sportsman = new Sportsman(name, s, birthday);
        System.out.println("Quantity of events: ");
        int quantityOfEvents = inp.nextInt();
        for (int i = 0; i < quantityOfEvents; i++) {
            System.out.println("Place: ");
            String place = inp.next();
            System.out.println("Year: ");
            int year = inp.nextInt();
            System.out.println("Result: ");
            int result = inp.nextInt();
            System.out.println("Award: ");
            String award = inp.next();
            sportsman.addEvent(place, year, result, award);
        }
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentFactory.newDocumentBuilder();

            Document document = builder.parse(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_3/src/main/java/ex_2/file.xml"));
            Node root = document.getDocumentElement();

            Element sportsmanEl = document.createElement("sportsman");
            sportsmanEl.setAttribute("name", sportsman.getName());
            sportsmanEl.setAttribute("s", Character.toString(sportsman.getS()));
            sportsmanEl.setAttribute("birthday", sportsman.getBirthday().getYear() + "-" + sportsman.getBirthday().getMonthValue()
                    + "-" + sportsman.getBirthday().getDayOfMonth());
            ArrayList<Event> eventsAL = sportsman.getEvents();
            for (int i = 0; i < eventsAL.size(); i++) {
                Element event = document.createElement("event");
                event.setAttribute("place", eventsAL.get(i).getPlace());
                event.setAttribute("year", String.valueOf(eventsAL.get(i).getYear()));
                sportsmanEl.appendChild(event);

                Element award = document.createElement("award");
                award.appendChild(document.createTextNode(eventsAL.get(i).getAward()));
                event.appendChild(award);

                Element result = document.createElement("result");
                result.appendChild(document.createTextNode(String.valueOf(eventsAL.get(i).getResult())));
                event.appendChild(result);
            }
            root.appendChild(sportsmanEl);
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult res = new StreamResult(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_3/src/main/java/ex_2/file.xml"));
            tr.transform(source, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sportsman;
    }

    static void printMenNamesAndBirthdays(ArrayList<Sportsman> sportsmenAL) {
        for (int i = 0; i < sportsmenAL.size(); i++) {
            if (sportsmenAL.get(i).getS() == 'м') {
                System.out.println("Name: " + sportsmenAL.get(i).getName() + "\n"
                        + "Birthday: " + sportsmenAL.get(i).getBirthday() + "\n");
            }
        }
    }
    static void printWomenNamesBirthdaysAndQofAwards(ArrayList<Sportsman> sportsmenAL) {
        for (int i = 0; i < sportsmenAL.size(); i++) {
            if (sportsmenAL.get(i).getS() == 'ж' && sportsmenAL.get(i).getBirthday().getYear() - 1985 <= 0) {

                System.out.println("Name: " + sportsmenAL.get(i).getName() + "\n"
                        + "Birthday: " + sportsmenAL.get(i).getBirthday() + "\n"
                + "Quantity of awards: " + sportsmenAL.get(i).getEvents().size()+ "\n");
            }
        }
    }
    static void printSportsmenWhichParticipatedIn(ArrayList<Sportsman> sportsmenAL) {
        for (int i = 0; i < sportsmenAL.size(); i++) {
            boolean b = false;
            ArrayList<Event> eventsAL = sportsmenAL.get(i).getEvents();
            Event event = null;
            for (int j = 0; j < eventsAL.size(); j++) {
                if (eventsAL.get(j).getPlace().equals("москва") && eventsAL.get(j).getYear() == 2002) {
                    b = true;
                    event = eventsAL.get(j);
                    break;
                }
            }
            if (b) {
                System.out.println("Name: " + sportsmenAL.get(i).getName() + "\n"
                        + "Result: " + event.getAward() + "\n");
            }
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
                String[] birthdayStr =  sportsmenAttr.getNamedItem("birthday").getNodeValue().split("-");
                Sportsman sportsman = new Sportsman(sportsmenAttr.getNamedItem("name").getNodeValue(),
                        sportsmenAttr.getNamedItem("s").getNodeValue().charAt(0),
                        LocalDate.of(Integer.parseInt(birthdayStr[0]), Integer.parseInt(birthdayStr[1]), Integer.parseInt(birthdayStr[2])));
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
}

class Sportsman {
    String name;
    char s;
    LocalDate birthday;
    ArrayList<Event> events;

    public Sportsman(String name, char s, LocalDate birthday) {
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

    public LocalDate getBirthday() {
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
        return "Name: " + name + "\n" + "Sex: " + s + "\n" + "Birthday: " + birthday + "\n"
                + events.toString() + "\n";
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
        return "\n" + "Place: " + place + "\n" + "Year:" + year + "\n" + "Result:" + result + "\n"
                + "Award: " + award + "\n";
    }
}