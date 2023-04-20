package com.carparking.core_entity.definition;

public enum CampaignLogActionTypeE {
  AddItem(Value.AddItem), 
  RemoveItem(Value.RemoveItem), 
  UpdateItem(Value.UpdateItem), 
  UpdateInfo(Value.UpdateInfo),
  ChangeStatus(Value.ChangeStatus),
  CreateNew(Value.CreateNew);

  private String value;

  CampaignLogActionTypeE(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public interface Value {
    String AddItem = "AddItem";
    String RemoveItem = "RemoveItem";
    String UpdateItem = "UpdateItem";
    String UpdateInfo = "UpdateInfo";
    String ChangeStatus = "ChangeStatus";
    String CreateNew = "CreateNew";
  }
}
