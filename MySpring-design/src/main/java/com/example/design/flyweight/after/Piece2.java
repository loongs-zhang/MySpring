package com.example.design.flyweight.after;

import com.example.design.flyweight.before.Color;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
@Getter
@ToString(callSuper = true)
@SuppressWarnings("unused")
public class Piece2 extends AbstractPiece {

    protected Piece2(Color color) {
        super(color);
    }
}
