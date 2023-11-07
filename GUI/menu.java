import java.awt.*;
import javax.swing.*;

public class menu extends JFrame {
    private JFrame frame;

    public static void main(String[] args) {
        new menu();
    }

    menu() {
        frame = new JFrame("Slang Dictionary Application");
        frame.setSize(800, 800);

        JLabel header = createHeader();
        JPanel searchPanel = createSearchPanel();
        JPanel historyPanel = createHistoryPanel();
        JPanel interactPanel = createInteractPanel();
        JPanel resetPanel = createResetPanel();
        JPanel randomPanel = createRandomPanel();

        frame.getContentPane().setBackground(Color.YELLOW);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(header);
        frame.add(searchPanel);
        frame.add(historyPanel);
        frame.add(interactPanel);
        frame.add(resetPanel);
        frame.add(randomPanel);

        GridLayout layout = new GridLayout(6, 1);
        frame.setLayout(layout);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static JLabel createHeader() {
        JLabel header = new JLabel("Slang Dictionary", JLabel.CENTER);
        header.setFont(new Font("Times New Roman", Font.BOLD, 35));
        header.setForeground(Color.BLUE);

        return header;
    }

    private static JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.YELLOW);

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

        return searchPanel;
    }

    private static JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(Color.YELLOW);

        JLabel title = new JLabel("View history searching");
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

        return historyPanel;
    }

    private static JPanel createInteractPanel() {
        JPanel interactPanel = new JPanel();
        interactPanel.setBackground(Color.YELLOW);

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

        return interactPanel;
    }

    private static JPanel createResetPanel() {
        JPanel resetPanel = new JPanel();
        resetPanel.setBackground(Color.YELLOW);

        JLabel title = new JLabel("Reset slang dictionary");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JLabel space3 = new JLabel("");
        JButton history = new JButton("Reset");
        history.setFont(new Font("Times New Roman", Font.BOLD, 20));

        resetPanel.add(title);
        resetPanel.add(space1);
        resetPanel.add(space2);
        resetPanel.add(space3);
        resetPanel.add(history);

        GridLayout layout = new GridLayout(2, 3);
        resetPanel.setLayout(layout);

        return resetPanel;
    }

    private static JPanel createRandomPanel() {
        JPanel randomPanel = new JPanel();
        randomPanel.setBackground(Color.YELLOW);

        JLabel title = new JLabel("Random activities");
        title.setFont(new Font("Times New Roman", Font.ITALIC, 25));
        title.setForeground(Color.BLUE);
        JLabel space1 = new JLabel("");
        JLabel space2 = new JLabel("");
        JButton randomSlang = new JButton("On this day slang word");
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

        return randomPanel;
    }    
}
