package com.dragon.springframework.core.expression;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 类表达式解析器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class ClassParser implements Parser {

    private final MethodParser methodParser = new MethodParser();

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public boolean parse(Method target, String expression) {
        int indexOf = expression.indexOf(target.getName());
        if (indexOf != -1) {
            threadLocal.set(expression.substring(indexOf));
        }
        String targetClassName = target.getDeclaringClass().getName();
        if (classMatch(targetClassName, expression.replaceAll("\\.\\.", "..."))) {
            try {
                return methodParser.parse(target, threadLocal.get());
            } finally {
                threadLocal.remove();
            }
        }
        return false;
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
                    threadLocal.set(expression.substring(expressionIndexOf + length + 1));
                    return true;
                }
                return expression.startsWith(threadLocal.get());
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
