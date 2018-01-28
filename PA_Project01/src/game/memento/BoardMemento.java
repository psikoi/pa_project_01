package game.memento;

import game.models.Board;

/**
 * A standart memento pattern class that holds a Board object.
 * 
 * ATTENTION: The Data Class/Inappropriate intimacy bad smells are unavoidable to properly comply
 * with the Memento Design Pattern.
 * 
 * @author Ruben
 * @author Tiago
 */
public class BoardMemento {
    
    private Board figure;

    public BoardMemento(Board figure) {
        this.figure = figure;
    }
    
    public Board getFigure() {
        return figure;
    }

}
