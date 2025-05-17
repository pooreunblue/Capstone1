package org.vox.capstonedesign1.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.util.handler.AgentSignalHandler;
import org.vox.capstonedesign1.util.handler.UdpMessageHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Slf4j
@Component
@RequiredArgsConstructor
public class UdpReceiver {

    private final DatagramSocket datagramSocket;
    private final AgentSignalHandler messageHandler;

    @PostConstruct
    public void start() {
        Thread thread = new Thread(this::listen);
        thread.setDaemon(true);
        thread.start();
    }

    private void listen() {
        log.info("UDP 서버 시작됨. 포트: {}", datagramSocket.getLocalPort());
        byte[] buffer = new byte[16834];

        while (!datagramSocket.isClosed()) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                // ▷ 바이너리 float[] 해석
                ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN); // Python 쪽과 byte 순서 맞추기
                int numFloats = packet.getLength() / 4;
                float[] data = new float[numFloats];
                for (int i = 0; i < numFloats; i++) {
                    data[i] = byteBuffer.getFloat();
                }
                log.info("수신된 UDP 데이터(float[]) 길이: {}", data.length);
//                messageHandler.handleMessage(message);
            } catch (Exception e) {
                log.error("UDP 수신 중 오류 발생", e);
            }
        }
    }
}
