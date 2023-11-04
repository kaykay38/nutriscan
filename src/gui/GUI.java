package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public static void createAndShowGUI() {
        // Create a JFrame
        JFrame frame = new JFrame("My Simple GUI Application");

        // Create a JLabel and a JButton
        JLabel label = new JLabel("Hello, World!");
        JButton button = new JButton("Click Me");

        // Set the layout for the JFrame
        frame.setLayout(new FlowLayout());

        // Add the components to the JFrame
        frame.add(label);
        frame.add(button);

        // Configure the close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add an ActionListener to the button
        button.addActionListener(e -> {
            label.setText("Button Clicked!");
        });

        // Set the window size
        frame.setSize(300, 150);

        // Make the JFrame visible
        frame.setVisible(true);
    }
}