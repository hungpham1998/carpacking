package com.carparking.core_entity.definition;

public enum CommonStatusE {
  Active(CommonStatus.Active), Deleted(CommonStatus.Deleted);

  private String value;

  CommonStatusE(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
  public  interface CommonStatus{
    public String Active = "Active";
    public String Deleted = "Deleted";
  }
}
