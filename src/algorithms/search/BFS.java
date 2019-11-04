package algorithms.search;

import algorithms.Algorithm;
import game.GameBoard;

import java.util.*;

public class BFS implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();
    @Override
    public String makeMove(String[][] grid) {
        if(!moveSequence.isEmpty())
            return moveSequence.pop();
        Set<GameBoard> visited = new HashSet<>();
        List<GameBoard> list = new LinkedList<>();
        GameBoard board = new GameBoard(grid, null, null, 0);
        GameBoard destBoard = null;
        list.add(board);
        visited.add(board);
        OUTER:
        while (list.size() != 0 && !list.get(0).isOnGoalState()){
            board = list.get(0);
            list.remove(0);
            String[] moves = {"left", "right", "up", "down"};
            for (String move : moves) {
                destBoard = board.move(move);
                if(destBoard == null)
                    continue;
                if(destBoard.isOnGoalState())
                    break OUTER;
                if(!visited.contains(destBoard)) {
                    list.add(destBoard);
                    visited.add(destBoard);
                }
            }
        }
        if(destBoard == null)
            return null;
        else{
            while (destBoard.getParentMove() != null){
                moveSequence.add(destBoard.getParentMove());
                destBoard = destBoard.getParent();
            }
            return moveSequence.pop();
        }
    }
}
