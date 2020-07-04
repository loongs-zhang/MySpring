package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.core.StringUtils;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * 方法表达式匹配器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class DefaultMethodMatcher implements MethodMatcher {

    @Setter
    private String expression;

    @Override
    public boolean matches(Method target) {
        return matches(target, this.expression);
    }

    @Override
    public boolean matches(Method target, String expression) {
        if (StringUtils.isEmpty(expression)) {
            return false;
        }
        expression = expression.replaceAll(" ", "");
        boolean nameMatch = false;
        if (expression.startsWith("*")) {
            nameMatch = true;
        }
        int indexOf = expression.indexOf("(");
        if (!nameMatch) {
            String targetName = target.getName();
            if (targetName.equals(expression.substring(0, indexOf))) {
                nameMatch = true;
            }
        }
        String argsType = expression.substring(indexOf + 1, expression.length() - 1);
        Class<?>[] parameterTypes = target.getParameterTypes();
        for (int i = 0; i < parameterTypes.length - 1; i++) {
            String type = parameterTypes[i].getName() + ",";
            if (argsType.startsWith(type)) {
                argsType = argsType.substring(type.length());
            }
        }
        boolean argsTypeMatch;
        if ("..".equals(argsType)) {
            argsTypeMatch = true;
        } else {
            argsTypeMatch = parameterTypes[parameterTypes.length - 1].getName().equals(argsType);
        }
        return nameMatch && argsTypeMatch;
    }
}
