package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.appconfig.MyApplication;


public class SPUtil {

    private SPUtil() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * SP的name值
     * <p>可通过修改PREFERENCE_NAME变量修改SP的name值</p>
     */
    private static final String PREFERENCE_NAME = "ANDROID_UTIL_CODE";

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean putString(String key, String value) {
        return getSP().edit().putString(key, value).commit();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值null
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static String getString(String key, String defaultValue) {
        return getSP().getString(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean putInt(String key, int value) {
        return getSP().edit().putInt(key, value).commit();
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static int getInt(String key, int defaultValue) {
        return getSP().getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean putLong(String key, long value) {
        return getSP().edit().putLong(key, value).commit();
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static long getLong(String key, long defaultValue) {
        return getSP().getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean putFloat(String key, float value) {
        return getSP().edit().putFloat(key, value).commit();
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static float getFloat(String key, float defaultValue) {
        return getSP().getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean putBoolean(String key, boolean value) {
        return getSP().edit().putBoolean(key, value).commit();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值false
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSP().getBoolean(key, defaultValue);
    }

    /**
     * 获取name为PREFERENCE_NAME的SP对象
     * * @return SP
     */
    private static SharedPreferences getSP() {
        return MyApplication.appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean cleanSP() {

        return getSP().edit().clear().commit();
    }
}
