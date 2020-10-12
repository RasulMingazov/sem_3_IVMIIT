package ex_1;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        /*
        Вывести на экран все данные из файла и общее количество страниц всех книг.
        Использовать Tree Model внутрипрограммного представления json в структуре дерева
         */
//        printAllAndSumOfPages();

        /*
        Добавить в прочитанной в п а) модели еще две книги Толстого и одну книгу Горького и сохранить обновленные данные в новый json-файл
         */
//        add();

        streamReading();
    }

    static void printAllAndSumOfPages() {
        try {
            JsonNode rootArr = mapper.readTree(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_1/file.json"));
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootArr));
            int sumPages = 0;
            for (JsonNode root : rootArr) {
                JsonNode booksArr = root.path("books");
                for (JsonNode book : booksArr) {
                    sumPages += book.path("pages").asInt();
                }
            }
            System.out.println("Summary pages: " + sumPages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void add() {
        try {
            JsonNode rootArr = mapper.readTree(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_1/file.json"));
            ObjectNode gogolNode = mapper.createObjectNode();
            gogolNode.put("writer name", "N.V.Gogol");
            ((ArrayNode) rootArr).add(gogolNode);
            for (JsonNode root : rootArr) {
                if (root.path("writer name").asText().equals("N.V.Gogol")) {
                    ArrayNode booksArr = mapper.createArrayNode();
                    ObjectNode book = mapper.createObjectNode();
                    book.put("book name", "Dead souls");
                    book.put("pages", 1120);
                    booksArr.add(book);
                    ((ObjectNode) root).set("books",booksArr);
                }
                if (root.path("writer name").asText().equals("L.N.Tolstoy")) {
                    JsonNode booksArr = root.path("books");
                    ObjectNode book1 = mapper.createObjectNode();
                    book1.put("book name", "Anna Karenina");
                    book1.put("pages", 1100);
                    ((ArrayNode) booksArr).add(book1);
                    ObjectNode book2 = mapper.createObjectNode();
                    book2.put("book name", "Childhood");
                    book2.put("pages", 1800);
                    ((ArrayNode) booksArr).add(book2);
                }
            }
            try (FileWriter f = new FileWriter("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_1/file.json")) {

                f.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootArr));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void streamReading() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_4/src/main/java/ex_1/file.json"));
        JsonToken token = parser.nextToken();
        while (parser.hasCurrentToken()) {
            String fieldname = parser.getCurrentName();
            if (token == JsonToken.VALUE_STRING && fieldname.equals("writer name")) {
                System.out.println(parser.getText());
            }
            token = parser.nextToken();
        }
    }
}
