package com.example.course13.db;

import org.apache.commons.lang3.StringUtils;

/**
 * @author oldFlag
 * @since 2022/4/26
 */
public class DBContextHolder {

    private final static ThreadLocal<String> context = new ThreadLocal<>();

    public static String getContext() {
        return context.get();
    }

    public static void setContext(String content) {
        String c = context.get();
        if (c == null) {
            context.set(content);
        } else {
            if (!StringUtils.equals(DBConstants.MASTER, c)) {
                context.set(content);
            }
        }
    }

    public static void clear() {
        context.remove();
    }

}
