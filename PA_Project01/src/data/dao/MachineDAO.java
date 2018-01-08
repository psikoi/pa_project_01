package data.dao;

import game.models.Machine;

/**
 * Interface containing the methods that the DAO variations will have to implement.
 * 
 * @author Ruben
 * @author Tiago
 */
public interface MachineDAO {
    
    /**
     * 
     * @return machine being kept in file.
     */
    Machine select();

    /**
     * Inserts a new machine in file. Only one machine is kept.
     * 
     * @param machine machine being inserted.
     * @return sucess of the operation.
     */
    boolean insert(Machine machine);

    /**
     * Increments by 1 the value of games played in the easy difficulty
     * by the machine.
     * @return sucess of the operation.
     */
    boolean addEasyGamePlayed();
    
    /**
     * Increments by 1 the value of victories in the easy difficulty by the
     * machine.
     * @return sucess of the operation.
     */
    boolean addEasyVictory();
    
    /**
     * Increments by 1 the value of losses in the easy difficulty by the 
     * machine.
     * @return sucess of the operation.
     */
    boolean addEasyLoss();
    
    /**
     * Increments the value of time played in the easy difficulty by the
     * machine.
     * 
     * @param time - value that will be added to the current value.
     * @return sucess of the operation.
     */
    boolean addEasyTimePlayed(long time);
    
    /**
     * Increments by 1 the value of games played in the hard difficulty
     * by the machine.
     * @return sucess of the operation.
     */
    boolean addHardGamePlayed();
    
    /**
     * Increments by 1 the value of victories in the hard difficulty by the
     * machine.
     * @return sucess of the operation.
     */
    boolean addHardVictory();
    
    /** Increments by 1 the value of losses in the hard difficulty by the 
     * machine.
     * @return sucess of the operation.
     */
    boolean addHardLoss();
    
    /**
     * Increments the value of time played in the hard difficulty by the
     * machine.
     * 
     * @param time - value that will be added to the current value.
     * @return sucess of the operation.
     */
    boolean addHardTimePlayed(long time);
}