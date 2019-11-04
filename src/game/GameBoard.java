package game;

import java.util.Arrays;

public class GameBoard implements Comparable<GameBoard>{
    private String[][] board;
    private GameBoard parent;
    private String parentMove;
    private int dist;

    public GameBoard(String[][] board, String parentMove, GameBoard parent, int dist){
        this.board = Arrays.copyOf(board, board.length);
        this.parentMove = parentMove;
        this.parent = parent;
        this.dist = dist;
    }
    public String getParentMove() {
        return parentMove;
    }
    public GameBoard getParent() {
        return parent;
    }
    public int getDist() {
        return dist;
    }

    public GameBoard move(String action){
        Cord cord = findZero();
        if (cord == null)
            return null;
        switch (action.toLowerCase()){
            case "right":
                if(cord.getCol() + 1 == board[0].length) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow(), cord.getCol() + 1), action);
                }
            case "left":
                if(cord.getCol() == 0) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow(), cord.getCol() - 1), action);
                }
            case "up":
                if(cord.getRow() == 0) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow() - 1, cord.getCol()), action);
                }
            case "down":
                if(cord.getRow() + 1 == board[0].length) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow() + 1, cord.getCol()), action);
                }
        }
        return null;
    }

    private GameBoard swap(Cord cord1, Cord cord2, String action) {
        String[][] tempBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            tempBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        String temp = tempBoard[cord1.getRow()][cord1.getCol()];
        tempBoard[cord1.getRow()][cord1.getCol()] = tempBoard[cord2.getRow()][cord2.getCol()];
        tempBoard[cord2.getRow()][cord2.getCol()] = temp;
        return new GameBoard(tempBoard, action, this, this.dist + 1);
    }

    private Cord findZero() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].equals(" "))
                    return new Cord(i, j);
            }
        }
        return null;
    }

    public boolean isOnGoalState(){
        int num = 1;
        if(!board[board.length - 1][board[0].length - 1].equals(" "))
            return false;
        for (String[] strings : board) {
            for (String string : strings) {
                if(string.equals(" "))
                    continue;
                if (Integer.parseInt(string) != num++)
                    return false;
            }
        }
        return true;
    }

    public int findHeuristicValue(){
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].equals(" "))
                    continue;
                int num = Integer.parseInt(board[i][j]);
                int rightRow = (num - 1) / board[i].length;
                int rightCol = (num - 1) % board[i].length;
                sum += Math.abs(rightCol - j) + Math.abs(rightRow - i);
            }
        }
        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        GameBoard that;
        if(obj instanceof GameBoard)
            that = (GameBoard) obj;
        else
            return false;
        if(that.board.length != this.board.length
            || that.board[0].length != this.board[0].length)
            return false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(!that.board[i][j].equals(board[i][j]))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public int compareTo(GameBoard o) {
        return Integer.compare(findHeuristicValue() + dist, o.findHeuristicValue() + dist);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                builder.append(board[i][j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
