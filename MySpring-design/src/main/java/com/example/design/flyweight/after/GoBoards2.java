package com.example.design.flyweight.after;

import lombok.Getter;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@SuppressWarnings("unused")
public class GoBoards2 {

    private final AbstractPiece[][] board;

    public GoBoards2(int lines) {
        this.board = new Piece2[lines][lines];
    }

    public boolean occupy(AbstractPiece piece, Location location) {
        int x = location.getX();
        int y = location.getY();
        if (board[x][y] == null) {
            board[x][y] = piece;
            return true;
        }
        return false;
    }
}
