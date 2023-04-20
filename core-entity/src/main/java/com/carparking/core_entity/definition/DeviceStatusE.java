package com.carparking.core_entity.definition;

public enum DeviceStatusE {
  Initialize(Value.Initialize), Using(Value.Using), Allocation(Value.Allocation),
  Accept(Value.Accept), Reject(Value.Reject), Recalled(Value.Recalled), Unknow(Value.Unknow),
  Deleted(Value.Deleted),
  UndoAllocation(Value.UndoAllocation);

  private String value;

  DeviceStatusE(String value) {
    this.value = value;
  }

  
  public String getValue() {
    return this.value;
  }

  public interface Value {
    String Initialize = "Initialize";
    String Using = "Using";
    String Allocation = "Allocation";
    String Accept = "Accept";
    String Reject = "Reject";
    String Recalled = "Recalled";
    String Unknow = "Unknow";
    String Deleted = "Deleted";
    String UndoAllocation = "UndoAllocation";
  }

}
