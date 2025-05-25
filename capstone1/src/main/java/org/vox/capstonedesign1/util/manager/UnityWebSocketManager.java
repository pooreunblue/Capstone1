package org.vox.capstonedesign1.util.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UnityWebSocketManager {

    private final Map<String, WebSocketSession> deviceSessions = new ConcurrentHashMap<>();

    public Map<String, WebSocketSession> getDeviceSessions() {
        return Collections.unmodifiableMap(deviceSessions);
    }

    public void registerSession(String deviceSerialNumber, WebSocketSession session) {
        deviceSessions.put(deviceSerialNumber, session);
    }

    public void removeSession(String deviceSerialNumber) {
        deviceSessions.remove(deviceSerialNumber);
    }

    public void sendToDevice(double deviceSerialNumber, byte[] rawData) {
        WebSocketSession session = deviceSessions.get(deviceSerialNumber);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new BinaryMessage(rawData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
