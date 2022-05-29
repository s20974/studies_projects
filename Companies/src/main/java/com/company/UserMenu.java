package com.company;

import java.awt.*;
import javax.swing.*;

public class UserMenu extends JFrame {
    public UserMenu(String name){
        JPanel panel = new JPanel();

        JButton stan = new JButton("Stan");
        JButton przedmioty = new JButton("Przedmioty");
        JButton mag_rz = new JButton("Zobacz magazyny/rzeczy");
        JButton zapisanie_stanu = new JButton("Zapisanie Stanu Magazynu");

        Container container = getContentPane();

        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        container.add(stan);
        container.add(przedmioty);
        container.add(mag_rz);
        container.add(zapisanie_stanu);

        setSize(575, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
