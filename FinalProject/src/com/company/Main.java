package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Configuration.getInstance();
        MainForm mainForm = new MainForm();
        //mainForm.pack();
        mainForm.setVisible(true);
        //mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
