package algorithms.search;

import algorithms.Algorithm;
import game.GameBoard;

import java.util.*;

public class AStar implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();

    @Override
    public String makeMove(String[][] grid) {
        if(!moveSequence.isEmpty())
            return moveSequence.pop();
        Set<GameBoard> visited = new HashSet<>();
        PriorityQueue<GameBoard> queue = new PriorityQueue<>();
        GameBoard board = new GameBoard(grid, null, null, 0);
        GameBoard destBoard = null;
        queue.add(board);
        visited.add(board);
        OUTER:
        while (queue.size() != 0){
            board = queue.poll();
            if(board == null)
                break;
            String[] moves = {"left", "right", "up", "down"};
            for (String move : moves) {
                destBoard = board.move(move);
                if(destBoard == null)
                    continue;
                if(destBoard.isOnGoalState())
                    break OUTER;
                if(!visited.contains(destBoard)) {
                    queue.add(destBoard);
                    visited.add(destBoard);
                }
            }
        }
        if(destBoard == null) {
            return null;
        }
        else{
            while (destBoard.getParentMove() != null){
                moveSequence.add(destBoard.getParentMove());
                destBoard = destBoard.getParent();
            }
            return moveSequence.pop();
        }
    }

}
