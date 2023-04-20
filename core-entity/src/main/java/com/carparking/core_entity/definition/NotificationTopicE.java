package com.carparking.core_entity.definition;

public enum NotificationTopicE {
  InGeofence(Value.InGeofence), 
  OutGeofence(Value.OutGeofence),
  Rendezvous(Value.Rendezvous),
  TurnOn(Value.TurnOn),
  TurnOff(Value.TurnOff),
  Moving(Value.Moving), 
  LowBattery(Value.LowBattery),
  CommandResult(Value.LowBattery);
  
  private String value;
  
  NotificationTopicE(String value){
    this.value = value;
  }
  
  public interface Value {
    String InGeofence = "InGeofence";
    String OutGeofence = "OutGeofence";
    String Rendezvous = "Rendezvous";
    String TurnOn = "TurnOn";
    String TurnOff = "TurnOff";
    String Moving = "Moving";
    String LowBattery = "LowBattery";
    String CommandResult = "CommandResult";
  }
}
