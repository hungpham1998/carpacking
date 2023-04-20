package com.carparking.core_entity.definition;

public enum MasterDataTypeE {
  WorkUnitMaster(Value.WorkUnitMaster), NotificationMethod(Value.NotificationMethod);
  private String value;
  
  MasterDataTypeE(String value){
    this.value = value;
  }
  public String getValue() {
    return value;
  }
  
  public interface Value {
    String WorkUnitMaster = "WorkUnitMaster";
    String NotificationMethod = "NotificationMethod";
  }
}
