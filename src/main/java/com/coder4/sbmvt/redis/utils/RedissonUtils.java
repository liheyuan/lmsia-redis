package com.coder4.sbmvt.redis.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author coder4
 */
public class RedissonUtils {

    public static String wrapSchema(String s) {
        return "redis://" + s;
    }

    public static List<String> splitStr(String str) {
        return Arrays.asList(str.split("\\s*,\\s*"));
    }

}