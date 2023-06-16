package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {

    public Main() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Library Management System");





    }

    public static void main(String[] args) throws FileNotFoundException {
        new Main();
    }
}
