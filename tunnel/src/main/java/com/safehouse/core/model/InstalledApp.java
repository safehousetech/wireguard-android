package com.safehouse.core.model;

import android.graphics.drawable.Drawable;

public class InstalledApp {
  public String name;
  public Drawable icon;
  public String packageName;
  public String originCountry;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Drawable getIcon() {
    return icon;
  }

  public void setIcon(Drawable icon) {
    this.icon = icon;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getOriginCountry() {
    return originCountry;
  }

  public void setOriginCountry(String originCountry) {
    this.originCountry = originCountry;
  }
}
