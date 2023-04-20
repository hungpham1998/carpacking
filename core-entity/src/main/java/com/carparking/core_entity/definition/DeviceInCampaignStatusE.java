package com.carparking.core_entity.definition;

public enum DeviceInCampaignStatusE {
  Initial(Value.Initial), Using(Value.Using), Unknow(Value.Unknow), UnUsing(Value.UnUsing), Remove(Value.Remove);

  private String value;

  DeviceInCampaignStatusE(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public interface Value {
    String Initial = "Initial";
    String Using = "Using";
    String Unknow = "Unknow";
    String UnUsing = "UnUsing";
    // this status don't use for device and campaign
    // It is used for device and campaign history
    String Remove = "Remove";
  }
}
