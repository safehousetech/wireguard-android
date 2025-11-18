package com.safehouse.core.api

interface BreachApiResponseV2 {
  fun onSuccess(breachListSize: Int)
  fun onError()
}
