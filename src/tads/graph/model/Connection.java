package tads.graph.model;

import game.models.Player;

public class Connection {

    private Player selector;

    public Connection() {
        this.selector = null;
    }
    
    public boolean isSelected(){
        return selector != null;
    }
    
    public void select(Player selector){
        this.selector = selector;
        
    }

    public Player getSelector() {
        return selector;
    }
    
    
    
    
    

}
