package org.vox.capstonedesign1.util.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.dto.SaveAgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentSignalHandler implements UdpMessageHandler {

    private final ObjectMapper objectMapper;
    private final AgentSignalService agentSignalService;

//    @Override
//    public void handleMessage(String message) throws Exception {
//        SaveAgentSignalRequest dto = objectMapper.readValue(message, SaveAgentSignalRequest.class);
//        agentSignalService.saveSignal(dto);
//    }

    @Override
    public void handleMessage(float[] data) throws Exception {
//        if (data.length < 56) {
//            log.warn("데이터 길이가 너무 짧음: {}", data.length);
//            return;
//        }
//
//        // 👉 앞 56개 복사
//        float[] trimmedSensorData = Arrays.copyOfRange(data, 0, 56);
//
//        // 👉 후반부에서 deviceId, label, timestamp 추출 (예시: 마지막 3개 값이라고 가정)
//        int len = data.length;
//        String deviceId = String.valueOf((int) data[len - 3]); // 또는 float→String으로 직접 매핑
//        int label = (int) data[len - 2];
//        long timestamp = (long) data[len - 1];
//
//        // 👉 streamingFrequency 계산은 이전 timestamp랑 비교 (AgentSignalService에 위임)
//
//        // 👉 DTO로 변환
//        SaveAgentSignalRequest dto = new SaveAgentSignalRequest();
//        dto.setDeviceId(deviceId);
//        dto.setLabel(label);
//        dto.setTimestamp(timestamp);
//        dto.setSensorValues(trimmedSensorData); // float[] → WebSocket으로 보낼 데이터
//
//        // 저장 서비스 호출
//        agentSignalService.saveSignal(dto);
//
//        // WebSocket 브로드캐스트
//        webSocketSender.sendToUnityClients(dto); // trimmedSensorData를 JSON으로 포장해서 보내기
    }
}
