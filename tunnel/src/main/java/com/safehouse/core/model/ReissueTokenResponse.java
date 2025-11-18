package com.safehouse.core.model;

import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReissueTokenResponse {
    @Expose
    @SerializedName("token")
    private String token;

    public String getToken() {
      return token;
    }

    @Expose
    @SerializedName("is_email_verified")
    private Boolean isEmailVerified;

    public Boolean getEmailVerified() {
      return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
      isEmailVerified = emailVerified;
    }

    @Expose
    @SerializedName("is_phone_verified")
    private Boolean isPhoneVerified;

    public Boolean getPhoneVerified() {
      return isPhoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
      isPhoneVerified = phoneVerified;
    }

    @Expose
    @SerializedName("is_alternate_contact_added")
    private Boolean isAlternateContactAdded;

    @Expose
    @SerializedName("is_alternate_contact_verified")
    private Boolean isAlternateContactVerified;

    @Expose
    @SerializedName("email")
    private String email;

    public String getEmail() {
      return email == null ? "" : email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Expose
    @SerializedName("alternateEmail")
    private String alternateEmail;

    public String getAlternateEmail() {
      return alternateEmail == null ? "" : alternateEmail;
    }

    public void setAlternateEmail(String email) {
      this.alternateEmail = email;
    }

    @Expose
    @SerializedName("phone")
    private String phone;

    @Nullable public String getPhone() {
      return phone;
    }

    public void setPhone(@Nullable String phone) {
      this.phone = phone;
    }

    @Expose
    @SerializedName("alternatePhone")
    private String alternatePhone;

    public String getAlternatePhone() {
      return alternatePhone == null ? "" : alternatePhone;
    }

    public void setAlternatePhone(String phone) {
      this.alternatePhone = phone;
    }

    @Expose
    @SerializedName("name")
    private String name;

    public String getName() {
      return name == null ? "" : name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Expose
    @SerializedName("unique_id")
    private String uniqueID;

    public String getuniqueID() {
      return uniqueID;
    }

    public void setuniqueID(String uniqueID) {
      this.uniqueID = uniqueID;
    }

    @Expose
    @SerializedName("cyberInsurance")
    private Boolean insurance;

    public Boolean getInsurance() {
      return insurance != null ? insurance : false;
    }

    public void setInsurance(Boolean insurance) {
      this.insurance = insurance;
    }

    @Expose
    @SerializedName("showInsuranceValidation")
    private Boolean showInsuranceValidation;

    public Boolean getShowInsuranceValidation() {
      return showInsuranceValidation != null ? showInsuranceValidation : false;
    }

    public void setShowInsuranceValidation(Boolean insurance) {
      this.showInsuranceValidation = insurance;
    }

    @Expose
    @SerializedName("salutation")
    private String salutation;

    public String getSalutation() {
      return salutation != null ? salutation : "";
    }

    public void setSalutation(String salutation) {
      this.salutation = salutation;
    }

    @Expose
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }

    @Expose
    @SerializedName("isEligibleForTrial")
    private Boolean isUserEligibleForTrial;

    public Boolean getIsUserEligibleForTrial() {
      return isUserEligibleForTrial;
    }

    public void setIsUserEligibleForTrial(Boolean isUserEligibleForTrial) {
      this.isUserEligibleForTrial = isUserEligibleForTrial;
    }

    @Expose
    @SerializedName("freeForEver")
    private Boolean areFeaturesFree;

    public Boolean getAreFeaturesFree() {
      return areFeaturesFree;
    }

    public void setAreFeaturesFree(Boolean areFeaturesFree) {
      this.areFeaturesFree = areFeaturesFree;
    }

    public Boolean getAlternateContactAdded() {
      return isAlternateContactAdded;
    }

    public void setAlternateContactAdded(Boolean alternateContactAdded) {
      isAlternateContactAdded = alternateContactAdded;
    }

    public Boolean getAlternateContactVerified() {
      return isAlternateContactVerified;
    }

    public void setAlternateContactVerified(Boolean alternateContactVerified) {
      isAlternateContactVerified = alternateContactVerified;
    }
}
