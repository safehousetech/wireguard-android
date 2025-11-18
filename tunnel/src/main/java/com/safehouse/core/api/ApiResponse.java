package com.safehouse.core.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class ApiResponse<T> {

  @NonNull public final Status status;

  @Nullable public final String message;

  @Nullable public final T data;

  public int statusCode;

  public ApiResponse(
      @NonNull Status status, @Nullable T data, @Nullable String message, int statusCode) {
    this.status = status;
    this.data = data;
    this.message = message;
    this.statusCode = statusCode;
  }

  public static <T> ApiResponse success(@Nullable T data, int statudCode) {
    return new ApiResponse<>(Status.SUCCESS, data, null, statudCode);
  }

  public static <T> ApiResponse error(String msg, @Nullable T data, int statudCode) {
    return new ApiResponse<>(Status.ERROR, data, msg, statudCode);
  }

  public static <T> ApiResponse loading(@Nullable T data) {
    return new ApiResponse<>(Status.LOADING, data, null, -1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ApiResponse<?> resource = (ApiResponse<?>) o;

    if (status != resource.status) {
      return false;
    }
    if (!Objects.equals(message, resource.message)) {
      return false;
    }
    return Objects.equals(data, resource.data);
  }

  @Override
  public int hashCode() {
    int result = status.hashCode();
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FAResponse{"
        + "status="
        + status
        + ", message='"
        + message
        + '\''
        + ", data="
        + data
        + '}';
  }

  /**
   * Status of a resource that is provided to the UI.
   *
   * <p>These are usually created by the Repository classes where they return {@code
   * LiveData<FAResponse<T>>} to pass back the latest data to the UI with its fetch status.
   */
  public enum Status {
    SUCCESS,
    ERROR,
    LOADING
  }
}
