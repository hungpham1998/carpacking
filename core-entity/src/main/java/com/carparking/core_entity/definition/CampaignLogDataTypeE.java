package com.carparking.core_entity.definition;

public enum CampaignLogDataTypeE {
  SystemUser(Value.SystemUser), Device(Value.Device),
  Notification(Value.Notification),
  Geofence(Value.Geofence), 
  BaseInfo(Value.BaseInfo),
  Status(Value.Status),
  All(Value.All);
  

  private String value;

  CampaignLogDataTypeE(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public interface Value {
    String SystemUser = "SystemUser";
    String Device = "Device";
    String Geofence = "Geofence";
    String BaseInfo = "BaseInfo";
    String Notification = "Notification";
    String Status = "Status";
    String All = "All";
  }
}
