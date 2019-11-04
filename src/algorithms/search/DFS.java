package algorithms.search;

import algorithms.Algorithm;
import game.Board;

import java.util.*;

public class DFS implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();
    private Set<Board> visited = new HashSet<>();

    @Override
    public String makeMove(String[][] grid) {
        if(!moveSequence.isEmpty())
            return moveSequence.pop();
        Board board = new Board(grid, null);
        Board goal = dfs(board);
        if(goal == null)
            return null;
        else{
            while (goal.getParentMove() != null)
                moveSequence.add(goal.getParentMove());
            return moveSequence.pop();
        }
    }

    private Board dfs(Board board){
        if(board.isOnGoalState())
            return board;
        visited.add(board);
        String[] moves = {"left", "right", "up", "down"};
        for (String move : moves) {
            Board destBoard = board.move(move);
            if(destBoard == null)
                continue;
            if(destBoard.isOnGoalState()) {
                return board;
            }
            if(!visited.contains(destBoard)) {
                visited.add(destBoard);
                return dfs(destBoard);
            }
        }
        return null;
    }
}
