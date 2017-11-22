/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class UsersProvider {
    public static Map<String,User> GetUsers()
    {
        Map<String,User> users = new HashMap<String,User>();
        
        
        users.put("Admin",new User("Admin","Admin",true));
        users.put("User",new User("User","User",false));
        return users;
    }
}
