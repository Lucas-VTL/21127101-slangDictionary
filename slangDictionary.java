import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.TreeMap;

class ArrrayList<T> {

}

public class slangDictionary {
    public static void main(String[] args) {
        TreeMap<String, ArrayList<String>> dictionary = new TreeMap<>();
        dictionary = readDataFromFile();
        show(dictionary);
    }

    private static TreeMap<String, ArrayList<String>> readDataFromFile() {
        TreeMap<String, ArrayList<String>> dictionary = new TreeMap<>();
        File f = new File("slang.txt");

        try {
            BufferedReader br = Files.newBufferedReader(f.toPath());
            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    String[] data = line.split("`");
                    ArrayList<String> definition = new ArrayList<String>();

                    if (data[1].contains("|")) {
                        String[] def = data[1].split("\\| ");
                        for (int i = 0; i < def.length; i++) {
                            definition.add(def[i]);
                        }
                    } else {
                        definition.add(data[1]);
                    }

                    dictionary.put(data[0], definition);
                }
            }
            System.out.println("Import data from file successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Import data from file failed!");
        }

        return dictionary;
    }

    private static void show(TreeMap<String, ArrayList<String>> dictionary) {
        for (String slangWord : dictionary.keySet()) {
            System.out.println("Slang: " + slangWord);
            if (dictionary.get(slangWord).size() > 1) {
                for (int i = 0; i < dictionary.get(slangWord).size(); i++) {
                    System.out.println("- Definiton " + (i + 1) + ": "
                            + dictionary.get(slangWord).get(i).toString().replace("[", "").replace("]", ""));
                }
            }
            else {
                System.out.println("- Definiton: " + dictionary.get(slangWord).toString().replace("[", "").replace("]", ""));
            }
        }
    }

}