package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.core.StringUtils;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 方法表达式匹配器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@NoArgsConstructor
public class DefaultMethodMatcher implements MethodMatcher {

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
        if (StringUtils.isEmpty(expression)) {
            return false;
        }
        if (!ExpressionMatcher.MODIFIER_MATCHED.get()) {
            //表达式完全没有匹配过
            return ExpressionMatcher.getInstance()
                    .matches(target, expression);
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
        Pattern pattern = Pattern.compile("\\.\\.+");
        if (pattern.matcher(argsType).matches()) {
            argsTypeMatch = true;
        } else {
            argsTypeMatch = parameterTypes[parameterTypes.length - 1].getName().equals(argsType);
        }
        return nameMatch && argsTypeMatch;
    }
}
