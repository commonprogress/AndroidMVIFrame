package com.base.commonality.utils

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * MMKV使用封装
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * 数据保存关键key
 * 1.token
 */
object MMKVSpUtils {

    /**
     * 初始化
     */
    fun initMMKV(context: Context): String? = MMKV.initialize(context)

    /**
     * 保存数据（简化）
     * 根据value类型自动匹配需要执行的方法
     */
    fun put(key: String, value: Any) =
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            else -> false
        }

    fun putString(key: String, value: String): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getString(key: String, defValue: String): String? =
        MMKV.defaultMMKV()?.decodeString(key, defValue)

    fun putInt(key: String, value: Int): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getInt(key: String, defValue: Int): Int? = MMKV.defaultMMKV()?.decodeInt(key, defValue)

    fun putLong(key: String, value: Long): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getLong(key: String, defValue: Long): Long? = MMKV.defaultMMKV()?.decodeLong(key, defValue)

    fun putDouble(key: String, value: Double): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getDouble(key: String, defValue: Double): Double? =
        MMKV.defaultMMKV()?.decodeDouble(key, defValue)

    fun putFloat(key: String, value: Float): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getFloat(key: String, defValue: Float): Float? =
        MMKV.defaultMMKV()?.decodeFloat(key, defValue)

    fun putBoolean(key: String, value: Boolean): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getBoolean(key: String, defValue: Boolean): Boolean? =
        MMKV.defaultMMKV()?.decodeBool(key, defValue)

    fun putStringSet(key: String, value: Set<String>): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getStringSet(key: String): Set<String>? =
        MMKV.defaultMMKV()?.decodeStringSet(key)

    fun contains(key: String): Boolean? = MMKV.defaultMMKV()?.contains(key)
}