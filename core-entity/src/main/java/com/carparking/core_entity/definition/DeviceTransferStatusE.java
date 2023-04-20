package com.carparking.core_entity.definition;

public enum DeviceTransferStatusE {
	Accept(1),
    NotFound(2),
    Damaged(3);
	
	
    private int value;
    
    DeviceTransferStatusE(int value) {
        this.value = value;
    }
    public int getValue() {
    	return value;
    }
}
