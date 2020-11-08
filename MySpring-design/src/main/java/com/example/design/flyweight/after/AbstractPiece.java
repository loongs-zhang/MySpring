package com.example.design.flyweight.after;

import com.example.design.flyweight.before.Color;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@ToString(of = "color")
public abstract class AbstractPiece {

    /**
     * 棋子的颜色
     */
    protected Color color;

    /**
     * 棋子的x轴坐标
     */
    protected Integer x;

    /**
     * 棋子的y轴坐标
     */
    protected Integer y;

    /**
     * 共享对象的构造方法
     */
    protected AbstractPiece(Color color) {
        this.color = color;
    }

    /**
     * 非共享对象的构造方法
     */
    public AbstractPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
