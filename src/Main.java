import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Main {
    public static void main(String[] args) {

        int screenWidth = 600;
        int screenHeight = 400;

        // Hashmap to store data
        String filePath = "src/data.csv";
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
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
        frame.add(panel);

        // Label for a title
        JLabel label = new JLabel("Quizlet Dummie");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 51, 102)); // Navy blue
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, screenWidth, 30);
        panel.add(label);

        // First question
        final String[] key = {keyList.getFirst()};
        JLabel questionLabel = new JLabel(key[0]);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionLabel.setForeground(new Color(0, 0, 0));
        questionLabel.setBounds(50, 100, screenWidth - 100, 30);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(questionLabel);

        // Text field to get user input
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBounds(150, 150, 300, 30);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(textField);

        // Button to check user input
        JButton button = new JButton("Check");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204)); // Blue
        button.setForeground(Color.WHITE);
        button.setBounds(250, 200, 100, 30);
        panel.add(button);

        // Feedback label
        JLabel feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setBounds(50, 250, screenWidth - 100, 30);
        panel.add(feedbackLabel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    try {
                        if (vocabulary.get(key[0]).equalsIgnoreCase(textField.getText().trim())) {
                            feedbackLabel.setText("Correct!");
                            feedbackLabel.setForeground(new Color(34, 139, 34)); // Green
                            keyList.remove(key[0]);
                        } else {
                            feedbackLabel.setText("Incorrect! Correct answer: " + vocabulary.get(key[0]));
                            feedbackLabel.setForeground(Color.RED);
                        }
                        // clear text field
                        textField.setText("");
                        // get and display next question
                        String newQuestion = getRandomQuestion(keyList);
                        key[0] = newQuestion;
                        questionLabel.setText(newQuestion);
                    } catch (Exception error) {
                        questionLabel.setText("Congratulations! You've completed all questions.");
                        button.setEnabled(false);
                        textField.setEnabled(false);
                    }
                }
            }
        });

        frame.setVisible(true);
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
        int randomIndex = (int) Math.floor(Math.random() * keyList.size());
        return keyList.get(randomIndex);
    }
}