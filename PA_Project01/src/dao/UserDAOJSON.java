/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import game.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public final class UserDAOJSON implements UserDAO{
    
    private static UserDAOJSON instance;
    
    private final static String USER_FILE = "user.json";

    private Map<String, User> data;

    private UserDAOJSON() {
        this.data = loadAll();
    }
    
    
    public static UserDAOJSON getInstance(){
        if(instance == null)
             instance = new UserDAOJSON();
        return instance;
    }

    @Override
    public List<User> selectAll() {
       List<User> list = new LinkedList<>();
       list.addAll(data.values());

        return list;
    }

    @Override
    public User select(String username) {
        return data.get(username);
    }

    @Override
    public boolean insert(User user) {
        if(data.containsKey(user.getUsername()))
            return false;
        
        data.put(user.getUsername(), user);
        saveAll();
        return true;
        
    }

    @Override
    public boolean remove(String username) {
        boolean res =  data.remove(username) != null;
        saveAll();
        return res;
        
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        User user = data.get(username);
        if(user == null)
            return false;
        
        user.setPassword(newPassword);
        
        data.replace(username, user);
        
        saveAll();
        
        return true;
    }

    @Override
    public boolean updateEmail(String username, String newEmail) {
        User user = data.get(username);
        if(user == null)
            return false;
        
        user.setEmail(newEmail);
        
        data.replace(username, user);
        
        saveAll();
        
        return true;
    }
    
    
    public boolean addGamePlayed(String username){
        User user = data.get(username);
        if(user == null)
            return false;
        
        user.addGamePlayed();
        
        data.replace(username, user);
        
        saveAll();
        
        return true;
    }
    
   
    public boolean addVictory(String username){
        User user = data.get(username);
        if(user == null){
            return false;
        }
        
        user.addVictory();
        
        data.replace(username, user);
        
        saveAll();
        
        return true;
    }
    
     private Map<String, User> loadAll() {
        File file = new File(USER_FILE);

        if (!file.exists()) {
            return new HashMap<>();
        } else {
            try {
                Map<String, User> readMap;
                
                BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
                Gson gson = new GsonBuilder().create();

                readMap = gson.fromJson(br, new TypeToken<Map<String, User>>(){}.getType());

                return readMap;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
            }
        }

        return new HashMap<>();
    }
     
      private void saveAll() {
        try {
            Writer writer = new FileWriter(USER_FILE);
            
            Gson gson = new GsonBuilder().create();
            gson.toJson(this.data, writer);
            
            writer.close();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
        }
    }
    
}
