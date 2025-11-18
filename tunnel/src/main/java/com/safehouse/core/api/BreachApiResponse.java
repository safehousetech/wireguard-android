package com.safehouse.core.api;

public interface BreachApiResponse {
  void onSuccess(String token, int breachListSize);

  void onError(String token);
}
