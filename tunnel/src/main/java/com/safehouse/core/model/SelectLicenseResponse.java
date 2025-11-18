package com.safehouse.core.model;

public class SelectLicenseResponse {
  public String getSelectedLicense() {
    return selectedLicense;
  }

  public void setSelectedLicense(String selectedLicense) {
    this.selectedLicense = selectedLicense;
  }

  private String selectedLicense;

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  private String hash;

  public String getValidDate() {
    return validDate;
  }

  public void setValidDate(String validDate) {
    this.validDate = validDate;
  }

  private String validDate;

  public String getLicenseLength() {
    return licenseLength;
  }

  public void setLicenseLength(String licenseLength) {
    this.licenseLength = licenseLength;
  }

  private String licenseLength;

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  private String productType;
}
