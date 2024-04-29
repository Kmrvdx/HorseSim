// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JPanel;

// public class MySwingApp {
//     public static void main(String[] args) {
//         // Create GUI components
//         JButton button = new JButton("Click Me");

//         // Create a container (JPanel)
//         JPanel panel = new JPanel();
        
//         // Add components to container
//         panel.add(button);

//         // Create and configure JFrame
//         JFrame frame = new JFrame("My Swing App");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(300, 200);
//         frame.add(panel);
//         frame.setVisible(true);
//     }
// }

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setResizable(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField display = new JTextField();
        display.setPreferredSize(new Dimension(300, 50));
        display.setEditable(true);
        display.setHorizontalAlignment(JTextField.RIGHT);
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
                "Ans", "&", "^", "%",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        System.out.println("GUI Loaded");
        frame.setVisible(true);
    }
}
