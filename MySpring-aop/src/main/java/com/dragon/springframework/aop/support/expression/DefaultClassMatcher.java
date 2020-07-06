package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.core.StringUtils;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * 类表达式匹配器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@NoArgsConstructor
public class DefaultClassMatcher implements ClassFilter {

    private final ThreadLocal<String> expression = ThreadLocal.withInitial(() -> "");

    public void setExpression(String expression) {
        this.expression.set(expression);
    }

    @Override
    public boolean matches(Class target) {
        try {
            return matches(target, this.expression.get());
        } finally {
            this.expression.remove();
        }
    }

    @Override
    public final boolean matches(Class target, String expression) {
        String targetName = target.getName();
        String leftSubString = StringUtils.getLeftSubString(targetName, expression);
        int begin = expression.indexOf(leftSubString);
        expression = expression.substring(begin)
                .replaceAll("\\.\\.", "...");
        boolean result = classMatch(targetName, expression);
        for (Class anInterface : target.getInterfaces()) {
            result = result || classMatch(anInterface.getName(), expression);
        }
        return result;
    }

    private boolean classMatch(String target, String expression) {
        if (expression.startsWith("..")) {
            int indexOf = expression.indexOf("..");
            expression = expression.substring(indexOf + 2);
            int expressionIndexOf = expression.indexOf(".");
            String expressionPackage = expression.substring(0, expressionIndexOf);
            int targetIndexOf = target.indexOf(expressionPackage);
            expressionIndexOf = expression.indexOf(expressionPackage);
            int length = expressionPackage.length();
            if (targetIndexOf == -1) {
                String regex = expressionPackage.replaceAll("\\*", ".*");
                Pattern pattern = Pattern.compile(regex);
                if (pattern.matcher(target).matches()) {
                    ExpressionMatcher.REMAINING_EXPRESSION.set(expression.substring(expressionIndexOf + length + 1));
                    return true;
                }
                return false;
            }
            if (targetIndexOf + length + 1 > target.length()) {
                return true;
            }
            return classMatch(target.substring(targetIndexOf + length + 1), expression.substring(expressionIndexOf + length + 1));
        }
        int targetIndexOf = target.indexOf(".");
        int expressionIndexOf = expression.indexOf(".");
        if (targetIndexOf == -1) {
            Pattern pattern = Pattern.compile(expression.substring(0, expressionIndexOf)
                    .replaceAll("\\*", ".*"));
            ExpressionMatcher.REMAINING_EXPRESSION.set(expression.substring(expressionIndexOf + 1));
            return pattern.matcher(target).matches();
        }
        String targetPackage = target.substring(0, targetIndexOf);
        String expressionPackage = expression.substring(0, expressionIndexOf);
        if (!targetPackage.equals(expressionPackage)) {
            return false;
        }
        return classMatch(target.substring(targetIndexOf + 1), expression.substring(expressionIndexOf + 1));
    }
}
