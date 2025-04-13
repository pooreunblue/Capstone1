package org.vox.capstonedesign1.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.util.UdpMessageHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Slf4j
@Component
@RequiredArgsConstructor
public class UdpReceiver {

    private final DatagramSocket datagramSocket;
    private final UdpMessageHandler messageHandler;

    @PostConstruct
    public void start() {
        Thread thread = new Thread(this::listen);
        thread.setDaemon(true);
        thread.start();
    }

    private void listen() {
        log.info("UDP 서버 시작됨. 포트: {}", datagramSocket.getLocalPort());
        byte[] buffer = new byte[1024];

        while (!datagramSocket.isClosed()) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                log.info("수신된 UDP 데이터: {}", message);

                messageHandler.handleMessage(message);

            } catch (Exception e) {
                log.error("UDP 수신 중 오류 발생", e);
            }
        }
    }
}
