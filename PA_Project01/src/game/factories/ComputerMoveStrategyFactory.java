package game.factories;

import game.models.GameDifficulty;
import game.strategies.ComputerMoveStrategy;
import game.strategies.moves.EasyComputerMove;
import game.strategies.moves.HardComputerMove;

/**
 * This is a standart pattern Factory class that initiates a Strategy object that will
 * determine the computer's behaviour depending on the difficulty.
 * 
 * @author Ruben
 * @author Tiago
 */
public class ComputerMoveStrategyFactory {

    /**
     * This is method initiates a Strategy object that will
     * determine the computer's behaviour depending on the difficulty.
     * 
     * @param difficulty (An enum containing the various levels of game difficulty)
     * @return (A ComputerMoveStrategy to be used in a ComputerGame)
     */
    public ComputerMoveStrategy create(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return new EasyComputerMove();
            case HARD:
                return new HardComputerMove();
            default:
                return new EasyComputerMove();
        }
    }

}
