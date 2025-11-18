package com.safehouse.core.model

interface SHAPIResponseCallback<T> {
    fun onResponseSuccess(t: T)
    fun onResponseFailure(error: String)
}