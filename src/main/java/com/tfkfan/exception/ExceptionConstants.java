package com.tfkfan.exception;

import java.util.Map;

public interface ExceptionConstants {
    String ALREADY_EXISTS_STATE = "23505";
    String DRONE_PKEY = "drone_pkey";
    String MODEL_PKEY = "model_pkey";

    String MEDICATION_PKEY = "medication_pkey";

    String MEDICATION_LOAD_PKEY = "medication_load_pkey";

    Map<String, String> PKEY_TO_ENTITY = Map.ofEntries(
        Map.entry(DRONE_PKEY, "Drone"),
        Map.entry(MODEL_PKEY, "Model"),
        Map.entry(MEDICATION_PKEY, "Medication"),
        Map.entry(MEDICATION_LOAD_PKEY, "Medication items load")
    );

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
