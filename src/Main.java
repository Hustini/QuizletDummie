import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        HashMap<String, String> vocabulary = new HashMap<String, String>();
        vocabulary.put("Haus", "house");
        vocabulary.put("Baum", "tree");
        vocabulary.put("Auto", "car");
        vocabulary.put("Buch", "book");
        vocabulary.put("Hund", "dog");
        vocabulary.put("Katze", "cat");
        vocabulary.put("Apfel", "apple");
        vocabulary.put("Wasser", "water");
        vocabulary.put("Tisch", "table");
        vocabulary.put("Stuhl", "chair");

        // Get all keys as a Set
        Set<String> keys = vocabulary.keySet();
        System.out.println("Keys: " + keys);
        ArrayList<String> keyList = new ArrayList<>(keys);
        System.out.println("Keys as List: " + keyList);
        int[] index = {0};

        for (String i : vocabulary.keySet()) {
            System.out.println(i);
        }

        // Standard frame
        JFrame frame = new JFrame("Quizlet Dummie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        panel.setLayout(null);

        // Label for a title
        JLabel label = new JLabel("Quizlet Dummie");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        label.setBounds(175, 0, screenWidth, screenHeight / 10);
        panel.add(label);

        JLabel questionLabel = new JLabel(keyList.getFirst());
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
                    if(index[0] > keyList.size()) {
                        System.out.println("Index out of bounds");
                    } else {
                        if(vocabulary.get(keyList.get(index[0])).equals(textField.getText())) {
                            System.out.println("Correct");
                        } else {
                            System.out.println("Incorrect");
                        }
                        index[0]++;
                        questionLabel.setText(keyList.get(index[0]));
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}