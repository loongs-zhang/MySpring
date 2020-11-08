package com.example.design.flyweight.before;

import lombok.Getter;
import lombok.ToString;

/**
 * 围棋棋子
 *
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@ToString(of = "color")
@SuppressWarnings("unused")
public class Piece {

    /**
     * 棋子的颜色
     */
    private final Color color;

    /**
     * 棋子的x轴坐标
     */
    private final int x;

    /**
     * 棋子的y轴坐标
     */
    private final int y;

    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
}
