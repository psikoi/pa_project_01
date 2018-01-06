package game.factories;

import game.models.GameDifficulty;
import game.strategies.ComputerMoveStrategy;
import game.strategies.moves.EasyComputerMove;
import game.strategies.moves.HardComputerMove;

public class ComputerMoveStrategyFactory {

    public ComputerMoveStrategy make(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return new EasyComputerMove();
            case HARD:
                return new HardComputerMove();
        }
        return new EasyComputerMove();
    }

}
