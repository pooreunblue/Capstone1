package org.vox.capstonedesign1.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;

import jakarta.annotation.PostConstruct;
import java.net.DatagramPacket; 
import java.net.DatagramSocket;
import java.util.function.Supplier;

@Slf4j
@Component // 빈 등록
@RequiredArgsConstructor
public class UdpReceiver {

    private final AgentSignalService agentSignalService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // 직렬화, 역직렬화 클래스

    private static final int PORT = 9999; // UDP 수신 포트

    private Supplier<DatagramSocket> socketSupplier = () -> {
        try {
            return new DatagramSocket(PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public void setSocketSupplier(Supplier<DatagramSocket> socketSupplier) {
        this.socketSupplier = socketSupplier;
    }

    @PostConstruct
    public void startUdpServer() {
        Thread thread = new Thread(() -> {
            try (DatagramSocket socket = socketSupplier.get()) {
                log.info("UDP 서버 시작됨. 포트: {}", PORT);
                byte[] buffer = new byte[1024]; // 수신 버퍼 (최대 1024바이트)
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet); // 데이터 수신 대기
                    // 받은 데이터 JSON이라고 가정하고 데이터 문자열로 변환, 인코딩 되어있으면 디코딩
                    String receivedData = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    log.info("수신된 UDP 데이터: {}", receivedData);
                    // JSON → DTO 변환
                    AgentSignalRequest dto = objectMapper.readValue(receivedData, AgentSignalRequest.class);
                    // 서비스에 전달하여 저장
                    agentSignalService.saveSignal(dto);
                }
            } catch (Exception e) {
                log.error("UDP 수신 중 오류 발생", e);
            }
        });
        thread.setDaemon(true); // 메인 스레드 종료 시 같이 종료
        thread.start(); // UDP 서버 백그라운드에서 실행
    }
}
