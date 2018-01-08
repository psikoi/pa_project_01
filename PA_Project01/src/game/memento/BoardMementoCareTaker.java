package game.memento;

import game.models.Board;
import java.util.Stack;

public class BoardMementoCareTaker {

    Stack<BoardMemento> boardMementos;

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
