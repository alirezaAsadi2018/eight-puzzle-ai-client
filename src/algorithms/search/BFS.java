package algorithms.search;

import algorithms.Algorithm;
import game.Board;

import java.util.*;

public class BFS implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();
    @Override
    public String makeMove(String[][] grid) {
        if(!moveSequence.isEmpty())
            return moveSequence.pop();
        Set<Board> visited = new HashSet<>();
        List<Board> list = new LinkedList<>();
        Board board = new Board(grid, null);
        Board destBoard = null;
        list.add(board);
        visited.add(board);
        while (list.size() != 0 && !list.get(0).isOnGoalState()){
            board = list.get(0);
            list.remove(0);
            String[] moves = {"left", "right", "up", "down"};
            for (String move : moves) {
                destBoard = board.move(move);
                if(destBoard == null)
                    continue;
                if(destBoard.isOnGoalState())
                    break;
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
            }
            return moveSequence.pop();
        }
    }
}
