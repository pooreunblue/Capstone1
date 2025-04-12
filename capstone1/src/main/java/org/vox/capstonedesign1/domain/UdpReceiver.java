package org.vox.capstonedesign1.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component // 빈 등록
@RequiredArgsConstructor
public class UdpReceiver {

    private final AgentSignalService agentSignalService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // 직렬화, 역직렬화 클래스
    private final DatagramSocket datagramSocket; // 외부에서 주입

    @PostConstruct
    public void startUdpServer() {
        Thread thread = new Thread(() -> {
            try {
                log.info("UDP 서버 시작됨. 포트: {}", datagramSocket.getLocalPort()); // 소켓에서 직접 포트 출력
                byte[] buffer = new byte[1024]; // 수신 버퍼 (최대 1024바이트)
                extracted(buffer);
            } catch (Exception e) {
                log.error("UDP 수신 중 오류 발생", e);
            }
        });
        thread.setDaemon(true); // 메인 스레드 종료 시 같이 종료
        thread.start(); // UDP 서버 백그라운드에서 실행
    }

    private void extracted(byte[] buffer) throws IOException {
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet); // 데이터 수신 대기
            String receivedData = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
            log.info("수신된 UDP 데이터: {}", receivedData);
            AgentSignalRequest dto = objectMapper.readValue(receivedData, AgentSignalRequest.class);
            agentSignalService.saveSignal(dto);
        }
    }
}
