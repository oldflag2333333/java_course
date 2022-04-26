package com.example.course13;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author oldFlag
 * @since 2022/4/25
 */
public class GsonUtil {

    private static final Gson GSON = new Gson();

    public static <T> List<T> toList(String json) {
        return GSON.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T> T toObject(String json) {
        return GSON.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }


}
