package game;

import java.util.Arrays;

public class Board {
    private String[][] board;
    private String parentMove;

    public Board(String[][] board, String parentMove){
        this.board = Arrays.copyOf(board, board.length);
        this.parentMove = parentMove;
    }
    public String getParentMove() {
        return parentMove;
    }
    public Board move(String action){
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
                if(cord.getCol() + 1 == 1) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow(), cord.getCol() - 1), action);
                }
            case "up":
                if(cord.getRow() + 1 == 1) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow() + 1, cord.getCol()), action);
                }
            case "down":
                if(cord.getRow() + 1 == board[0].length) {
                    return null;
                }else {
                    return swap(cord, new Cord(cord.getRow() - 1, cord.getCol()), action);
                }
        }
        return null;
    }

    private Board swap(Cord cord1, Cord cord2, String action) {
        String[][] tempBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            tempBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        String temp = tempBoard[cord1.getRow()][cord1.getCol()];
        tempBoard[cord1.getRow()][cord1.getCol()] = tempBoard[cord2.getRow()][cord2.getCol()];
        tempBoard[cord2.getRow()][cord2.getCol()] = temp;
        return new Board(tempBoard, action);
    }

    private Cord findZero() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].equals("0"))
                    return new Cord(i, j);
            }
        }
        return null;
    }

    public boolean isOnGoalState(){
        int num = 1;
        for (String[] strings : board) {
            for (String string : strings) {
                if (Integer.parseInt(string) != num++)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        Board that;
        if(obj instanceof Board)
            that = (Board) obj;
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
}
