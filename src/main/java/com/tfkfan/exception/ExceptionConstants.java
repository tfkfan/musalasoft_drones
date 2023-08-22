package com.tfkfan.exception;

import java.util.Map;

public interface ExceptionConstants {
    String ALREADY_EXISTS_STATE = "23505";
    String DRONE_PKEY = "drone_pkey";
    String MODEL_PKEY = "model_pkey";

    Map<String, String> PKEY_TO_ENTITY = Map.ofEntries(Map.entry(DRONE_PKEY, "Drone"), Map.entry(MODEL_PKEY, "Model"));

    static String formatConstraintMessage(String message) {
        for (Map.Entry<String, String> entry : PKEY_TO_ENTITY.entrySet()) {
            if (message.contains(entry.getKey())) return String.format(
                ExceptionDictionary.ENTITY_ALREADY_EXISTS.getDefaultMessage(),
                entry.getValue()
            );
        }

        return message;
    }
}
