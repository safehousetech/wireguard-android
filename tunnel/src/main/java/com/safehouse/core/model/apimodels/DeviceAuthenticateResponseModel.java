package com.safehouse.core.model.apimodels;

public class DeviceAuthenticateResponseModel {
  /* "responseCode": 1,
          "responseMessage": "",
          "data": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI4IiwidW5pcXVlX25hbWUiOiIgIiwicm9sZSI6IjIiLCJuYmYiOjE1ODgxODAyNTEsImV4cCI6MTU4ODc4NTA1MSwiaWF0IjoxNTg4MTgwMjUxfQ.2YIJRJrN6oJC5kqfkmoQstZRUUlv1ePaDSzbdjv1qL8",
              "refreshToken": "appdrkidqrgleqrqhpbbqukwucydas",
              "expiryDate": "06-May-2020 17:10"
  },
          "totalPages": 0,
          "totalRecords": 0,
          "page": 0,
          "pageSize": 0
  */
  private String token;
  private String refreshToken;
  private String expiryDate;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
}
