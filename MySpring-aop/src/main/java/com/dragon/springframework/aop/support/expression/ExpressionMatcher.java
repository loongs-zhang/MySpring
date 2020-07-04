package com.dragon.springframework.aop.support.expression;

import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.core.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表达式匹配器，也是上下文对象。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionMatcher implements MethodMatcher, ClassFilter {

    private static final Map<String, Map<Method, Boolean>> CACHE = new ConcurrentHashMap<>(16);

    @Getter
    private final ModifierMatcher modifierMatcher = new ModifierMatcher();

    @Getter
    private final DefaultClassMatcher classMatcher = new DefaultClassMatcher();

    @Getter
    private final DefaultMethodMatcher methodMatcher = new DefaultMethodMatcher();

    @Setter
    private String expression;

    static final ThreadLocal<String> REMAINING_EXPRESSION = new ThreadLocal<>();

    @Override
    public boolean matches(Method target, String expression) {
        Map<Method, Boolean> expressionCache = CACHE.get(expression);
        if (expressionCache == null) {
            expressionCache = new ConcurrentHashMap<>(64);
        }
        Boolean isMatch = expressionCache.get(target);
        if (isMatch != null) {
            return isMatch;
        }
        int splitter = expression.lastIndexOf(" ");
        String modifier = expression.substring(0, splitter);
        String packageAndMethod = expression.substring(splitter + 1);
        boolean left, middle = false, right = false;
        if (StringUtils.isEmpty(modifier)) {
            return false;
        }
        left = modifierMatcher.matches(target, modifier);
        if (left) {
            middle = classMatcher.matches(target.getDeclaringClass(), packageAndMethod.replaceAll(" ", ""));
            int indexOf = expression.indexOf(target.getName());
            if (indexOf != -1) {
                REMAINING_EXPRESSION.set(expression.substring(indexOf));
            }
        }
        if (middle) {
            try {
                right = methodMatcher.matches(target, REMAINING_EXPRESSION.get());
            } finally {
                REMAINING_EXPRESSION.remove();
            }
        }
        isMatch = left && middle && right;
        expressionCache.put(target, isMatch);
        CACHE.put(expression, expressionCache);
        return isMatch;
    }

    @Override
    public boolean matches(Method target) {
        return matches(target, this.expression);
    }

    @Override
    public boolean matches(Class target, String expression) {
        return this.classMatcher.matches(target, expression);
    }

    @Override
    public boolean matches(Class target) {
        return matches(target, this.expression);
    }
}
