package com.example.design.flyweight.before;

/**
 * 棋手
 *
 * @author SuccessZhang
 * @date 2020/05/26
 */
@SuppressWarnings("unused")
public class Player {

    /**
     * 执棋的颜色
     */
    private final Color color;

    /**
     * 下到哪个棋盘上
     */
    private final GoBoards board;

    public Player(Color color, GoBoards board) {
        this.color = color;
        this.board = board;
    }

    public boolean dropPiece(int x, int y) {
        return this.board.occupy(new Piece(this.color, x, y));
    }
}
