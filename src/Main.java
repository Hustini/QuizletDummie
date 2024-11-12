import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static JButton button;
    static JTextField textField;

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

        textField = new JTextField();
        textField.setBounds(100, 100, 300, 25);
        panel.add(textField);

        button = new JButton("Check");
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