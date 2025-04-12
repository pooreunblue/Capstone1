package org.vox.capstonedesign1.domain;

import org.junit.jupiter.api.Test;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.testutil.MockDatagramSocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

class UdpReceiverSocketTest {

    @Test
    void Udp통합테스트() throws Exception {

        // Given
        AgentSignalService agentSignalService = mock(AgentSignalService.class);
        UdpReceiver receiver = new UdpReceiver(agentSignalService);
        String json = "{\"agentId\":42,\"signal\":\"ALIVE\"}";
        byte[] testData = json.getBytes("UTF-8");
        MockDatagramSocket mockSocket = new MockDatagramSocket(testData);
        receiver.setSocketSupplier(() -> mockSocket);

        // when
        receiver.startUdpServer();
        Thread.sleep(200); // 너무 짧으면 안 읽힐 수도 있음

        // then
        verify(agentSignalService, times(1)).saveSignal(any(AgentSignalRequest.class));
    }
}
