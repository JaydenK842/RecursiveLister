import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;

public class RecursiveListerGUI extends JFrame {
    JFrame frame;

    JButton directoryChoice;
    JButton quit;

    JTextArea textArea;
    JScrollPane scroll;

    JLabel title;

    public RecursiveListerGUI() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        frame.setLayout(null);

        //Finds the users screen height and width
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //Centers the frame in the middle of the user's screen
        frame.setSize(screenWidth / 2, screenHeight / 2);
        frame.setLocation(screenWidth / 4, screenHeight / 4);

        text();
        title.setBounds((frame.getWidth() - 200) / 2,15,200,50);
        frame.add(title);
        scroll.setBounds((frame.getWidth() - 370) / 2,70,370,200);
        frame.add(scroll);

        buttonPanel();
        directoryChoice.setBounds((frame.getWidth() - 150) / 3,300,150,40);
        frame.add(directoryChoice);
        quit.setBounds(((frame.getWidth() - 150) / 3) * 2,300,150,40);
        frame.add(quit);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void text() {
        title = new JLabel("Recursive File Lister");
        title.setFont(new Font("Arial", Font.PLAIN, 20));

        textArea = new JTextArea(10,35);
        textArea.setEditable(false);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void buttonPanel() {
        directoryChoice = new JButton("Choose Directory");
        directoryChoice.setFocusable(false);
        directoryChoice.addActionListener((ActionEvent ae) -> getDirectory(textArea));

        quit = new JButton("Quit");
        quit.setFocusable(false);
        quit.addActionListener((ActionEvent ae) -> System.exit(0));
    }

    private void getDirectory(JTextArea textArea) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        //Creates and sets a directory
        File workingDirectory= new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        //If they selected a file, it will run the code
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //Gets the file path
            selectedFile = chooser.getSelectedFile();
            Path file = selectedFile.toPath();

            String fullPath = file.getParent().toString();

            returnDirectory(0, 1, textArea, fullPath);
        }
    }

    private void returnDirectory(int i, int tabs, JTextArea textArea, String fullPath) {
        textArea.append(fullPath.substring(i, i + 1));

        if (fullPath.charAt(i) == '\\') {
            textArea.append("\n");

            for (int x = 0; x < tabs; x++) {
                textArea.append("   ");
            }

            if (i < fullPath.length() - 1) {
                returnDirectory(++i, ++tabs, textArea, fullPath);
            }
        } else {
            if (i < fullPath.length() - 1) {
                returnDirectory(++i, tabs, textArea, fullPath);
            }
        }
    }
}
