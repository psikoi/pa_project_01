/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao.serialization;

import data.dao.MachineDAO;
import game.models.Machine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class MachineDAOSerialization implements MachineDAO {
    
    private final static String MACHINE_FILE = "machine.dat";

    private Map<String, Machine> data;

    public MachineDAOSerialization() {
        this.data = loadAll();
    }

    @Override
    public Machine select() {
        return data.get("Machine");
    }

    @Override
    public boolean insert(Machine machine) {
        if(!data.isEmpty())
            return false;
        
        data.put("Machine", machine);
        
        save();
        
        return true;
    }
    
    @Override
    public boolean addGamePlayed(){
        Machine machine = data.get("Machine");
        if(machine == null)
            return false;
        
        machine.addGamePlayed();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addVictory(){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addVictory();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addLoss(){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addLoss();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addTimePlayed(long time){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addTimePlayed(time);
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }

    private Map<String, Machine> loadAll() {
        File f = new File(MACHINE_FILE);

        if (!f.exists()) {
            return new HashMap<>();
        } else {
            try {
                Map<String, Machine> readMap;
                FileInputStream fileIn = new FileInputStream(MACHINE_FILE);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                readMap = (Map<String, Machine>) in.readObject();

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

    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(MACHINE_FILE);
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