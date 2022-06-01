package com.sdms.common.util;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义的字符串工具
 */
@SuppressWarnings("unused")
public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * @param str 接受判断的字符串
     * @return 当字符串对象为null或者字符串为""时返回true
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * @param str 接受判断的字符串
     * @return 当字符串由纯数字构成时返回true, 例如 "123"
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str 接受判断的字符串
     * @return 当字符串为null 或 "" 或 仅由空白字符构成时,返回true, 例如 "            "
     */
    public static boolean isBlankOrNull(String str) {
        return !hasText(str);
    }

    /**
     * @param str 接受转换的字符串
     * @return 转化成功返回Long类型, 若失败则返回null
     */
    public static Long parseLong(String str) {
        if (isBlankOrNull(str)) {
            return null;
        }
        long number;
        try {
            number = Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
        return number;
    }

    /**
     * 将逗号分割的字符串转换成Long数组类型,例如: "1,34,2,3,5,6" => [1,34,2,3,5,6]
     *
     * @param str 接受转换的字符串
     * @return 返回Long[]类型, 且必不为null, 转化失败会返回空数组
     */
    public static Long[] parseLongArray(String str) {
        final List<Long> longList = parseLongList(str);
        final Long[] numbers = new Long[longList.size()];
        return longList.toArray(numbers);
    }

    /**
     * 将逗号分割的字符串转换成Long列表类型,例如: "1,34,2,3,5,6" => [1,34,2,3,5,6]
     *
     * @param str 接受转换的字符串
     * @return 返回List<Long>类型, 且必不为null, 转化失败会返回空列表
     */
    public static List<Long> parseLongList(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        final String[] strings = trimAllWhitespace(str).split(",");
        final List<Long> longList = new LinkedList<>();
        for (String string : strings) {
            if (!"".equals(string)) {
                final Long number = parseLong(string);
                if (number != null) {
                    longList.add(number);
                } else {
                    return Collections.emptyList();
                }
            }
        }
        return longList;
    }

    /**
     * 将逗号分割的字符串转换成字符串数组类型,例如: "1,34,2,3,5,6" => ["1","34","2","3","5","6"]
     * 注意: 空格和空串不会出现在转换结果中
     *
     * @param str 接受转换的字符串
     * @return 返回String[]类型, 且必不为null, 转化失败会返回空数组
     */
    public static String[] parseStringArray(String str) {
        final List<String> stringList = parseStringList(str);
        final String[] stringArray = new String[stringList.size()];
        return stringList.toArray(stringArray);
    }

    /**
     * 将逗号分割的字符串转换成字符串列表类型,例如: "1,34,2,3,5,6" => ["1","34","2","3","5","6"]
     * 注意: 空格和空串不会出现在转换结果中
     *
     * @param str 接受转换的字符串
     * @return 返回List<String>类型, 且必不为null, 转化失败会返回空列表
     */
    public static List<String> parseStringList(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        final String[] strings = trimAllWhitespace(str).split(",");
        final List<String> stringList = new LinkedList<>();
        for (String string : strings) {
            if (!"".equals(string)) {
                stringList.add(string);
            }
        }
        return stringList;
    }

    /**
     * 去除字符串中所有的空白字符,例如：空格、回车、制表符...
     *
     * @param str 待处理的字符串
     * @return 去除了空白字符的字符串
     */
    @Nonnull
    public static String trimAllWhitespace(@Nonnull String str) {
        return org.springframework.util.StringUtils.trimAllWhitespace(str);
    }
}