package com.example.course13.db;

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
        context.set(content);
    }

    public static void clear() {
        context.remove();
    }

}
