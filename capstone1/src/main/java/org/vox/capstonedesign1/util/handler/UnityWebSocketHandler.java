package org.vox.capstonedesign1.util.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import org.vox.capstonedesign1.util.manager.UnityWebSocketManager;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UnityWebSocketHandler extends BinaryWebSocketHandler {

    private final UnityWebSocketManager manager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String uri = session.getUri().toString();
        String deviceSerialNumber = UriComponentsBuilder.fromUriString(uri).build().getQueryParams().getFirst("deviceSerialNumber");
        if (deviceSerialNumber != null) {
            manager.registerSession(deviceSerialNumber, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 연결 종료 시 등록 해제
        for (Map.Entry<String, WebSocketSession> entry : manager.getDeviceSessions().entrySet()) {
            if (entry.getValue().equals(session)) {
                manager.removeSession(entry.getKey());
                break;
            }
        }
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // Unity에서는 일반적으로 수신만 하므로 처리 생략
    }
}
