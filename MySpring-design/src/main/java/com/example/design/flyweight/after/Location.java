package com.example.design.flyweight.after;

/**
 * 不能共享的棋子位置对象
 *
 * @author SuccessZhang
 * @date 2020/05/26
 */
@SuppressWarnings("unused")
public class Location extends AbstractPiece {

    public Location(int x, int y) {
        super(x, y);
    }
}
