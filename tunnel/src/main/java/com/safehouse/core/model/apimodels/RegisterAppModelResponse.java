package com.safehouse.core.model.apimodels;

public class RegisterAppModelResponse {
  private String errorMessage;
  private String result;
  private String server;
  private String secret;
  private String region;
  private String configFile;

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getServer() {
    return server;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getSecret() {
    return secret;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRegion() {
    return region;
  }

  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

  public String getConfigFile() {
    return configFile;
  }

  @Override
  public String toString() {
    return "RegisterAppModelResponse{"
        + "error_message = '"
        + errorMessage
        + '\''
        + ",result = '"
        + result
        + '\''
        + ",server = '"
        + server
        + '\''
        + ",secret = '"
        + secret
        + '\''
        + ",region = '"
        + region
        + '\''
        + ",config_file = '"
        + configFile
        + '\''
        + "}";
  }
}
