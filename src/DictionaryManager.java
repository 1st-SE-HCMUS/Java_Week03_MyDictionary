import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Genius Doan on 3/23/2017.
 */
public class DictionaryManager {
    public static final int MODE_ENG_VIE = 0;
    public static final int MODE_VIE_ENG = 1;
    public static final int SEARCH_STARTS_WITH = 0;
    public static final int SEARCH_CONTAINS = 1;
    private static DictionaryManager engVieInstance = new DictionaryManager();
    private static DictionaryManager vieEngInstance = new DictionaryManager();
    private Map<String, String> map;
    private List<String> favouriteKeyword;
    private String engVieFilePath = "Anh_Viet.xml";
    private String vieEngFilePath = "Viet_Anh.xml";
    private String favouriteDataPath = "fav.txt";

    public static DictionaryManager getInstance(int mode) {
        if (mode == MODE_ENG_VIE)
            return engVieInstance;
        else if (mode == MODE_VIE_ENG)
            return vieEngInstance;

        System.err.println("Wrong dictionary mode! Only Eng-Vie or Vie-Eng allowed");
        return null;
    }

    private DictionaryManager() {
        map = new HashMap<>();
        favouriteKeyword = new ArrayList<>();
    }

    public void loadDataFromFile(String path)
    {
        map = FileUtils.readAndParseXMLFile(path);
    }

    public String[] getKeywordArray()
    {
        return map.keySet().toArray(new String[map.keySet().size()]);
    }

    public String getMeaning(String keyword) {
        return map.get(keyword);
    }

    public String[] search(String keyword, int searchMode)
    {
        String[] keySet =  map.keySet().toArray(new String[map.keySet().size()]);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < map.keySet().size(); i++)
        {
            if (searchMode == SEARCH_CONTAINS) {
                if (keySet[i].contains(keyword))
                    res.add(keySet[i]);
            }
            else {
               if (keySet[i].startsWith(keyword))
                   res.add(keySet[i]);
            }
        }

        return res.toArray(new String[res.size()]);
    }

    public void saveToFavourite(String word)
    {
        try {
            FileUtils.writeToFile(favouriteDataPath, word, true);
            JOptionPane.showMessageDialog(null, "Added to Favourite");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getFavouriteArray()
    {
        try {
            List<String> l = FileUtils.readFromFile(favouriteDataPath);
            return l.toArray(new String[l.size()]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
