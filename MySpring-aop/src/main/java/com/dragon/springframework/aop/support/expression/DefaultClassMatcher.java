package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.core.StringUtils;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 类表达式匹配器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class DefaultClassMatcher implements ClassFilter {

    private static final Map<String, Class<?>> EXPRESSION_INTERFACE_CACHE = new ConcurrentHashMap<>();

    @Setter
    private String expression;

    @Override
    public boolean matches(Class target) {
        return matches(target, this.expression);
    }

    @Override
    public final boolean matches(Class target, String expression) {
        String targetName = target.getName();
        String maxSubString = StringUtils.getMaxSubString(targetName, expression);
        int begin = expression.indexOf(maxSubString);
        String key = expression.substring(begin);
        Class<?> theInterface = EXPRESSION_INTERFACE_CACHE.get(key);
        if (theInterface == null) {
            int length = maxSubString.length();
            while (length < expression.length()) {
                try {
                    theInterface = Class.forName(expression.substring(begin, length - 1));
                    EXPRESSION_INTERFACE_CACHE.put(key, theInterface);
                    break;
                } catch (ClassNotFoundException e) {
                    length = expression.indexOf(".", length) + 1;
                }
            }
        }
        if (theInterface != null &&
                theInterface.isAssignableFrom(target)) {
            ExpressionMatcher.REMAINING_EXPRESSION.set(expression.replaceAll(theInterface.getName() + "\\.", ""));
            return true;
        }
        return classMatch(targetName, expression.replaceAll("\\.\\.", "..."));
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
                return expression.startsWith(ExpressionMatcher.REMAINING_EXPRESSION.get());
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
