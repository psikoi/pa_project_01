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
 * Implements Serialization. Creates a file named "machine.dat" that will
 * keep information about the Machine. The file only keeps one machine, and when
 * a computer game is being saved, it will increment the statistics of that one machine.
 * 
 * @author Tiago
 * @author Ruben
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
    public boolean addEasyGamePlayed(){
        Machine machine = data.get("Machine");
        if(machine == null)
            return false;
        
        machine.addEasyGamePlayed();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addEasyVictory(){
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
    public boolean addHardGamePlayed(){
        Machine machine = data.get("Machine");
        if(machine == null)
            return false;
        
        machine.addHardGamePlayed();
        
        data.replace("Machine", machine);
        save();
        
        return true;
    }
    
    @Override
    public boolean addHardVictory(){
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