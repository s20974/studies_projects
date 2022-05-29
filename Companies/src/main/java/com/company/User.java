package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class User {
    static public ArrayList<User> all_users = new ArrayList<User>();
    boolean is_superuser = true;
    String username = "";
    String password = "";

    private byte salt[];

    public User(String username, String password){
        this.salt = getSalt();
        password = get_SHA_256_SecurePassword(password, this.salt);
        this.password = password;
        this.username = username;
        System.out.println("Username: " + username + "; password " + this.password);
        all_users.add(this);
    }

    public byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static User checkUser(String username, String password){
        for(int i =0; i < all_users.size(); i++){
            if(all_users.get(i).username.equals(username)){
                String password_gen = get_SHA_256_SecurePassword(password, all_users.get(i).salt);
                if(all_users.get(i).password.equals(password_gen)){
                    return all_users.get(i);
                }
            }
        }
        return null;
    }

    public static User getUserByUsername(String username){
        User ret = null;
        for(User user: all_users){
            System.out.println(user);
            if(user.username.equals(username)){
                System.out.println("yes");
                ret = user;
            }
        }
        return ret;
    }

    public String toString(){
        return username;
    }
}
