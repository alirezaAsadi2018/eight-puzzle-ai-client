package algorithms.search;

import algorithms.Algorithm;
import game.GameBoard;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class IDAStar implements Algorithm {
    private Stack<String> moveSequence = new Stack<>();

    @Override
    public String makeMove(String[][] grid) {
        if (!moveSequence.isEmpty())
            return moveSequence.pop();
        int maxCost = 5;
        GameBoard destBoard;
        OUTER:
        while (true) {
            Set<GameBoard> visited = new HashSet<>();
            PriorityQueue<GameBoard> queue = new PriorityQueue<>();
            GameBoard board = new GameBoard(grid, null, null, 0);
            queue.add(board);
            visited.add(board);
            while (queue.size() != 0) {
                board = queue.poll();
                if (board == null)
                    break;
                String[] moves = {"left", "right", "up", "down"};
                for (String move : moves) {
                    destBoard = board.move(move);
                    if (destBoard == null)
                        continue;
                    if (destBoard.isOnGoalState())
                        break OUTER;
                    if (!visited.contains(destBoard)
                            && destBoard.findHeuristicValue() <= maxCost) {
                        queue.add(destBoard);
                        visited.add(destBoard);
                    }
                }
            }
            maxCost += 5;
        }
        while (destBoard.getParentMove() != null) {
            moveSequence.add(destBoard.getParentMove());
            destBoard = destBoard.getParent();
        }
        return moveSequence.pop();
    }
}
