package com.dragon.springframework.core.expression;

import com.dragon.springframework.core.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class ParserContext implements Parser {

    private static final Map<String, Map<Method, Boolean>> CACHE = new ConcurrentHashMap<>(16);

    private final ModifierParser modifierParser = new ModifierParser();

    private final ClassParser classParser = new ClassParser();

    @Override
    public boolean parse(Method target, String expression) {
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
        boolean left, right;
        if (StringUtils.isEmpty(modifier)) {
            return false;
        }
        left = modifierParser.parse(target, modifier);
        right = classParser.parse(target, packageAndMethod);
        isMatch = left && right;
        expressionCache.put(target, isMatch);
        CACHE.put(expression, expressionCache);
        return isMatch;
    }
}
