package com.safehouse.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PawnedModel implements Parcelable {

  private String name;
  private String title;
  private String domain;
  private String breachDate;
  private String addedDate;
  private String modifiedDate;
  private Integer pwnCount;
  private String description;
  private String logoPath;
  private Boolean isVerified;
  private Boolean isFabricated;
  private Boolean isSensitive;
  private Boolean isRetired;
  private Boolean isSpamList;
  private JSONObject object;

  private ArrayList<String> breachPoints;

  PawnedModel() {}

  protected PawnedModel(Parcel in) {
    name = in.readString();
    title = in.readString();
    domain = in.readString();
    breachDate = in.readString();
    addedDate = in.readString();
    modifiedDate = in.readString();
    if (in.readByte() == 0) {
      pwnCount = null;
    } else {
      pwnCount = in.readInt();
    }
    description = in.readString();
    logoPath = in.readString();
    byte tmpIsVerified = in.readByte();
    isVerified = tmpIsVerified == 0 ? null : tmpIsVerified == 1;
    byte tmpIsFabricated = in.readByte();
    isFabricated = tmpIsFabricated == 0 ? null : tmpIsFabricated == 1;
    byte tmpIsSensitive = in.readByte();
    isSensitive = tmpIsSensitive == 0 ? null : tmpIsSensitive == 1;
    byte tmpIsRetired = in.readByte();
    isRetired = tmpIsRetired == 0 ? null : tmpIsRetired == 1;
    byte tmpIsSpamList = in.readByte();
    isSpamList = tmpIsSpamList == 0 ? null : tmpIsSpamList == 1;
    breachPoints = in.createStringArrayList();
  }

  // method to return PawnedModel as per updated Breach API
  public static PawnedModel updatedJsonObject(JSONObject object) throws JSONException {
    PawnedModel mdl = new PawnedModel();

    mdl.name = object.getString("name");
    mdl.title = object.getString("title");
    mdl.domain = object.getString("domain");
    mdl.breachDate = object.getString("breachDate");
    mdl.addedDate = object.getString("addedDate");
    mdl.modifiedDate = object.getString("modifiedDate");
    mdl.pwnCount = object.getInt("pwnCount");
    mdl.description = object.getString("description");
    mdl.logoPath = object.getString("logoPath");
    mdl.object = object;

    JSONArray breachPointsJsonArray = object.getJSONArray("dataClasses");
    ArrayList<String> breachPoints = new ArrayList<>();

    try {
      for (int i = 0; i < breachPointsJsonArray.length(); i++) {
        breachPoints.add(breachPointsJsonArray.getString(i));
      }
      mdl.breachPoints = breachPoints;
    } catch (Exception e) {
      mdl.breachPoints = new ArrayList<>();
    }

    return mdl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getBreachDate() {
    return breachDate;
  }

  public void setBreachDate(String breachDate) {
    this.breachDate = breachDate;
  }

  public String getAddedDate() {
    return addedDate;
  }

  public void setAddedDate(String addedDate) {
    this.addedDate = addedDate;
  }

  public String getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Integer getPwnCount() {
    return pwnCount;
  }

  public void setPwnCount(Integer pwnCount) {
    this.pwnCount = pwnCount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public Boolean getIsVerified() {
    return isVerified;
  }

  public void setIsVerified(Boolean isVerified) {
    this.isVerified = isVerified;
  }

  public Boolean getIsFabricated() {
    return isFabricated;
  }

  public void setIsFabricated(Boolean isFabricated) {
    this.isFabricated = isFabricated;
  }

  public Boolean getIsSensitive() {
    return isSensitive;
  }

  public void setIsSensitive(Boolean isSensitive) {
    this.isSensitive = isSensitive;
  }

  public Boolean getIsRetired() {
    return isRetired;
  }

  public void setIsRetired(Boolean isRetired) {
    this.isRetired = isRetired;
  }

  public Boolean getIsSpamList() {
    return isSpamList;
  }

  public void setIsSpamList(Boolean isSpamList) {
    this.isSpamList = isSpamList;
  }

  public JSONObject getObject() {
    return object;
  }

  public void setObject(JSONObject object) {
    this.object = object;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public ArrayList<String> getBreachPoints() {
    return breachPoints;
  }

  public void setBreachPoints(ArrayList<String> breachPoints) {
    this.breachPoints = breachPoints;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(title);
    dest.writeString(domain);
    dest.writeString(breachDate);
    dest.writeString(addedDate);
    dest.writeString(modifiedDate);
    if (pwnCount == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(pwnCount);
    }
    dest.writeString(description);
    dest.writeString(logoPath);
    dest.writeByte((byte) (isVerified == null ? 0 : isVerified ? 1 : 2));
    dest.writeByte((byte) (isFabricated == null ? 0 : isFabricated ? 1 : 2));
    dest.writeByte((byte) (isSensitive == null ? 0 : isSensitive ? 1 : 2));
    dest.writeByte((byte) (isRetired == null ? 0 : isRetired ? 1 : 2));
    dest.writeByte((byte) (isSpamList == null ? 0 : isSpamList ? 1 : 2));
    dest.writeStringList(breachPoints);
  }

  public static final Creator<PawnedModel> CREATOR =
          new Creator<PawnedModel>() {
            @Override
            public PawnedModel createFromParcel(Parcel in) {
              return new PawnedModel(in);
            }

            @Override
            public PawnedModel[] newArray(int size) {
              return new PawnedModel[size];
            }
          };
}
