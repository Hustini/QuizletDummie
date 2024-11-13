import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {

        JPanel panel = new JPanel();

        int screenWidth = 600;
        int screenHeight = 400;

        // Hashmap to store data
        HashMap<String, String> vocabulary = new HashMap<String, String>();
        vocabulary.put("Haus", "House");
        vocabulary.put("Baum", "Tree");
        vocabulary.put("Auto", "Car");
        vocabulary.put("Buch", "Book");
        vocabulary.put("Hund", "Dog");
        vocabulary.put("Katze", "Cat");
        vocabulary.put("Apfel", "Apple");
        vocabulary.put("Wasser", "Water");
        vocabulary.put("Tisch", "Table");
        vocabulary.put("Stuhl", "Chair");

        for (String i : vocabulary.values()) {
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
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        label.setBounds(230, 0, screenWidth, screenHeight / 10);
        panel.add(label);

        //Text field to get user input
        JTextField textField = new JTextField();
        textField.setBounds(100, 100, 300, 25);
        panel.add(textField);

        // Button to check user input
        JButton button = new JButton("Check");
        button.setBounds(420, 100, 80, 25);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == button) {
                    String text = textField.getText();
                    System.out.println(text);
                }
            }
        });

        frame.setVisible(true);
    }
}