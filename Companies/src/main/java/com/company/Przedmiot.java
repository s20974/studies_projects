package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.TRAILING;


public class Przedmiot extends AbstractTableModel {
    public static ArrayList<Przedmiot> listaRzerzej = new ArrayList<Przedmiot>();

    public String nazwa = "";
    public int powierzchnia = 0;
    public int cena;
    public static int di = 0;
    public int id = 0;


    Przedmiot(String nazwa, int powierzchnia, int cena) {
        this.cena = cena;
        this.nazwa = nazwa;
        this.powierzchnia = powierzchnia;
        di++;
        this.id = di;
        listaRzerzej.add(this);
    }

    Przedmiot() {
        this.cena = 0;
        this.nazwa = "";
        this.powierzchnia = 0;
    }

    public static class PrzedmiotWindow extends JFrame{

        PrzedmiotWindow() {

            JLabel nazwa = new JLabel("nazwa:");
            JLabel powierzchnia = new JLabel("powierzchnia:");
            JLabel cena = new JLabel("cena:");

            JTextField nazwa_filed = new JTextField(15);
            JTextField powierzchnia_field = new JTextField(15);
            JTextField cena_field = new JTextField(15);

            JButton dodacz = new JButton("Dodacz");
            JButton dodacz_prz = new JButton("Dodacz z pliku");



            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(nazwa)
                            .addComponent(nazwa_filed))
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(powierzchnia)
                            .addComponent(powierzchnia_field))
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(cena)
                            .addComponent(cena_field))
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(dodacz)
                            .addComponent(dodacz_prz))
            );

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(TRAILING)
                            .addComponent(nazwa)
                            .addComponent(powierzchnia)
                            .addComponent(cena))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(nazwa_filed)
                            .addComponent(powierzchnia_field)
                            .addComponent(cena_field)
                            .addComponent(dodacz_prz))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(dodacz))
            );

            pack();
            JFileChooser fileChooser = new JFileChooser();

            UIManager.put(
                    "FileChooser.saveButtonText", "Save");
            UIManager.put(
                    "FileChooser.cancelButtonText", "Сancel");
            UIManager.put(
                    "FileChooser.fileNameLabelText", "File name");
            UIManager.put(
                    "FileChooser.filesOfTypeLabelText", "File type");
            UIManager.put(
                    "FileChooser.lookInLabelText", "Directory");
            UIManager.put(
                    "FileChooser.saveInLabelText", "Save");
            UIManager.put(
                    "FileChooser.folderNameLabelText", "Path");

            dodacz_prz.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            fileChooser.setDialogTitle("Choose directory");
                            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            int result = fileChooser.showOpenDialog(getContentPane());
                            if (result == JFileChooser.APPROVE_OPTION ){
                                try {
                                    ExcelFilesGenerator.readFromExcel(new Przedmiot(), fileChooser.getSelectedFile());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            JOptionPane.showMessageDialog(getContentPane(), fileChooser.getSelectedFile());
                        }
                    }
            );


            dodacz.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            if(nazwa_filed.getText() == null || nazwa_filed.getText() == ""){
                                JOptionPane.showMessageDialog(getContentPane(), "Enter subject name");
                                return;
                            }
                            int pow;
                            try{
                                pow = Integer.parseInt(powierzchnia_field.getText());
                            }   catch (Exception e){
                                JOptionPane.showMessageDialog(getContentPane(), "Enter the number");
                                return;
                            }
                            int price;
                            try{
                                price = Integer.parseInt(cena_field.getText());
                            }   catch (Exception e){
                                JOptionPane.showMessageDialog(getContentPane(), "Enter the number");
                                return;
                            }
                            new Przedmiot(nazwa_filed.getText(), pow, price);
                            setVisible(false);
                            dispose();
                        }
                    }
            );

            setSize(400, 165);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    public static void Window(){
        new PrzedmiotWindow();
    }

    @Override
    public int getRowCount() {
        return listaRzerzej.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0:
                return listaRzerzej.get(i).id;
            case 1:
                return listaRzerzej.get(i).powierzchnia;
            case 2:
                return listaRzerzej.get(i).nazwa;
            case 3:
                return listaRzerzej.get(i).cena;
            default:
                return null;
        }
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Pole powierzchni";
            case 2:
                return "Nazwa";
            case 3:
                return "Cena";
            default:
                return "Niepoprawne dane";
        }
    }

    public String toString(){
        return "ID:" +  id + "; Nazwa " + nazwa + "; Powierzchnia " + powierzchnia;
    }
}

