/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao.dao_json;

import data.dao.MachineDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import game.models.Machine;
import game.models.User;
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
public final class MachineDAOJSON implements MachineDAO{
    
    private static MachineDAOJSON instance;
    
    private final static String MACHINE_FILE = "machine.json";

    private Machine data;

    private MachineDAOJSON() {
        this.data = loadAll();
    }
    
    public static MachineDAOJSON getInstance(){
        if(instance == null)
             instance = new MachineDAOJSON();
        
        return instance;
    }

    @Override
    public Machine select() {
        return data;
    }

    @Override
    public boolean insert(Machine machine) {
        if(machine != null)
            return false;
        
        data = machine;
        
        save();
        
        return true;
    }

    @Override
    public boolean addGamePlayed() {
        if(data == null)
            return false;
        
        data.addGamePlayed();
        
        save();
        
        return true;
    }

    @Override
    public boolean addVictory() {
        if(data == null)
            return false;
        
        data.addVictory();
        
        save();
        
        return true;
    }
    
    private Machine loadAll() {
        File file = new File(MACHINE_FILE);

        if (!file.exists()) {
            return null;
        } else {
            try {
                Map<String, Machine> readMap;
                
                BufferedReader br = new BufferedReader(new FileReader(MACHINE_FILE));
                Gson gson = new GsonBuilder().create();

                readMap = gson.fromJson(br, new TypeToken<Map<String, Machine>>(){}.getType());

                for(Machine m : readMap.values()){
                    return m;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
            }
        }

        return null;
    }
    
    private void save() {
        try {
            Writer writer = new FileWriter(MACHINE_FILE);
            
            Gson gson = new GsonBuilder().create();
            gson.toJson(this.data, writer);
            
            writer.close();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
        }
    }
    
}
