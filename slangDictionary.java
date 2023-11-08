import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ArrrayList<T> {
}

public class slangDictionary {
    public static TreeMap<String, ArrayList<String>> sourceDictionary;
    public static TreeMap<String, ArrayList<String>> dictionary;
    public static TreeMap<String, ArrayList<String>> historyDictionary;

    slangDictionary() {
        sourceDictionary = new TreeMap<>();
        dictionary = new TreeMap<>();
        historyDictionary = new TreeMap<>();

        sourceDictionary = readDataFromFile();
        dictionary.putAll(sourceDictionary);
    }

    public static TreeMap<String, ArrayList<String>> getSourceDictionary() {
        return sourceDictionary;
    }

    public static TreeMap<String, ArrayList<String>> getDictionary() {
        return dictionary;
    }

    public static TreeMap<String, ArrayList<String>> getHistoryDictionary() {
        return historyDictionary;
    }

    public static void main(String[] args) {
        new menu();
        new slangDictionary();
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
                        String tempSlang = duplicatedDictionary.firstKey().split("__")[0] + "__ver1";
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

    private static TreeMap<String, ArrayList<String>> randomSlang(TreeMap<String, ArrayList<String>> dictionary) {
        int size = dictionary.size();
        Random rand = new Random();
        int randomNumber = rand.nextInt(size);
        int i = 0;
        TreeMap<String, ArrayList<String>> randomSlang = new TreeMap<>();

        for (String slang : dictionary.keySet()) {
            if (i == randomNumber) {
                String slangWord = slang;
                ArrayList<String> definition = new ArrayList<>(dictionary.get(slang));
                randomSlang.put(slangWord, definition);
                break;
            } else {
                i++;
            }
        }

        return randomSlang;
    }

    private static boolean quizBySlang(TreeMap<String, ArrayList<String>> dictionary) {
        Scanner scan = new Scanner(System.in);
        boolean check = false;

        TreeMap<String, ArrayList<String>> answerSlang = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_1 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_2 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_3 = new TreeMap<>();

        answerSlang = randomSlang(dictionary);
        wrongSlang_1 = randomSlang(dictionary);
        wrongSlang_2 = randomSlang(dictionary);
        wrongSlang_3 = randomSlang(dictionary);

        TreeMap<String, ArrayList<String>> mixSlang = new TreeMap<>();
        mixSlang.put(answerSlang.firstKey(), answerSlang.get(answerSlang.firstKey()));
        mixSlang.put(wrongSlang_1.firstKey(), wrongSlang_1.get(wrongSlang_1.firstKey()));
        mixSlang.put(wrongSlang_2.firstKey(), wrongSlang_2.get(wrongSlang_2.firstKey()));
        mixSlang.put(wrongSlang_3.firstKey(), wrongSlang_3.get(wrongSlang_3.firstKey()));

        int i = 0;
        int j = 1;
        int choose = -1;

        System.out.println("What is the definitions of this slang: " + answerSlang.firstKey() + " ?");
        for (String slang : mixSlang.keySet()) {
            System.out.println((i + 1) + ". "
                    + mixSlang.get(slang).toString().replace("[", "").replace("]", ""));
            i++;
        }

        do {
            System.out.print("Input your choice: ");
            choose = scan.nextInt();
        } while (choose != 1 && choose != 2 && choose != 3 && choose != 4);

        for (String slang : mixSlang.keySet()) {
            if (j == choose) {
                if (slang.equals(answerSlang.firstKey())) {
                    check = true;
                    break;
                }
            }
            j++;
        }

        return check;
    }

    private static boolean quizByDef(TreeMap<String, ArrayList<String>> dictionary) {
        Scanner scan = new Scanner(System.in);
        boolean check = false;

        TreeMap<String, ArrayList<String>> answerSlang = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_1 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_2 = new TreeMap<>();
        TreeMap<String, ArrayList<String>> wrongSlang_3 = new TreeMap<>();

        answerSlang = randomSlang(dictionary);
        wrongSlang_1 = randomSlang(dictionary);
        wrongSlang_2 = randomSlang(dictionary);
        wrongSlang_3 = randomSlang(dictionary);

        TreeMap<String, ArrayList<String>> mixSlang = new TreeMap<>();
        mixSlang.put(answerSlang.firstKey(), answerSlang.get(answerSlang.firstKey()));
        mixSlang.put(wrongSlang_1.firstKey(), wrongSlang_1.get(wrongSlang_1.firstKey()));
        mixSlang.put(wrongSlang_2.firstKey(), wrongSlang_2.get(wrongSlang_2.firstKey()));
        mixSlang.put(wrongSlang_3.firstKey(), wrongSlang_3.get(wrongSlang_3.firstKey()));

        int i = 0;
        int j = 1;
        int choose = -1;

        System.out.println("What is the slang of this definition: " + answerSlang.get(answerSlang.firstKey()) + " ?");
        for (String slang : mixSlang.keySet()) {
            System.out.println((i + 1) + ". " + slang);
            i++;
        }

        do {
            System.out.print("Input your choice: ");
            choose = scan.nextInt();
        } while (choose != 1 && choose != 2 && choose != 3 && choose != 4);

        for (String slang : mixSlang.keySet()) {
            if (j == choose) {
                if (mixSlang.get(slang).equals(answerSlang.get(answerSlang.firstKey()))) {
                    check = true;
                    break;
                }
            }
            j++;
        }

        return check;
    }
}

class menu extends JFrame {
    private static JFrame frame;
    private static Color bgColor = new Color(227, 197, 237);
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new menu());
    }

    menu() {
        frame = new JFrame("Slang Dictionary Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(bgColor);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel menuPage = createMenuPage();
        mainPanel.add(menuPage, "menu");
        frame.add(mainPanel);

        JPanel slangSearchPage = createFunctionPage("Searching by slang");
        mainPanel.add(slangSearchPage, "slangSearchPage");

        JPanel defSearchPage = createFunctionPage("Searching by definitions");
        mainPanel.add(defSearchPage, "defSearchPage");

        JPanel viewHistoryPage = createFunctionPage("Searching history");
        mainPanel.add(viewHistoryPage, "viewHistoryPage");

        JPanel addPage = createFunctionPage("Adding new slang to dictionary");
        mainPanel.add(addPage, "addPage");

        JPanel removePage = createFunctionPage("Remove slang from dictionary");
        mainPanel.add(removePage, "removePage");

        JPanel editPage = createFunctionPage("Edit slang definitions");
        mainPanel.add(editPage, "editPage");

        JPanel resetPage = createFunctionPage("Reset to original dictionary");
        mainPanel.add(resetPage, "resetPage");

        JPanel randomSlangPage = createFunctionPage("On this day slang word");
        mainPanel.add(randomSlangPage, "randomSlangPage");

        JPanel quizOnSlangPage = createFunctionPage("Quiz on slang");
        mainPanel.add(quizOnSlangPage, "quizOnSlangPage");

        JPanel quizOnDefPage = createFunctionPage("Quiz on definitions");
        mainPanel.add(quizOnDefPage, "quizOnDefPage");

        frame.setVisible(true);
    }

    private static JPanel createMenuPage() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        JLabel header = createHeader();
        JPanel searchPanel = createSearchPanel();
        JPanel historyPanel = createHistoryPanel();
        JPanel interactPanel = createInteractPanel();
        JPanel resetPanel = createResetPanel();
        JPanel randomPanel = createRandomPanel();

        JPanel contentPanel = new JPanel(new GridLayout(6, 1));

        contentPanel.add(header);
        contentPanel.add(searchPanel);
        contentPanel.add(historyPanel);
        contentPanel.add(interactPanel);
        contentPanel.add(resetPanel);
        contentPanel.add(randomPanel);

        menuPanel.add(contentPanel, BorderLayout.CENTER);
        frame = createBorder(menuPanel);

        return menuPanel;
    }

    private static JFrame createBorder(JPanel panel) {
        JLabel top = new JLabel();
        top.setPreferredSize(new Dimension(800, 50));
        JLabel left = new JLabel();
        left.setPreferredSize(new Dimension(50, 800));
        JLabel right = new JLabel();
        right.setPreferredSize(new Dimension(50, 800));
        JLabel bottom = new JLabel();
        bottom.setPreferredSize(new Dimension(800, 100));

        frame.add(top, BorderLayout.NORTH);
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);
        frame.add(bottom, BorderLayout.SOUTH);

        return frame;
    }

    private static JLabel createHeader() {
        JLabel header = new JLabel("Slang Dictionary", JLabel.CENTER);
        header.setFont(new Font("Times New Roman", Font.BOLD, 35));
        header.setBackground(bgColor);
        header.setOpaque(true);
        header.setForeground(Color.BLUE);

        return header;
    }

    private static JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(bgColor);

        JLabel title = new JLabel("Searching");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space = new JLabel("");
        JButton slangSearch = new JButton("Search by slang");
        slangSearch.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JButton defSearch = new JButton("Search by definition");
        defSearch.setFont(new Font("Times New Roman", Font.BOLD, 20));

        searchPanel.add(title);
        searchPanel.add(space);
        searchPanel.add(slangSearch);
        searchPanel.add(defSearch);

        GridLayout layout = new GridLayout(2, 2);
        layout.setHgap(10);
        searchPanel.setLayout(layout);

        slangSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "slangSearchPage");
            }
        });

        defSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "defSearchPage");
            }
        });

        return searchPanel;
    }

    private static JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(bgColor);

        JLabel title = new JLabel("View history search");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JLabel space3 = new JLabel("");
        JButton history = new JButton("History searching");
        history.setFont(new Font("Times New Roman", Font.BOLD, 20));

        historyPanel.add(title);
        historyPanel.add(space1);
        historyPanel.add(space2);
        historyPanel.add(space3);
        historyPanel.add(history);

        GridLayout layout = new GridLayout(2, 3);
        historyPanel.setLayout(layout);

        history.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "viewHistoryPage");
            }
        });

        return historyPanel;
    }

    private static JPanel createInteractPanel() {
        JPanel interactPanel = new JPanel();
        interactPanel.setBackground(bgColor);

        JLabel title = new JLabel("Interact with slangs");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JButton add = new JButton("Add new slang");
        add.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JButton remove = new JButton("Remove old slang");
        remove.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JButton edit = new JButton("Edit definitons");
        edit.setFont(new Font("Times New Roman", Font.BOLD, 20));

        interactPanel.add(title);
        interactPanel.add(space1);
        interactPanel.add(space2);
        interactPanel.add(add);
        interactPanel.add(remove);
        interactPanel.add(edit);

        GridLayout layout = new GridLayout(2, 3);
        layout.setHgap(10);
        interactPanel.setLayout(layout);

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "addPage");
            }
        });

        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "removePage");
            }
        });

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "editPage");
            }
        });

        return interactPanel;
    }

    private static JPanel createResetPanel() {
        JPanel resetPanel = new JPanel();
        resetPanel.setBackground(bgColor);

        JLabel title = new JLabel("Reset slang dictionary");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JLabel space3 = new JLabel("");
        JButton reset = new JButton("Reset");
        reset.setFont(new Font("Times New Roman", Font.BOLD, 20));

        resetPanel.add(title);
        resetPanel.add(space1);
        resetPanel.add(space2);
        resetPanel.add(space3);
        resetPanel.add(reset);

        GridLayout layout = new GridLayout(2, 3);
        resetPanel.setLayout(layout);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "resetPage");
            }
        });

        return resetPanel;
    }

    private static JPanel createRandomPanel() {
        JPanel randomPanel = new JPanel();
        randomPanel.setBackground(bgColor);

        JLabel title = new JLabel("Random activities");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JButton randomSlang = new JButton("Slang for today");
        randomSlang.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JButton quizOnSlang = new JButton("Quiz on slang");
        quizOnSlang.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JButton quizOnDef = new JButton("Quiz on definitions");
        quizOnDef.setFont(new Font("Times New Roman", Font.BOLD, 20));

        randomPanel.add(title);
        randomPanel.add(space1);
        randomPanel.add(space2);
        randomPanel.add(randomSlang);
        randomPanel.add(quizOnSlang);
        randomPanel.add(quizOnDef);

        GridLayout layout = new GridLayout(2, 3);
        layout.setHgap(10);
        randomPanel.setLayout(layout);

        randomSlang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "randomSlangPage");
            }
        });

        quizOnSlang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "quizOnSlangPage");
            }
        });

        quizOnDef.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "quizOnDefPage");
            }
        });
        return randomPanel;
    }

    private static JPanel createFunctionPage(String functionTitle) {
        JPanel functionPanel = new JPanel();
        functionPanel.setBackground(bgColor);
        JButton backButton = new JButton("Back to menu");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menu");
            }
        });

        functionPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(functionTitle, JLabel.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        functionPanel.add(titleLabel, BorderLayout.NORTH);
        functionPanel.add(backButton, BorderLayout.SOUTH);

        return functionPanel;
    }
}