package game.memento;

import game.models.Board;
import java.util.Stack;

/**
 * A standart memento pattern class that holds serves as the Care Taker.
 * 
 * @author Ruben
 * @author Tiago
 */
public class BoardMementoCareTaker {

    private Stack<BoardMemento> boardMementos;

    public BoardMementoCareTaker() {
        this.boardMementos = new Stack<>();
    }

    public void saveState(Board board) {
        BoardMemento memento = new BoardMemento(board.copy());
        boardMementos.push(memento);
    }

    public void restoreState(Board board) {
        if (boardMementos.isEmpty()) {
            return;
        }
        BoardMemento memento = boardMementos.pop();
        board.setMemento(memento);
    }

    
    
    
    
}
