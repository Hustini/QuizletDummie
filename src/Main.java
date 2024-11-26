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

        JPanel panel = new JPanel();

        int screenWidth = 600;
        int screenHeight = 400;
        int posX = 100;

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
        frame.add(panel);

        panel.setLayout(null);

        // Label for a title
        JLabel label = new JLabel("Quizlet Dummie");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        int labelWidth = label.getPreferredSize().width;
        int labelX = (screenWidth - labelWidth) / 2;
        label.setBounds(labelX, 0, labelWidth, screenHeight / 10);
        panel.add(label);

        final String[] key = {keyList.getFirst()};
        JLabel questionLabel = new JLabel(key[0]);
        questionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        questionLabel.setBounds(posX, 100, screenWidth, screenHeight / 10);
        panel.add(questionLabel);

        //Text field to get user input
        JTextField textField = new JTextField();
        textField.setBounds(posX, 200, 300, 25);
        panel.add(textField);

        // Button to check user input
        JButton button = new JButton("Check");
        button.setBounds(posX + 320, 200, 80, 25);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == button) {
                    try {
                        // using the list of keys checking the user input and if its correct add it to a list
                        if(vocabulary.get(key[0]).equals(textField.getText())) {
                            System.out.println("Correct");
                            keyList.remove(key[0]);
                        } else {
                            System.out.println("Incorrect");
                            System.out.println("Correct answer: " + vocabulary.get(key[0]));
                        }
                        // clear text field
                        textField.setText("");
                        // get the next question
                        String newQuestion = getRandomQuestion(keyList);
                        key[0] = newQuestion;
                        questionLabel.setText(newQuestion);
                    } catch(Exception error) {
                        System.exit(0);
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