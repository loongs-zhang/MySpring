package com.dragon.springframework.core.expression;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * 修饰符解析器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class ModifierParser implements Parser {
    @Override
    public boolean parse(Method target, String expression) {
        if ("*".equals(expression)) {
            return true;
        }
        List<String> modifiers = Arrays.asList(
                Modifier.toString(target.getModifiers())
                        .split(" "));
        for (String modifier : expression.split(" ")) {
            if (!modifiers.contains(modifier)) {
                return false;
            }
        }
        return true;
    }
}
