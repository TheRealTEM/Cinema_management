package com.example.dp.adapter;

import com.example.dp.external.ExternalSmsApi;
import com.example.dp.service.NotificationService;

public class SmsAdapter implements NotificationService {
    private ExternalSmsApi legacySmsApi;

    public SmsAdapter() {
        this.legacySmsApi = new ExternalSmsApi();
    }

    @Override
    public void sendNotification(String userId, String message) {
        // Adapting the method signature and format
        String payload = String.format("{\"user\": \"%s\", \"body\": \"%s\"}", userId, message);
        legacySmsApi.sendTextPayload(payload);
    }
}
