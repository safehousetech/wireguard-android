package com.safehouse.core.model;

public class TotalLicenseDetail {

  private String licenseHash;

  private String expiryDate;

  public Boolean hasPolicy;

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  private boolean isSelected;

  private int id;

  public int getLicenseAvailability() {
    return licenseAvailability;
  }

  public void setLicenseAvailability(int licenseAvailability) {
    this.licenseAvailability = licenseAvailability;
  }

  private int licenseAvailability; // 0 = available and 1 = used

  public String getLicenseLength() {
    return licenseLength;
  }

  public void setLicenseLength(String licenseLength) {
    this.licenseLength = licenseLength;
  }

  private String licenseLength;

  public String getLicenseHash() {
    return licenseHash;
  }

  public void setLicenseHash(String licenseHash) {
    this.licenseHash = licenseHash;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  private String productType;

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
