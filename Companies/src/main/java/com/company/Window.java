package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import static javax.swing.GroupLayout.Alignment.*;


public class Window extends JFrame {

    static int condition = 0;

    public Window(){
        JPanel panel = new JPanel();

        JLabel username = new JLabel("username:");
        JLabel password = new JLabel("password:");
        JLabel confirm_password = new JLabel("confirm:");

        JTextField username_field = new JTextField(15);
        JPasswordField password_field = new JPasswordField(13);
        JPasswordField confirm_password_field = new JPasswordField(13);

        JButton display_pswd = new JButton("Show");

        JButton login = new JButton("LogIn");
        JButton signin = new JButton("SignIn" );

        login.setSize(100, 100);
        signin.setSize(100, 100);
        
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                    .addComponent(username)
                    .addComponent(username_field))
                .addGroup(layout.createParallelGroup(BASELINE)
                    .addComponent(password)
                    .addComponent(password_field)
                    .addComponent(display_pswd))
                .addGroup(layout.createParallelGroup(BASELINE)
                    .addComponent(login)
                    .addComponent(signin))
        );

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(TRAILING)
                        .addComponent(username)
                        .addComponent(password))
                .addGroup(layout.createParallelGroup()
                        .addComponent(username_field)
                        .addComponent(password_field)
                        .addComponent(login))
                .addGroup(layout.createParallelGroup()
                        .addComponent(display_pswd)
                        .addComponent(signin))
        );



        GroupLayout layout1 = new GroupLayout(panel);

        layout1.setVerticalGroup(layout1.createSequentialGroup()
                .addGroup(layout1.createParallelGroup(BASELINE)
                        .addComponent(username)
                        .addComponent(username_field))
                .addGroup(layout1.createParallelGroup(BASELINE)
                        .addComponent(password)
                        .addComponent(password_field)
                        .addComponent(display_pswd))
                .addGroup(layout1.createParallelGroup(BASELINE)
                        .addComponent(confirm_password)
                        .addComponent(confirm_password_field))
                .addGroup(layout1.createParallelGroup(BASELINE)
                        .addComponent(signin)
                        .addComponent(login))
        );

        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addGroup(layout1.createParallelGroup(TRAILING)
                        .addComponent(username)
                        .addComponent(password)
                        .addComponent(confirm_password))
                .addGroup(layout1.createParallelGroup()
                        .addComponent(username_field)
                        .addComponent(password_field)
                        .addComponent(confirm_password_field)
                        .addComponent(login))
                .addGroup(layout1.createParallelGroup()
                        .addComponent(display_pswd)
                        .addComponent(signin))
        );

        display_pswd.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String pswd = new String(password_field.getPassword());
                        JOptionPane.showMessageDialog(getContentPane(), "Password is " + pswd);
                    }
                }
        );

        signin.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(Window.condition == 0){

                            getContentPane().removeAll();
                            getContentPane().add(panel);
                            panel.setLayout(layout1);
                            getContentPane().revalidate();
                            getContentPane().repaint();
                            confirm_password.setVisible(true);
                            confirm_password_field.setVisible(true);


                            Window.condition = 1;
                        }   else {
                            String user_name = username_field.getText();
                            String password1 = new String(password_field.getPassword());
                            String password2 = new String(confirm_password_field.getPassword());

                            if(!password1.equals(password2)){
                                JOptionPane.showMessageDialog(getContentPane(), "Password mismatch");
                                return;
                            }
                            for(int i = 0; i < User.all_users.size(); i++){
                                if(User.all_users.get(i).username == user_name){
                                    JOptionPane.showMessageDialog(getContentPane(), "User is already exist");
                                    return;
                                }
                            }
                            User user = new User(user_name, password1);
                            Window.condition = 0;
                            new Menu(user);
                            setVisible(false);
                        }
                    }
                }
        );

        login.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(Window.condition == 1){
                            getContentPane().removeAll();
                            getContentPane().add(panel);
                            panel.setLayout(layout);
                            getContentPane().revalidate();
                            getContentPane().repaint();
                            confirm_password.setVisible(false);
                            confirm_password_field.setVisible(false);

                            Window.condition = 0;
                        }   else {
                            String user_name = username_field.getText();
                            String password1 = new String(password_field.getPassword());

                            boolean is_reg = false;
                            User user = User.checkUser(user_name, password1);

                            if(user == null){
                                JOptionPane.showMessageDialog(getContentPane(), "Wrong data");
                                return;
                            }
                            Window.condition = 0;
                            new Menu(user);
                            setVisible(false);
                        }
                    }
                }
        );

        setTitle("LogIn");
        setLocationRelativeTo(null);
        panel.setSize(300,100);
        getContentPane().add(panel);
        setVisible(true);
        confirm_password.setVisible(false);
        confirm_password_field.setVisible(false);
        getContentPane().setPreferredSize(new Dimension(400, 100));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(layout);
        pack();
    }


}