package com.safehouse.core.model

interface BasicCallback<T> {
  fun onFinished(response: T)
}
