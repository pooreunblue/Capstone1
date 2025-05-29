package org.vox.capstonedesign1.util.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.service.DeviceService;
import org.vox.capstonedesign1.util.calculator.FrequencyCalculator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentSignalHandler implements UdpMessageHandler {

    private final AgentSignalService agentSignalService;
    private final DeviceService deviceService;
    private final UnityWebSocketHandler unityWebSocketHandler;
    private final FrequencyCalculator frequencyCalculator;

    @Override
    public void handleMessage(byte[] data){
        float[] floats = getFloats(data);
        String deviceSerialNumber = getDeviceSerialNumber(floats);
        // saveSignal(floats, deviceSerialNumber);
        float[] poses = extractPoseData(floats);
        String message = formatToString(poses);
        unityWebSocketHandler.sendTextToDevice(deviceSerialNumber, message);
    }

    private float[] getFloats(byte[] data) {
        FloatBuffer floatBuffer = ByteBuffer.wrap(data)
                .order(ByteOrder.LITTLE_ENDIAN)
                .asFloatBuffer();
        float[] floats = new float[floatBuffer.remaining()];
        floatBuffer.get(floats);
        return floats;
    }

    private String getDeviceSerialNumber(float[] floats) {
        Long deviceId = (long) floats[1];
        String deviceSerialNumber = deviceService.resolveDeviceSerialNumber(deviceId);
        return deviceSerialNumber;
    }

    private float[] extractPoseData(float[] floats) {
        try {
            // 필요한 인덱스 순서대로 추출
            float[] hand_q = {floats[52], floats[53], floats[54], floats[51]};
            float[] hand_p = {floats[7], floats[8], floats[9]};

            float[] larm_q = {floats[12], floats[13], floats[14], floats[11]};
            float[] larm_p = {floats[15], floats[16], floats[17]};

            float[] uarm_q = {floats[30], floats[31], floats[32], floats[29]};
            float[] uarm_p = {floats[33], floats[34], floats[35]};

            float[] hips_q = {floats[38], floats[39], floats[40], floats[37]};

            // 총 3 + 3 + 3 + 3 + 4 + 4 = 24개
            float[] result = new float[
                    hand_q.length + hand_p.length +
                            larm_q.length + larm_p.length +
                            uarm_q.length + uarm_p.length +
                            hips_q.length
                    ];

            int index = 0;
            for (float v : hand_q) result[index++] = v;
            for (float v : hand_p) result[index++] = v;

            for (float v : larm_q) result[index++] = v;
            for (float v : larm_p) result[index++] = v;

            for (float v : uarm_q) result[index++] = v;
            for (float v : uarm_p) result[index++] = v;

            for (float v : hips_q) result[index++] = v;

            return result;

        } catch (IndexOutOfBoundsException e) {
            log.error("Pose data extraction failed: index out of bounds. floats.length={}", floats.length, e);
            return new float[0]; // 빈 배열 반환
        }
    }

    private String formatToString (float[] floats) {
        String message = IntStream.range(0, floats.length)
                .mapToObj(i -> Float.toString(floats[i]))
                .collect(Collectors.joining(","));
        return message;
    }

    private void saveSignal(float[] floats, String deviceSerialNumber) {
        Long statusId = (long) floats[0];
        float timestamp = floats[2];
        long seconds = (long) timestamp;
        long nanos = (long) ((timestamp % 1) * 1_000_000_000);  // 소수점 이하 나노초
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        LocalDateTime localTimestamp = instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        String formattedTimestamp = localTimestamp.format(DateTimeFormatter.ISO_DATE_TIME);
        // frequency 계산
        double frequency = frequencyCalculator.calculateFrequency(deviceSerialNumber, timestamp);
        // 저장 요청 객체 생성
        AgentSignalRequest request = AgentSignalRequest.builder()
                .deviceSerialNumber(deviceSerialNumber)
                .statusId(statusId)
                .timeStamp(formattedTimestamp)
                .streamingFrequency(frequency)
                .build();
        // DB 저장
        agentSignalService.saveSignal(request);
    }
}
