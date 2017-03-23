import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Genius Doan on 3/23/2017.
 */
public class FileUtils {
    public static Map<String, String> readAndParseXMLFile(String path) {
        try {
            Map<String, String> keywordMap = new HashMap<>();
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    keywordMap.put(eElement.getElementsByTagName("word").item(0).getTextContent(), eElement.getElementsByTagName("meaning").item(0).getTextContent());
                }
            }
            
            return  keywordMap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    public static List<String> readFromFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        List<String> stringList = new ArrayList<>();
        String line = "";

        while ((line = reader.readLine()) != null)
        {
            stringList.add(line);
        }
        reader.close();
        return stringList;
    }

    public static void writeToFile(String path, List<String> data, boolean append) throws IOException {
        File file = new File(path);

        if (!file.exists())
            file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));

        for (int i = 0; i < data.size(); i++)
        {
            writer.write(data.get(i));
        }

        writer.close();
    }

    public static void writeToFile(String path, String word, boolean append) throws IOException {
        File file = new File(path);

        if (!file.exists())
            file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));

        writer.write(word + "\n");

        writer.close();
    }
}
