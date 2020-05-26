package com.example.design.flyweight.after;

import com.example.design.flyweight.before.Color;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@SuppressWarnings("unused")
public class Player2 {

    private final Color color;

    private final GoBoards2 board;

    public Player2(Color color, GoBoards2 board) {
        this.color = color;
        this.board = board;
    }

    public boolean dropPiece(int x, int y) {
        return this.board.encroach(PieceFactory.create(this.color), new Location(x, y));
    }
}
