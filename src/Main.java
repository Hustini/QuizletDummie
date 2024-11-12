import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JPanel panel = new JPanel();

        int screenWidth = 600;
        int screenHeight = 400;

        JFrame frame = new JFrame("Quizlet Dummie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        panel.setLayout(null);

        JLabel label = new JLabel("Quizlet Dummie");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        label.setBounds(230, 0, screenWidth, screenHeight / 10);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(150, 100, 300, 25);
        panel.add(textField);

        frame.setVisible(true);
    }
}