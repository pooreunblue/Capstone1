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
        float[] numbers = extractData(floats);
        String message = formatToString(numbers);
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

    private float[] extractData (float[] floats) {
        return Arrays.copyOfRange(floats, 3, floats.length);
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
