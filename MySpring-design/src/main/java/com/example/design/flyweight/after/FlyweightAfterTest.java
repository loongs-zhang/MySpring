package com.example.design.flyweight.after;

import com.example.design.flyweight.before.Color;

import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class FlyweightAfterTest {
    public static void main(String[] args) {
        GoBoards2 board = new GoBoards2(9);
        Player2 player1 = new Player2(Color.WHITE, board);
        Player2 player2 = new Player2(Color.BLACK, board);
        System.out.println(player1.dropPiece(5, 5));
        System.out.println(player2.dropPiece(8, 8));
        System.out.println(player1.dropPiece(4, 4));
        System.out.println(player2.dropPiece(6, 7));
        for (AbstractPiece[] line : board.getBoard()) {
            System.out.println(Arrays.toString(line));
        }
    }
}
