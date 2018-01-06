/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao.serialization;

import data.dao.UserDAO;
import game.models.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class UserDAOSerialization implements UserDAO {
    
    private final static String USER_FILE = "user.dat";

    private Map<String, User> data;

    public UserDAOSerialization() {
        this.data = loadAll();
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
        if (data.containsKey(user.getUsername())) {
            return false;
        }

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
    
    @Override
    public boolean addGamePlayed(String username){
        User user = data.get(username);
        if(user == null)
            return false;
        
        user.addGamePlayed();
        
        data.replace(username, user);
        
        saveAll();
        
        return true;
    }
    
    @Override
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
        File f = new File(USER_FILE);

        if (!f.exists()) {
            return new HashMap<>();
        } else {
            try {
                Map<String, User> readMap;
                FileInputStream fileIn = new FileInputStream(USER_FILE);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                readMap = (Map<String, User>) in.readObject();

                in.close();
                fileIn.close();

                return readMap;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
            }
        }

        return new HashMap<>();
    }

    private void saveAll() {
        try {
            FileOutputStream fileOut = new FileOutputStream(USER_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.data);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
        }
    }

}
