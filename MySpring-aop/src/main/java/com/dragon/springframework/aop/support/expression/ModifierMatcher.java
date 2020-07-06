package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.MethodMatcher;

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
public class ModifierMatcher implements MethodMatcher {

    private final ThreadLocal<String> expression = ThreadLocal.withInitial(() -> "");

    public void setExpression(String expression) {
        this.expression.set(expression);
    }

    @Override
    public boolean matches(Method target) {
        try {
            return matches(target, this.expression.get());
        } finally {
            this.expression.remove();
        }
    }

    @Override
    public boolean matches(Method target, String expression) {
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
