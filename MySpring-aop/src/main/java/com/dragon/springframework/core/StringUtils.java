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

    /**
     * 获取最大公共子串。
     */
    public static String getMaxSubString(String sting1, String sting2) {
        //1.先找到最大的字符串和最小的字符串。 根据长度进行比较
        String max = sting1.length() > sting2.length() ? sting1 : sting2;
        String min = sting1.equals(max) ? sting2 : sting1;

        //2.求出最小的那个的长度。 根据这个长度，进行相应的循环。
        int minLength = min.length();
        //3.如果整个包含的话，那个就不用循环判断了。
        if (max.contains(min)) {
            return min;
        }
        //3.开始进行相关的循环操作了。
        for (int i = 0; i < minLength; i++) {
            // 从开头处开始,最后的位置是minLength; 因为subString 截取时不到后面的那个参数，所以这里是<=minLength;
            for (int start = 0, end = minLength - i; end <= minLength; start++, end++) {
                /*
                  第一次循环时， 先看整个是否进行了包含，去掉0位，即defg 是否在abcdef 里面。
                  第二次循环时，要把defg 去掉一位，看是否在abcdef 里面。 截取时，有def 和efg 两种。
                  第三次循环时，把defg 去掉两位。 截取有: de ef fg 三种方式。
                  第四次循环时， 把defg 去掉三位，有 d e f g 四种方式。 如果还不存在，则说明没有相同的子串。
                  外层循环 为最小的字符串的长度。 0~ length_1
                  内层循环为: 从0 开始，结束位置为 length-i, 判断是否在，如果不再，则进行整体移动， 为1 ~length-i+1,2~ length-i+2
                  直到 end 结束位置为 length , 因为substring 时不会取出最后的索引值。
                 */
                String temp = min.substring(start, end);
                if (max.contains(temp)) {
                    return temp;
                }
            }
        }
        return "";
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
