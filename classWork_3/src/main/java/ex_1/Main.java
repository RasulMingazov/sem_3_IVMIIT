package ex_1;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.sound.midi.Soundbank;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("/Users/a1/Desktop/java/classWork/sem_3/classWork_3/src/main/java/ex_1/file.xml"));
            Node root = document.getDocumentElement();

            NodeList writers = ((Element) root).getElementsByTagName("writer");
            for (int i = 0; i < writers.getLength(); i++) {
                Node writer = writers.item(i);
                NamedNodeMap attributes = writer.getAttributes();
                System.out.println("Author name: " + attributes.getNamedItem("name").getNodeValue() + "\n");

                NodeList books = ((Element) writer).getElementsByTagName("book");
                for (int j = 0; j < books.getLength(); j++) {
                    Node book = books.item(j);
                    NamedNodeMap attributesBook = book.getAttributes();
                    System.out.println("Book name: " + attributesBook.getNamedItem("bName").getNodeValue());
                    System.out.println("Pages: " + attributesBook.getNamedItem("pages").getNodeValue() + "\n");
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}