/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import game.models.Machine;
import java.util.List;

/**
 *
 * @author Tiago
 */
public interface MachineDAO {
    Machine select();

    boolean insert(Machine machine);

    boolean addEasyGamePlayed();
    
    boolean addEasyVictory();
    
    boolean addEasyLoss();
    
    boolean addEasyTimePlayed(long time);
    
    boolean addHardGamePlayed();
    
    boolean addHardVictory();
    
    boolean addHardLoss();
    
    boolean addHardTimePlayed(long time);
}