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

public class HardComputerMove implements ComputerMoveStrategy {

    private int simPlayerIndex;

    @Override
    public Edge<Connection, Joint> calculateMove(Game game) {
        
        
        if(game.getRounds().size() < 4){
            ArrayList<Edge<Connection, Joint>> possibleMoves = game.getBoard().getPossibleMoves();
            return possibleMoves.get(new Random().nextInt(possibleMoves.size()));
        }

        simPlayerIndex = 0;
        Map<Position<Board>, Integer> results = new HashMap<>();

        Board currentBoard = game.getBoard().copy();
        LinkedTree<Board> tree = new LinkedTree<>(currentBoard);

        Position<Board> root = tree.root();

        long start = System.currentTimeMillis();
        
        
        addSubtrees(tree, root, game);
        System.out.println(System.currentTimeMillis() - start);
        start  = System.currentTimeMillis();

        for (Position<Board> board : tree.depthOrder()) {
            if (tree.isExternal(board)) {
                int height = tree.height();
                Position<Board> current = board;

                while (!tree.isRoot(current)) {
                    int score = /*evaluate(tree, current, height);*/ 50;
                    results.put(current, score);
                    height--;
                    current = tree.parent(current);
                }

            }
        }

        System.out.println(System.currentTimeMillis() - start);
        start  = System.currentTimeMillis();
        
        results = sortByScore(results);


        for (Map.Entry<Position<Board>, Integer> entry : results.entrySet()) {

            Position<Board> prev = null;
            Position<Board> next = entry.getKey();
            while (!tree.isRoot(next)) {
                prev = next;
                next = tree.parent(next);
            }

            for (Edge<Connection, Joint> edge : prev.element().edges()) {
                if (edge.element().getSelector() instanceof Machine) {
                    Edge<Connection, Joint>  chosen =  game.getBoard().findIdentical(edge);
                    if(chosen.element().isSelected())
                        continue;
                    return chosen;
                }
            }

        }

        return null;

    }

    public int evaluate(LinkedTree<Board> tree, Position<Board> parent, int height) {

        int score = 25 * height;
        
        if(parent.element().numSelectedEdges() < 5)
            return score;

        ArrayList<Edge<Connection, Joint>> edges = parent.element().checkAnyTriangle();

        if (!edges.isEmpty()) {

            if (edges.get(0).element().getSelector() instanceof Machine) {
                score = 100 * height;
            } else {
                score = 1 * height;
            }

        }

        return score;

    }

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

    private Player getSimulatedPlayer(Game game) {
        return simPlayerIndex % 2 == 0 ? game.getActivePlayer() : game.getInactivePlayer();
    }

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

}
