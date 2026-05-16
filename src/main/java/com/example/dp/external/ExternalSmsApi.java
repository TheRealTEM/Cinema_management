package com.example.dp.external;

// This represents a 3rd party legacy API we don't control
public class ExternalSmsApi {
    public void sendTextPayload(String json) {
        System.out.println("[LEGACY SMS API] Dispatching payload: " + json);
    }
}
