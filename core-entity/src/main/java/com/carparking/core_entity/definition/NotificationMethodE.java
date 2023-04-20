package com.carparking.core_entity.definition;

public enum NotificationMethodE {
  MAIL("MAIL"),
  SMS("SMS"),
  WEB("WEB");
  
  private String value;
  NotificationMethodE(String value){
    this.value = value;
  }
  public String getValue() {
    return value;
  }
  
  public interface Value {
    String MAIL = "MAIL";
    String SMS = "SMS";
    String WEB = "WEB";
  }
}
