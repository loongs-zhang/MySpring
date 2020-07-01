package com.dragon.springframework.core.expression;

import java.lang.reflect.Method;

/**
 * 方法表达式解析器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class MethodParser implements Parser {
    @Override
    public boolean parse(Method target, String expression) {
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
        String argsType = expression.substring(indexOf);
        StringBuilder sb = new StringBuilder("(");
        Class<?>[] parameterTypes = target.getParameterTypes();
        for (int i = 0; i < parameterTypes.length - 1; i++) {
            sb.append(parameterTypes[i].getName()).append(",");
        }
        sb.append(parameterTypes[parameterTypes.length - 1].getName());
        sb.append(")");
        boolean argsTypeMatch = sb.toString().equals(argsType);
        return nameMatch && argsTypeMatch;
    }
}
