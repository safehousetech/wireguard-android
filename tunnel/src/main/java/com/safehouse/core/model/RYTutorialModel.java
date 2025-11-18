package com.safehouse.core.model;

public class RYTutorialModel {

  public RYTutorialModel(String heading, String description, int drawableID) {
    this.heading = heading;
    this.description = description;
    this.drawableID = drawableID;
  }

  public String getHeading() {
    return heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDrawableID() {
    return drawableID;
  }

  public void setDrawableID(int drawableID) {
    this.drawableID = drawableID;
  }

  private String heading;
  private String description;
  private int drawableID;
}
