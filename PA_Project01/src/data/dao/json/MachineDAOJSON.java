package data.dao.json;

import data.dao.MachineDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import game.models.Machine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements JSON Persistence. Creates a file named "machine.json" that will
 * keep information about the Machine. The file only keeps one machine, and when
 * a computer game is being saved, it will increment the statistics of that one machine.
 * 
 * @author Ruben
 * @author Tiago
 */
public class MachineDAOJSON implements MachineDAO{
    
    private final static String MACHINE_FILE = "machine.json";

    private Map<String, Machine> data;

    public MachineDAOJSON() {
       this.data = loadAll();
    }
    
    /**
     * Selects the machine being kept in file.
     * @return machine being kept in file.
     */
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
    public boolean addEasyGamePlayed() {
        Machine machine = data.get("Machine");
        if(machine == null)
            return false;
        
        machine.addEasyGamePlayed();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }

    @Override
    public boolean addEasyVictory() {
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addEasyVictory();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addEasyLoss(){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addEasyLoss();
        
        data.replace("Machine", machine);
        
        save();
        
        return true;
    }
    
    @Override
    public boolean addEasyTimePlayed(long time){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addEasyTimePlayed(time);
        
        data.replace("Machine", machine);
        
        save();
        
        return true;
    }
    
     @Override
    public boolean addHardGamePlayed() {
        Machine machine = data.get("Machine");
        if(machine == null)
            return false;
        
        machine.addHardGamePlayed();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }

    @Override
    public boolean addHardVictory() {
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addHardVictory();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addHardLoss(){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addHardLoss();
        
        data.replace("Machine", machine);
        
        save();
        
        return true;
    }
    
    @Override
    public boolean addHardTimePlayed(long time){
        Machine machine = data.get("Machine");
        if(machine == null){
            return false;
        }
        
        machine.addHardTimePlayed(time);
        
        data.replace("Machine", machine);
        
        save();
        
        return true;
    }

    
    private Map<String, Machine> loadAll() {
        File file = new File(MACHINE_FILE);

        if (!file.exists()) {
            return new HashMap<>();
         } else {
            try {
                Map<String, Machine> readMap;
                
                BufferedReader br = new BufferedReader(new FileReader(MACHINE_FILE));
                Gson gson = new GsonBuilder().create();

                readMap = gson.fromJson(br, new TypeToken<Map<String, Machine>>(){}.getType());

                
                return readMap;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage());
            }
       }

        return new HashMap<>();
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