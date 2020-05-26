package com.example.design.flyweight.before;

import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class FlyweightBeforeTest {
    public static void main(String[] args) {
        GoBoards board = new GoBoards(9);
        Player player1 = new Player(Color.WHITE, board);
        Player player2 = new Player(Color.BLACK, board);
        System.out.println(player1.dropPiece(5, 5));
        System.out.println(player2.dropPiece(8, 8));
        System.out.println(player1.dropPiece(4, 4));
        System.out.println(player2.dropPiece(6, 7));
        for (Piece[] line : board.getBoard()) {
            System.out.println(Arrays.toString(line));
        }
    }
}
