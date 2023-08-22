package com.tfkfan.exception;

public class BatteryChargeIsLowException extends BusinessException {

    static ExceptionDictionary dict = ExceptionDictionary.BATTERY_CHARGE_IS_LOW;

    public BatteryChargeIsLowException() {
        super(dict);
    }

    public BatteryChargeIsLowException(String message) {
        super(message, dict);
    }
}
