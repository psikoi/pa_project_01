package game.memento;

import game.models.Board;

public class BoardMemento {
    
    private Board figure;

    public BoardMemento(Board figure) {
        this.figure = figure;
    }
    
    public Board getFigure() {
        return figure;
    }

}
