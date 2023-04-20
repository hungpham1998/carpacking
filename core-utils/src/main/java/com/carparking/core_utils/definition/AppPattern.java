package com.htsc.core_utils.definition;

public class AppPattern {
  public static final String Password = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&]).{8,20}$";
  public static final String Email = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)$";
  public static final String Digital = "^[0-9]+$";
  public static final String DigitalCode = "^[0-9][0-9-]*$";
  public static final String PageSize = "^[1-9][0-9]$";
  public static final String PageNo = "^0|[1-9][0-9]+$";
  public static final String DeviceCode = "^[0-9]{15}$";
  public static final String DeviceName = "^[a-zA-Z0-9_.-]{1,32}$";
  public static final String AllocationReceivedStatus = "^(Accept|Reject)$";
  public static final String PhoneNumber = "^0\\d{9,11}$";
  public static final String CharAndNumAndDash = "^[a-zA-Z0-9_.-]*$";
}
