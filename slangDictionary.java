import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

class ArrrayList<T> {
}

public class slangDictionary {
    private static TreeMap<String, ArrayList<String>> sourceDictionary = new TreeMap<>();
    private static TreeMap<String, ArrayList<String>> dictionary = new TreeMap<>();
    private static TreeMap<String, ArrayList<String>> historyDictionary = new TreeMap<>();

    public static void main(String[] args) {
        sourceDictionary = readDataFromFile();
        dictionary.putAll(sourceDictionary);;
        dictionary = addSlangWord(dictionary);
        showTreeMap(dictionary);
        dictionary = resetDictionary();
        showTreeMap(dictionary);
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

        System.out.println("");
        return dictionary;
    }

    private static void showTreeMap(TreeMap<String, ArrayList<String>> dictionary) {
        for (String slangWord : dictionary.keySet()) {
            System.out.println("Slang: " + slangWord);
            if (dictionary.get(slangWord).size() > 1) {
                for (int i = 0; i < dictionary.get(slangWord).size(); i++) {
                    System.out.println("- Definiton " + (i + 1) + ": "
                            + dictionary.get(slangWord).get(i).toString().replace("[", "").replace("]", ""));
                }
            } else {
                System.out.println(
                        "- Definiton: " + dictionary.get(slangWord).toString().replace("[", "").replace("]", ""));
            }
        }
        System.out.println("");
    }

    private static void showElement(String slangWord, ArrayList<String> def) {
        System.out.println("Slang: " + slangWord);
        if (def.size() > 1) {
            for (int i = 0; i < def.size(); i++) {
                System.out.println("- Definiton " + (i + 1) + ": "
                        + def.get(i).toString().replace("[", "").replace("]", ""));
            }
        } else {
            System.out.println(
                    "- Definiton: " + def.toString().replace("[", "").replace("]", ""));
        }
        System.out.println("");
    }

    /**
     * @param dictionary
     * @param historyDictionary
     */
    private static boolean searchBySlang(TreeMap<String, ArrayList<String>> dictionary,
            TreeMap<String, ArrayList<String>> historyDictionary) {
        System.out.print("Input slang you want to search: ");
        Scanner scan = new Scanner(System.in);
        String slang = scan.nextLine();
        int count = 0;

        for (String slangWord : dictionary.keySet()) {
            if (slang.equals(slangWord.split("__")[0])) {
                showElement(slangWord, dictionary.get(slangWord));
                historyDictionary.put(slangWord, dictionary.get(slangWord));
                count++;
            }
        }
        if (count == 0) {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
            return false;
        } else {
            System.out.println("");
            return true;
        }
    }

    private static boolean searchBySlang(TreeMap<String, ArrayList<String>> dictionary,
            TreeMap<String, ArrayList<String>> historyDictionary, String slang) {
        int count = 0;

        for (String slangWord : dictionary.keySet()) {
            if (slang.equals(slangWord.split("__")[0])) {
                showElement(slangWord, dictionary.get(slangWord));
                historyDictionary.put(slangWord, dictionary.get(slangWord));
                count++;
            }
        }
        if (count == 0) {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
            return false;
        } else {
            System.out.println("");
            return true;
        }
    }

    private static boolean searchByDefinition(TreeMap<String, ArrayList<String>> dictionary,
            TreeMap<String, ArrayList<String>> historyDictionary) {
        System.out.print("Input definition you want to search: ");
        Scanner scan = new Scanner(System.in);
        CharSequence def = scan.nextLine();
        int count = 0;

        for (String slangWord : dictionary.keySet()) {
            for (int i = 0; i < dictionary.get(slangWord).size(); i++) {
                if (dictionary.get(slangWord).get(i).contains(def)) {
                    showElement(slangWord, dictionary.get(slangWord));
                    historyDictionary.put(slangWord, dictionary.get(slangWord));
                    count++;
                    break;
                }
            }
        }

        if (count == 0) {
            System.out.println("This definition does not exist in the dictionary");
            System.out.println("");
            return false;
        } else {
            System.out.println("");
            return true;
        }
    }

