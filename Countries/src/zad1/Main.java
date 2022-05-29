package zad1;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.*;
import java.util.HashMap;

import org.json.*;

public class Main {

    private JTable ctab;

    public void createTable() throws Exception {
        ctab = new CountryTable().create();
    }

    public void showGui() {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame f = new JFrame("Countries table");
                f.add( new JScrollPane(ctab) );
                f.setPreferredSize(new Dimension(700, 500));
                f.pack();
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws IOException{
        Main main = new Main();
        try {
            main.createTable();
            main.showGui();
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Table creation failed, " + exc);
        }
    }

}