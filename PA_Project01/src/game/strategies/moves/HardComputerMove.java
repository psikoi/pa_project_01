package game.strategies.moves;

import game.models.Board;
import game.models.Game;
import game.models.Machine;
import game.models.Player;
import game.strategies.ComputerMoveStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import tads.graph.Edge;
import tads.graph.model.Connection;
import tads.graph.model.Joint;
import tads.tree.LinkedTree;
import tads.tree.Position;

/**
 *
 * @author Ruben
 */
public class HardComputerMove implements ComputerMoveStrategy {

    /*
     The simulated player index, this is used to simulate alternating player
     moves.d
     */
    private int simPlayerIndex;

    /**
     * This method will calculate the best possible move on a given game. It
     * uses trees to calculate every move possibility, evaluates every possible
     * path, organizes the possibilities by score and chooses the best at end.
     *
     * If all ramining moves are loosing moves, it will choose the first
     * available.
     *
     * @param game (The current game state)
     * @return (The best possible move)
     */
    @Override
    public Edge<Connection, Joint> calculateMove(Game game) {

        /*
         To avoid longer trees in the beginning moves, the first move is
         completely random.
         */
        if (game.getRounds().size() < 3) {
            ArrayList<Edge<Connection, Joint>> possibleMoves = game.getBoard().getPossibleMoves();
            return possibleMoves.get(new Random().nextInt(possibleMoves.size()));
        }

        simPlayerIndex = 0;
        Map<Position<Board>, Integer> results = new HashMap<>();

        Board currentBoard = game.getBoard().copy();
        LinkedTree<Board> tree = new LinkedTree<>(currentBoard);

        Position<Board> root = tree.root();

        /*
         Recursively adds the subtrees of root.
         */
        addSubtrees(tree, root, game);

        int treeHeight = tree.height();

        ArrayList<BoardScorePair> toInsert = new ArrayList<>();

        for (Position<Board> board : tree.depthOrder()) {
            
            /*
               Evaluates each path to all the external nodes.
            */
            if (tree.isExternal(board)) {

                toInsert = new ArrayList<>();

                int height = treeHeight;
                Position<Board> current = board;

                while (!tree.isRoot(current)) {
                    evaluate(tree, current, height, toInsert);
                    height--;
                    current = tree.parent(current);
                }

                for (BoardScorePair pair : toInsert) {
                    results.put(pair.getBoard(), pair.getScore());
                }

            }
        }

        /*
         Sorts the results Map by score (lower to higher)
        */
        results = sortByScore(results);

        /*
          Given the ordered results, chooses the best scored possible move.
        */
        for (Map.Entry<Position<Board>, Integer> entry : results.entrySet()) {

            Position<Board> prev = null;
            Position<Board> next = entry.getKey();
            while (!tree.isRoot(next)) {
                prev = next;
                next = tree.parent(next);
            }

            for (Edge<Connection, Joint> edge : prev.element().getGraph().edges()) {
                if (edge.element().getSelector() instanceof Machine) {
                    Edge<Connection, Joint> chosen = game.getBoard().findIdentical(edge);
                    if (chosen.element().isSelected()) {
                        continue;
                    }
                    return chosen;
                }
            }

        }

        return null;

    }

    /**
     *
     * This method evaluates a current node's score. If the current node
     * is a loosing node, it will clear the list (path) to avoid the computer
     * pursuing an eventually loosing path. (Some paths score better even
     * if they have a loosing node in the middle)
     * 
     * Score weights:
     * - Winning = 1
     * - Loosing = 100
     * - Default = 25
     * 
     * The score is then determined by the weight * tree height.
     * 
     * @return (The node's score)
     */
    public int evaluate(LinkedTree<Board> tree, Position<Board> parent, int height, ArrayList<BoardScorePair> list) {
        int score = 25 * height;

        if (parent.element().numSelectedEdges() < 5) {
            return score;
        }

        Edge<Connection, Joint> toCheck = difference(parent.element(), tree.parent(parent).element());
        Player p = toCheck.element().getSelector();

        ArrayList<Edge<Connection, Joint>> edges = parent.element().checkMove(p, toCheck);

        if (!edges.isEmpty()) {

            if (edges.get(0).element().getSelector() instanceof Machine) {
                score = 100 * height;
                list.clear();
            } else {
                score = 1 * height;
            }

        }

        list.add(new BoardScorePair(parent, score));

        return score;

    }

    /*
      Determines the difference between two boards, to know which edge was
      selected in between the two.
    */
    private Edge<Connection, Joint> difference(Board a, Board b) {

        for (Edge<Connection, Joint> edge : a.getGraph().edges()) {
            if (edge.element().isSelected()) {
                Edge<Connection, Joint> identical = b.findIdentical(edge);
                if (identical == null || !identical.element().isSelected()) {
                    return edge;
                }
            }

        }

        return null;

    }

    /**
     * Recursively adds a subtree to a node.
     * Alternates the simulated player index for every node.
     */
    public void addSubtrees(LinkedTree<Board> tree, Position<Board> parent, Game game) {

        for (Edge<Connection, Joint> possibleMove : parent.element().getPossibleMoves()) {
            Board b = parent.element().copy();

            Edge<Connection, Joint> found = b.findIdentical(possibleMove);
            if (found != null) {
                tree.insert(parent, b);
                Player sim = getSimulatedPlayer(game);
                found.element().select(sim);
            }
        }

        if (tree.height() > 8) {
            return;
        }

        int temp = simPlayerIndex;

        if (simPlayerIndex == 0) {
            simPlayerIndex++;
        } else {
            simPlayerIndex--;
        }

        for (Position<Board> p : tree.children(parent)) {
            addSubtrees(tree, p, game);
        }

        simPlayerIndex = temp;

    }

    /*
      Returns the game's player that matches the simulated player index.
    */
    private Player getSimulatedPlayer(Game game) {
        return simPlayerIndex % 2 == 0 ? game.getActivePlayer() : game.getInactivePlayer();
    }

    /**
     * Sorts a map by it's Value (Integer), from lower to higher.
     * 
     * @param unsortMap (Unsorted current map)
     * @return (Sorted map)
     */
    private Map<Position<Board>, Integer> sortByScore(Map<Position<Board>, Integer> unsortMap) {

        List<Map.Entry<Position<Board>, Integer>> list
                = new LinkedList<Map.Entry<Position<Board>, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Position<Board>, Integer>>() {
            public int compare(Map.Entry<Position<Board>, Integer> o1,
                    Map.Entry<Position<Board>, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<Position<Board>, Integer> sortedMap = new LinkedHashMap<Position<Board>, Integer>();
        for (Map.Entry<Position<Board>, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }

    /**
     * This class holds a relation between a board and it's score.
     */
    private class BoardScorePair {

        private Position<Board> board;
        private int score;

        public BoardScorePair(Position<Board> board, int score) {
            this.board = board;
            this.score = score;
        }

        public Position<Board> getBoard() {
            return board;
        }

        public int getScore() {
            return score;
        }

    }

}
