package com.safehouse.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes

open class PersistentPreferences(val context: Context, preferenceName: String) {
  private var sharedPreferences: SharedPreferences =
    context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

  private fun gp(vararg pref: SharedPreferences): SharedPreferences {
    return if (pref.isNotEmpty()) pref[0] else sharedPreferences
  }

  //
  // Getter for resources
  //
  private fun rstr(@StringRes stringKeyResourceId: Int): String {
    return context.getString(stringKeyResourceId)
  }

  //
  // Getter & Setter for String
  //
  fun setString(@StringRes keyResourceId: Int, value: String, vararg pref: SharedPreferences) {
    gp(*pref).edit().putString(rstr(keyResourceId), value).apply()
  }
  fun setString(keyResourceId: String, value: String, vararg pref: SharedPreferences) {
    gp(*pref).edit().putString((keyResourceId), value).apply()
  }
  fun getString(
    @StringRes keyResourceId: Int,
    defaultValue: String,
    vararg pref: SharedPreferences
  ): String {
    return gp(*pref).getString(rstr(keyResourceId), defaultValue)!!
  }

  fun getString(
    keyResourceId: String,
    defaultValue: String,
    vararg pref: SharedPreferences
  ): String {
    return gp(*pref).getString((keyResourceId), defaultValue)!!
  }

  //
  // Getter & Setter for integer
  //
  fun setInt(key: String, value: Int, vararg pref: SharedPreferences) {
    gp(*pref).edit().putInt(key, value).apply()
  }

  fun setInt(@StringRes keyResourceId: Int, value: Int, vararg pref: SharedPreferences) {
    gp(*pref).edit().putInt(rstr(keyResourceId), value).apply()
  }

  fun getInt(
    @StringRes keyResourceId: Int,
    defaultValue: Int,
    vararg pref: SharedPreferences
  ): Int {
    return gp(*pref).getInt(rstr(keyResourceId), defaultValue)
  }

  fun getInt(key: String, defaultValue: Int, vararg pref: SharedPreferences): Int {
    return gp(*pref).getInt(key, defaultValue)
  }

  //
  // Getter & Setter for Long
  //
  fun setLong(@StringRes keyResourceId: Int, value: Long, vararg pref: SharedPreferences) {
    gp(*pref).edit().putLong(rstr(keyResourceId), value).apply()
  }

  fun getLong(
    @StringRes keyResourceId: Int,
    defaultValue: Long,
    vararg pref: SharedPreferences
  ): Long {
    return gp(*pref).getLong(rstr(keyResourceId), defaultValue)
  }

  //
  // Getter & Setter for boolean
  //
  fun setBool(@StringRes keyResourceId: Int, value: Boolean, vararg pref: SharedPreferences) {
    gp(*pref).edit().putBoolean(rstr(keyResourceId), value).apply()
  }

  private fun setBool(key: String, value: Boolean, vararg pref: SharedPreferences) {
    gp(*pref).edit().putBoolean(key, value).apply()
  }
  private fun setLong(key: String, value: Long, vararg pref: SharedPreferences) {
    gp(*pref).edit().putLong(key, value).apply()
  }

  fun getBool(
    @StringRes keyResourceId: Int,
    defaultValue: Boolean,
    vararg pref: SharedPreferences
  ): Boolean {
    return gp(*pref).getBoolean(rstr(keyResourceId), defaultValue)
  }

  private fun getBool(key: String, defaultValue: Boolean, vararg pref: SharedPreferences): Boolean {
    return gp(*pref).getBoolean(key, defaultValue)
  }
  private fun getLong(key: String, defaultValue: Long, vararg pref: SharedPreferences): Long {
    return gp(*pref).getLong(key, defaultValue)
  }

  fun getBool(key: String, defaultValue: Boolean): Boolean {
    return getBool(key, defaultValue, sharedPreferences)
  }

  fun setBool(key: String, value: Boolean) {
    setBool(key, value, sharedPreferences)
  }

  fun getLong(key: String, defaultValue: Long): Long {
    return getLong(key, defaultValue, sharedPreferences)
  }

  fun setLong(key: String, value: Long) {
    setLong(key, value, sharedPreferences)
  }
}
