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

    protected Color color;

    protected int x;

    protected int y;

    protected AbstractPiece(Color color) {
        this.color = color;
    }

    public AbstractPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
