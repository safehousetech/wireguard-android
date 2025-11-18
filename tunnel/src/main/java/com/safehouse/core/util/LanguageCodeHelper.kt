package com.safehouse.core.util

import java.util.Locale

object LanguageCodeHelper {
  fun getLanguageCode(): String {
    return Locale.getDefault().language ?: "en"
  }
}
