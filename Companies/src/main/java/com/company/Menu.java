package com.company;

import javax.swing.*;

public class Menu extends JFrame {

    public Menu(User user){

        if(user.is_superuser){
            new SuperUserMenu(user.username);
        }   else{
            new UserMenu(user.username);
        }
    }
}
