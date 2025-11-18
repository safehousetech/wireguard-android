package com.safehouse.core.model;

public class LostDeviceDTO {
  private int lostDeviceId;
  private String reportingdate;
  private String latitude;
  private String longitude;
  private String video;
  private String videoData;
  private String image;
  private String imageData;

  public int getLostDeviceId() {
    return lostDeviceId;
  }

  public void setLostDeviceId(int lostDeviceId) {
    this.lostDeviceId = lostDeviceId;
  }

  public String getReportingdate() {
    return reportingdate;
  }

  public void setReportingdate(String reportingdate) {
    this.reportingdate = reportingdate;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getVideo() {
    return video;
  }

  public void setVideo(String video) {
    this.video = video;
  }

  public String getVideoData() {
    return videoData;
  }

  public void setVideoData(String videoData) {
    this.videoData = videoData;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImageData() {
    return imageData;
  }

  public void setImageData(String imageData) {
    this.imageData = imageData;
  }
  /*private String locationInfo;
  private int updatedBy;
  private int userId;
  private String deviceInfo;
  private String createdAt;
  private int statusId;
  private int createdBy;
  private int id;
  private String status;
   	private String updatedAt;*/
}
