package com.example.design.flyweight.before;

import lombok.Getter;
import lombok.ToString;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@ToString(of = "color")
@SuppressWarnings("unused")
public class Piece {

    private final Color color;

    private final int x;

    private final int y;

    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
}
