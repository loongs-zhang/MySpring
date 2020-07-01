package com.dragon.springframework.core;

/**
 * @author SuccessZhang
 * @date 2020/06/07
 */
@SuppressWarnings("unused")
public class StringUtils {

    public static String lowerFirstCase(String simpleName) {
        //beanName首字母小写，原理ASCII码
        char[] chars = simpleName.toCharArray();
        if (65 <= chars[0] && chars[0] <= 90) {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    /**
     * 判断字符串是否为空且长度为0
     *
     * @param str 字符串
     * @return 为空或0返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean hasText(CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将数组转换为指定“界限”的字符串。
     */
    public static String arrayToDelimitedString(Object[] arr, String delimit) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        }
        if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delimit);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static String handleRegexSpecialSymbols(String string) {
        return string.replace("\\", "\\\\")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("?", "\\?")
                .replace(",", "\\,")
                .replace(".", "\\.")
                .replace("&", "\\&")
                .replaceAll("\n", "<br>");
    }
}
