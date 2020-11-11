package com.example.design.flyweight.after;

import com.example.design.flyweight.before.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 *
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class PieceFactory {

    private static final Color[] COLORS = Color.values();

    private static final Map<Color, AbstractPiece> MAP = new HashMap<>(COLORS.length);

    static {
        for (Color color : COLORS) {
            MAP.put(color, new Piece2(color));
        }
    }

    public static AbstractPiece create(Color color) {
        AbstractPiece piece = MAP.get(color);
        System.out.println("从缓存中取到" + piece.toString());
        return piece;
    }
}
