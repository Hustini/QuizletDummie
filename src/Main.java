import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class Main {
    public static void main(String[] args) {

        int screenWidth = 600;
        int screenHeight = 400;

        // Hashmap to store data
        String filePath = "Data/data.csv";
        HashMap<String, String> vocabulary = readCSV(filePath);
        System.out.println("Vocab Hashmap:" + vocabulary);

        // Get all keys as a Set
        Set<String> keys = vocabulary.keySet();
        ArrayList<String> keyList = new ArrayList<>(keys);
        System.out.println("Keys as List: " + keyList);

        // Standard frame
        JFrame frame = new JFrame("Quizlet Dummie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);

        // Main Panel
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        frame.add(cardPanel);

        // Quiz Panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(null);
        quizPanel.setBackground(new Color(240, 248, 255));

        // Label for a title
        JLabel label = new JLabel("Quizlet Dummie");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 51, 102)); // Navy blue
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, screenWidth, 30);
        quizPanel.add(label);

        // First question
        final String[] key = {keyList.getFirst()};
        JLabel questionLabel = new JLabel(key[0]);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionLabel.setForeground(new Color(0, 0, 0));
        questionLabel.setBounds(50, 100, screenWidth - 100, 30);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quizPanel.add(questionLabel);

        // Text field to get user input
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBounds(150, 150, 300, 30);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        quizPanel.add(textField);

        // Button to check user input
        JButton button = new JButton("Check");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204)); // Blue
        button.setForeground(Color.WHITE);
        button.setBounds(250, 200, 100, 30);
        quizPanel.add(button);

        // Feedback label
        JLabel feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setBounds(50, 250, screenWidth - 100, 30);
        quizPanel.add(feedbackLabel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    handleAction(vocabulary, key, textField, feedbackLabel, keyList, questionLabel, button);
                }
            }
        });

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAction(vocabulary, key, textField, feedbackLabel, keyList, questionLabel, button);
            }
        });

        // Start screen panel
        JPanel startScreenPanel = new JPanel();
        startScreenPanel.setLayout(null);
        startScreenPanel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Quizlet Dummie");
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title.setForeground(new Color(0, 51, 102)); // Navy blue
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 20, screenWidth, 30);
        startScreenPanel.add(title);

        // Buttons to choose a quiz
        final File folder = new File("Data");
        int startScreenBtnX = 5;
        int startScreenBtnY = 70;
        int buttonWidth = 137;
        int buttonHeight = 40;
        int padding = 10;
        int buttonsPerRow = 4;
        int buttonCount = 0;

        JButton quizButtons;
        for (final File fileEntry : folder.listFiles()) {
            quizButtons = new JButton(fileEntry.getName());
            quizButtons.setFont(new Font("Arial", Font.BOLD, 14));
            quizButtons.setBackground(new Color(0, 102, 204)); // Blue
            quizButtons.setForeground(Color.WHITE);
            quizButtons.setBounds(startScreenBtnX, startScreenBtnY, buttonWidth, buttonHeight);
            startScreenPanel.add(quizButtons);
            startScreenBtnX += buttonWidth + padding;
            buttonCount++;

            if (buttonCount % buttonsPerRow == 0) {
                startScreenBtnX = 5;
                startScreenBtnY += buttonHeight + padding;
            }

            quizButtons.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    String selectedFileName = clickedButton.getText();
                    String selectedFilePath = "Data/" + selectedFileName;

                    HashMap<String, String> selectedVocabulary = readCSV(selectedFilePath);

                    vocabulary.clear();
                    vocabulary.putAll(selectedVocabulary);
                    keyList.clear();
                    keyList.addAll(vocabulary.keySet());

                    // Set the first question
                    key[0] = getRandomQuestion(keyList);
                    questionLabel.setText(key[0]);

                    // Reset quiz panel state
                    feedbackLabel.setText("");
                    textField.setText("");
                    button.setEnabled(true);
                    textField.setEnabled(true);

                    // Switch to quiz panel
                    cardLayout.show(cardPanel, "Quiz");
                }
            });
        }


        // Add a button to open a file chooser dialog on the start screen
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectFileButton.setBackground(new Color(0, 102, 204)); // Blue
        selectFileButton.setForeground(Color.WHITE);
        selectFileButton.setBounds(screenWidth / 2 - 75, screenHeight - 100, 150, 30);
        startScreenPanel.add(selectFileButton);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:/Users/ejder/OneDrive - Bildungszentrum Zürichsee/Desktop"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedFilePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFilePath);

                    // Define source and target file paths
                    Path sourcePath = Path.of(selectedFilePath);
                    Path targetPath = Path.of("C:/JavaProjects/Quizlet Dummie/Data/" + selectedFile.getName());

                    try {
                        // Copy the file
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File copied successfully!");
                    } catch (IOException error) {
                        System.err.println("Failed to copy the file: " + error.getMessage());
                    }
                }
            }
        });

        // Add panels to CardLayout
        cardPanel.add(quizPanel, "Quiz");
        cardPanel.add(startScreenPanel, "Start Screen");

        // Show the quiz panel initially
        cardLayout.show(cardPanel, "Start Screen");
        frame.setVisible(true);
    }

    private static void handleAction(HashMap<String, String> vocabulary, String[] key, JTextField textField, JLabel feedbackLabel, ArrayList<String> keyList, JLabel questionLabel, JButton button) {
        try {
            // user input is checked
            if (vocabulary.get(key[0]).equals(textField.getText().trim())) {
                feedbackLabel.setText("Correct!");
                feedbackLabel.setForeground(new Color(34, 139, 34)); // Green
                keyList.remove(key[0]);
            } else {
                feedbackLabel.setText("Incorrect! Correct answer: " + vocabulary.get(key[0]));
                feedbackLabel.setForeground(Color.RED);
            }

            // check if learning set is over
            if (keyList.isEmpty()) {
                questionLabel.setText("Congratulations! You've completed all questions.");
                button.setEnabled(false);
                textField.setEnabled(false);
            }

            // clear text field
            textField.setText("");
            // get and display next question
            String newQuestion = getRandomQuestion(keyList);
            key[0] = newQuestion;
            questionLabel.setText(newQuestion);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public static HashMap<String, String> readCSV(String filePath) {
        HashMap<String, String> test = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                test.put(values[0], values[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

    public static String getRandomQuestion(ArrayList<String> keyList) {
        /* First Step: get a random number from 0 to the length of the key list
        Second step: get the key with the index
         */
        try {
            int randomIndex = (int) Math.floor(Math.random() * keyList.size());
            return keyList.get(randomIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds");
        }
        return null;
    }
}