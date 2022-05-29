package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () ->{
                    var ex1 = new Window();
                }
        );
    }
}
