package com.example.design.flyweight.before;

import lombok.Getter;

/**
 * 围棋棋盘
 *
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@SuppressWarnings("unused")
public class GoBoards {

    /**
     * 用棋子的二维数组来表示棋盘
     */
    private final Piece[][] board;

    public GoBoards(int lines) {
        board = new Piece[lines][lines];
    }

    public boolean occupy(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();
        if (board[x][y] == null) {
            board[x][y] = piece;
            return true;
        }
        return false;
    }
}
