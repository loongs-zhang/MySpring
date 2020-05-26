package com.example.design.flyweight.before;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@SuppressWarnings("unused")
public class Player {

    private final Color color;

    private final GoBoards board;

    public Player(Color color, GoBoards board) {
        this.color = color;
        this.board = board;
    }

    public boolean dropPiece(int x, int y) {
        return this.board.encroach(new Piece(this.color, x, y));
    }
}
