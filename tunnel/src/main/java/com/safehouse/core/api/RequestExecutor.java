package com.safehouse.core.api;

public interface RequestExecutor<T> {
  public ApiResponse<T> executeRequest(Boolean isMoreTimeNeeded);
}
