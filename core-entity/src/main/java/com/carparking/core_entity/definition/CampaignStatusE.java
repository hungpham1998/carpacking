package com.carparking.core_entity.definition;

public enum CampaignStatusE {
	Reserver(Value.Reserver) ,Active(Value.Active), Pending(Value.Pending), Close(Value.Close);

	private String value;

	CampaignStatusE(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	 public interface Value {
	   String Reserver = "Reserver";
	   String Active = "Active";
	   String Pending = "Pending";
	   String Close = "Close";
	 }
}
