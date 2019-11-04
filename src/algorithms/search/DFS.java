package algorithms.search;

import algorithms.Algorithm;
import game.Board;
import game.GameBoard;

import java.util.*;

public class DFS implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();
    private Set<GameBoard> visited = new HashSet<>();
    private boolean ok;

    @Override
    public String makeMove(String[][] grid) {
        if(!moveSequence.isEmpty())
            return moveSequence.pop();
        if(ok)
            System.exit(0);
        GameBoard board = new GameBoard(grid, null, null, 0);
        GameBoard goal = dfs(board, 0);
        if(goal == null)
            return null;
        else{
            ok = true;
            while (goal.getParentMove() != null){
                moveSequence.add(goal.getParentMove());
                goal = goal.getParent();
            }
            return moveSequence.pop();
        }
    }

    private GameBoard dfs(GameBoard board, int depth){
        visited.add(board);
        if(board.isOnGoalState() || depth == 300)
            return board;
        String[] moves = {"left", "right", "up", "down"};
        for (String move : moves) {
            GameBoard destBoard = board.move(move);
            if(destBoard == null)
                continue;
            if(!visited.contains(destBoard)) {
                GameBoard ans = dfs(destBoard, depth + 1);
                if(ans != null) {
                    return ans;
                }
            }
        }
        return null;
    }
}