    private static TreeMap<String, ArrayList<String>> editSlangWord(TreeMap<String, ArrayList<String>> dictionary) {
        TreeMap<String, ArrayList<String>> duplicatedDictionary = new TreeMap<>();
        Scanner scan = new Scanner(System.in);
        if (searchBySlang(dictionary, duplicatedDictionary)) {
            for (String slangWord : duplicatedDictionary.keySet()) {
                ArrayList<String> definition = new ArrayList<>();
                int numberOfDef = 0;
                do {
                    System.out.print("Input number of definitions for editting: ");
                    numberOfDef = scan.nextInt();
                    scan.nextLine();
                } while (numberOfDef <= 0);

                if (numberOfDef == 1) {
                    System.out.print("Input definition for editting: ");
                    String def = scan.nextLine();
                    definition.add(def);
                } else {
                    for (int i = 0; i < numberOfDef; i++) {
                        System.out.print("Input definition " + (i + 1) + " for editting: ");
                        String def = scan.nextLine();
                        definition.add(def);
                    }
                }

                dictionary.put(slangWord, definition);
                System.out.println("");
            }
        } else {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
        }

        return dictionary;
    }

    private static TreeMap<String, ArrayList<String>> editSlangWord(TreeMap<String, ArrayList<String>> dictionary,
            String slang) {
        TreeMap<String, ArrayList<String>> duplicatedDictionary = new TreeMap<>();
        Scanner scan = new Scanner(System.in);
        if (searchBySlang(dictionary, duplicatedDictionary, slang)) {
            for (String slangWord : duplicatedDictionary.keySet()) {
                int choose = -1;
                System.out.println("Do you want to edit " + slangWord + " ?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                do {
                    System.out.print("Input your choice: ");
                    choose = scan.nextInt();
                } while (choose != 1 && choose != 2);

                if (choose == 1) {
                    ArrayList<String> definition = new ArrayList<>();
                    int numberOfDef = 0;
                    do {
                        System.out.print("Input number of definitions for editting: ");
                        numberOfDef = scan.nextInt();
                        scan.nextLine();
                    } while (numberOfDef <= 0);

                    if (numberOfDef == 1) {
                        System.out.print("Input definition for editting: ");
                        String def = scan.nextLine();
                        definition.add(def);
                    } else {
                        for (int i = 0; i < numberOfDef; i++) {
                            System.out.print("Input definition " + (i + 1) + " for editting: ");
                            String def = scan.nextLine();
                            definition.add(def);
                        }
                    }

                    dictionary.put(slangWord, definition);
                    System.out.println("");
                }
            }
        } else {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
        }

        return dictionary;
    }

    private static TreeMap<String, ArrayList<String>> removeSlangWord(TreeMap<String, ArrayList<String>> dictionary) {
        TreeMap<String, ArrayList<String>> duplicatedDictionary = new TreeMap<>();
        Scanner scan = new Scanner(System.in);

        if (searchBySlang(dictionary, duplicatedDictionary)) {
            for (String slangWord : duplicatedDictionary.keySet()) {
                System.out.println("Are you sure to remove slang " + slangWord + ": ");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choose = -1;

                do {
                    System.out.print("Input your choice: ");
                    choose = scan.nextInt();
                } while (choose != 1 && choose != 2);

                switch (choose) {
                    case 1: {
                        dictionary.remove(slangWord);
                        break;
                    }
                    case 2: {
                        break;
                    }
                }

                System.out.println("");
            }
        } else {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
        }

        return dictionary;
    }

    private static TreeMap<String, ArrayList<String>> removeSlangWord(TreeMap<String, ArrayList<String>> dictionary,
            String slang) {
        TreeMap<String, ArrayList<String>> duplicatedDictionary = new TreeMap<>();
        Scanner scan = new Scanner(System.in);

        if (searchBySlang(dictionary, duplicatedDictionary, slang)) {
            for (String slangWord : duplicatedDictionary.keySet()) {
                dictionary.remove(slangWord);

                System.out.println("");
            }
        } else {
            System.out.println("This slang does not exist in the dictionary");
            System.out.println("");
        }

        return dictionary;
    }

