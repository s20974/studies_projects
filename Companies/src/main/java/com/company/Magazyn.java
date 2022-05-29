package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.TRAILING;


public class Magazyn extends AbstractTableModel{
    static ArrayList<Magazyn> listaMagazynow = new ArrayList<Magazyn>();
    public ArrayList<Przedmiot> przedmiotArrayList = new ArrayList<Przedmiot>();

    String nazwa = "";
    int powierzchnia = 0;
    User owner;
    public static int di = 0;
    public int id = 0;

    Magazyn(String nazwa, int powierzchnia, User user) {
        this.owner = user;
        this.nazwa = nazwa;
        this.powierzchnia = powierzchnia;
        di++;
        this.id = di;
        listaMagazynow.add(this);
    }

    Magazyn() {
        this.owner = null;
        this.nazwa = "";
        this.powierzchnia = 0;
    }

    public static class MagazynWindow extends JFrame{

        MagazynWindow() {

            JLabel nazwa = new JLabel("nazwa:");
            JLabel powierzchnia = new JLabel("powierzchnia:");
            JLabel owner = new JLabel("owner:");

            JTextField nazwa_filed = new JTextField(15);
            JTextField powierzchnia_field = new JTextField(15);

            JButton dodacz = new JButton("Dodacz");
            JButton dodacz_plik = new JButton("Dodacz z pliku");

            DefaultListModel<User> dlmMagazyn = new DefaultListModel<User>();

            for (int i = 0; i < User.all_users.size(); i++) {
                dlmMagazyn.addElement(User.all_users.get(i));
            }

            JList<User> userJList = new JList<User>();
            userJList.setModel(dlmMagazyn);
            JScrollPane userScroll = new JScrollPane(userJList);


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
                            .addComponent(owner)
                            .addComponent(userScroll))
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(dodacz)
                            .addComponent(dodacz_plik))
            );

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(TRAILING)
                            .addComponent(nazwa)
                            .addComponent(powierzchnia)
                           .addComponent(owner))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(nazwa_filed)
                            .addComponent(powierzchnia_field)
                            .addComponent(userScroll)
                            .addComponent(dodacz_plik))
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


            dodacz_plik.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            fileChooser.setDialogTitle("Choose directory");
                            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            int result = fileChooser.showOpenDialog(getContentPane());
                            if (result == JFileChooser.APPROVE_OPTION ){
                                try {
                                    ExcelFilesGenerator.readFromExcel(new Magazyn(), fileChooser.getSelectedFile());
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
                                JOptionPane.showMessageDialog(getContentPane(), "Enter store name");
                                return;
                            }
                            int pow;
                            try{
                                pow = Integer.parseInt(powierzchnia_field.getText());
                            }   catch (Exception e){
                                JOptionPane.showMessageDialog(getContentPane(), "Enter the number");
                                return;
                            }
                            User username = null;
                            username = userJList.getSelectedValue();
                            if(username == null){
                                JOptionPane.showMessageDialog(getContentPane(), "Choose owner");
                                return;
                            }
                            new Magazyn(nazwa_filed.getText(), pow, username);
                            setVisible(false);
                            dispose();
                        }
                    }
            );

            setSize(400, 250);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    public static void Window(){
        new MagazynWindow();
    }

    @Override
    public int getRowCount() {
        return listaMagazynow.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0:
                return listaMagazynow.get(i).id;
            case 1:
                return listaMagazynow.get(i).powierzchnia;
            case 2:
                return listaMagazynow.get(i).nazwa;
            case 3:
                return listaMagazynow.get(i).owner.username;
            case 4:
                return String.valueOf(getFreeSpace(listaMagazynow.get(i)));
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
                return "Wlasciciel";
            case 4:
                return "Wolna powierzchna";
            default:
                return "Niepoprawne dane";
        }
    }

    public int getFreeSpace(Magazyn magazyn){
        int powierzchna = magazyn.powierzchnia;
        int powierzchna_prz = 0;
        for(int i = 0; i < magazyn.przedmiotArrayList.size(); i++){
            powierzchna_prz += magazyn.przedmiotArrayList.get(i).powierzchnia;
        }

        return powierzchna - powierzchna_prz;
    }

    public String toString(){
        return "ID " + id + " Nazwa " + nazwa + " Wolna powierzchna " + getFreeSpace(this);
    }
}

