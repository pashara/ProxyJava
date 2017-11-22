/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author admin
 */
public class User {
    String login;
    String password;
    boolean isAdmin;
    
    public User(String login, String password, boolean isAdmin)
    {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
