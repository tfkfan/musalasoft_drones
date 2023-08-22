package com.tfkfan.exception;

import com.tfkfan.config.Constants;
import org.springframework.http.HttpStatus;

public enum ExceptionDictionary {
    DRONE_NOT_FOUND("Drone with the given serial number not found", "DroneNotFoundException", HttpStatus.NOT_FOUND),
    DRONE_IS_BUSY("Drone is not available for load/delivery", "DroneIsBusyException", HttpStatus.BAD_REQUEST),
    DRONE_IS_OVERLOADED("Drone is overloaded with the given items", "DroneIsOverloadedException", HttpStatus.BAD_REQUEST),
    BATTERY_CHARGE_IS_LOW(
        String.format("Battery charge is lower than %s percents", Constants.LOW_CHARGE_THRESHOLD),
        "BatteryChargeIsLowException",
        HttpStatus.INTERNAL_SERVER_ERROR
    );

    private final String defaultMessage;
    private final String code;
    private final HttpStatus status;

    ExceptionDictionary(String defaultMessage, String code, HttpStatus status) {
        this.defaultMessage = defaultMessage;
        this.code = code;
        this.status = status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