    private static TreeMap<String, ArrayList<String>> addSlangWord(TreeMap<String, ArrayList<String>> dictionary) {
        Scanner scan = new Scanner(System.in);
        String slang = "";
        TreeMap<String, ArrayList<String>> duplicatedDictionary = new TreeMap<>();

        do {
            System.out.print("Input new slang you want to add: ");
            slang = scan.nextLine();
        } while (slang.isEmpty() || slang.contains("__"));

        if (!searchBySlang(dictionary, duplicatedDictionary, slang)) {
            ArrayList<String> definition = new ArrayList<>();
            int numberOfDef = 0;
            do {
                System.out.print("Input number of definitions for new slang: ");
                numberOfDef = scan.nextInt();
                scan.nextLine();
            } while (numberOfDef <= 0);

            if (numberOfDef == 1) {
                System.out.print("Input definition for new slang: ");
                String def = scan.nextLine();
                definition.add(def);
            } else {
                for (int i = 0; i < numberOfDef; i++) {
                    System.out.print("Input definition " + (i + 1) + " for new slang: ");
                    String def = scan.nextLine();
                    definition.add(def);
                }
            }
            dictionary.put(slang, definition);

            return dictionary;
        } else {
            System.out.println("This slang already exists in the dictionary");
            System.out.println("The old slang:");
            showTreeMap(duplicatedDictionary);
            int choose = -1;
            System.out.println("What do you want ?");
            System.out.println("1. Overwrite the old slang");
            System.out.println("2. Duplicate and create a new slang");
            System.out.println("3. Exit");
            do {
                System.out.print("Input your choice: ");
                choose = scan.nextInt();
            } while (choose != 1 && choose != 2 && choose != 3);

            switch (choose) {
                case 1: {
                    dictionary = editSlangWord(dictionary, slang);
                    break;
                }
                case 2: {
                    if (duplicatedDictionary.size() == 1) {
                        String tempSlang = duplicatedDictionary.firstKey() + "__ver1";
                        ArrayList<String> tempDef = duplicatedDictionary.get(duplicatedDictionary.firstKey());

                        dictionary = removeSlangWord(dictionary, slang);
                        dictionary.put(tempSlang, tempDef);

                        tempSlang = slang + "__ver2";
                        ArrayList<String> definition = new ArrayList<>();
                        int numberOfDef = 0;
                        do {
                            System.out.print("Input number of definitions for new slang: ");
                            numberOfDef = scan.nextInt();
                            scan.nextLine();
                        } while (numberOfDef <= 0);

                        if (numberOfDef == 1) {
                            System.out.print("Input definition for new slang: ");
                            String def = scan.nextLine();
                            definition.add(def);
                        } else {
                            for (int i = 0; i < numberOfDef; i++) {
                                System.out.print("Input definition " + (i + 1) + " for new slang: ");
                                String def = scan.nextLine();
                                definition.add(def);
                            }
                        }
                        dictionary.put(tempSlang, definition);
                    } else {
                        System.out.println("");
                        String tempSlang = slang + "__ver" + (duplicatedDictionary.size() + 1);
                        ArrayList<String> definition = new ArrayList<>();
                        int numberOfDef = 0;
                        do {
                            System.out.print("Input number of definitions for new slang: ");
                            numberOfDef = scan.nextInt();
                            scan.nextLine();
                        } while (numberOfDef <= 0);

                        if (numberOfDef == 1) {
                            System.out.print("Input definition for new slang: ");
                            String def = scan.nextLine();
                            definition.add(def);
                        } else {
                            for (int i = 0; i < numberOfDef; i++) {
                                System.out.print("Input definition " + (i + 1) + " for new slang: ");
                                String def = scan.nextLine();
                                definition.add(def);
                            }
                        }
                        dictionary.put(tempSlang, definition);
                    }
                    break;
                }
                case 3: {
                    break;
                }
            }

            System.out.println("");
            return dictionary;
        }
    }

    private static TreeMap<String, ArrayList<String>> resetDictionary() {
        return sourceDictionary;
    }
}