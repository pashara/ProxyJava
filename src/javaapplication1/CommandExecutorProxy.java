/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import org.bouncycastle.crypto.DataLengthException;


public class CommandExecutorProxy implements CommandExecutor{

    String username;
    String password;
    
    String filepath;
    String command;
    
    boolean isAdmin = false;
    boolean isLogned = false;
    
    Map<String,User> users = UsersProvider.GetUsers();
    
    
    public CommandExecutorProxy()
    {
    }
    
    public CommandExecutorProxy(String username, String password)
    {
        this.Login(username, password);
    }
    
    public boolean Login(String username, String password)
    {
        this.username = username;
        this.password = password;
        
        if(users.containsKey(this.username))
        {
            User user = users.get(this.username);
            if(user.password.equals(password))
            {
                isLogned = true;
                isAdmin = user.isAdmin;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void runCommand(String cmd) throws Exception {
        if(!isLogned)
        {
            throw new Exception("Работа с программой разрешена только авторизированным пользователям.");   
        }
        else
        {
            String[] parts = cmd.split(" ");
            
            String type = "";
            String inputPath = "";
            String outputPath = "";
            if(parts.length != 0)
                type = parts[0];
            if(parts.length == 2)
            {
                inputPath = parts[1];
                outputPath = parts[1];
            }
            if(parts.length == 3)
            {
                inputPath = parts[1];
                outputPath = parts[2];
            }
            
            if(isAdmin)
            {
                if(type.equals("rashifrovat")) 
                {
                    if(inputPath != null && outputPath != null && !inputPath.equals(outputPath))
                    {
                        if(Encrypt(inputPath,outputPath))
                        {
                            System.out.println("Файл "+inputPath+" успешно расшифрован в "+outputPath);
                        }
                    }
                }
                else if(type.equals("shifrovat"))
                {
                    
                    if(inputPath != null && outputPath != null && !inputPath.equals(outputPath))
                    {
                        if(Decrypt(inputPath,outputPath))
                        {
                            System.out.println("Файл "+inputPath+" успешно зашифрован в "+outputPath);
                        }
                    }
                }
            }
            else
            {
                if(type.equals("rashifrovat")) 
                {
                    throw new Exception("rashifrovat  доступна только админам");
                }
                else if(type.equals("shifrovat"))
                {
                    if(inputPath != null && outputPath != null && !inputPath.equals(outputPath))
                    {
                        if(Decrypt(inputPath,outputPath))
                        {
                            System.out.println("Файл "+inputPath+" успешно зашифрован в "+outputPath);
                        }
                    }
                }
            }
        }
        
    }
    
    
    private boolean Decrypt(String from, String to)
    {
        FileInputStream fis;
        FileOutputStream fos;
        
        try {
            fis = new FileInputStream(new File(from));
            fos = new FileOutputStream(new File(to)); 
            BouncyCastleProvider_AES_CBC bc = new BouncyCastleProvider_AES_CBC();
            bc.InitCiphers();     
            bc.CBCEncrypt(fis, fos);
            return true;
        } catch (ShortBufferException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalBlockSizeException | BadPaddingException | DataLengthException | IllegalStateException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    
    
    private boolean Encrypt(String from, String to)
    {
        FileInputStream fis;
        FileOutputStream fos;
        
        try {
            fis = new FileInputStream(new File(from));
            fos = new FileOutputStream(new File(to));
            
            BouncyCastleProvider_AES_CBC bc = new BouncyCastleProvider_AES_CBC();
            bc.InitCiphers();
            //зашифровка файла           
            bc.CBCEncrypt(fis, fos);
            return true;
        } catch (ShortBufferException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalBlockSizeException | BadPaddingException | DataLengthException | IllegalStateException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    


}
