package org.vox.capstonedesign1.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.testutil.MockDatagramSocket;

import java.net.DatagramPacket;

import static org.mockito.Mockito.*;

class UdpReceiverIntegrationTest {

    @Test
    void UDP소켓서비스전달테스트() throws Exception {
        AgentSignalService mockService = mock(AgentSignalService.class);
        MockDatagramSocket mockDatagramSocket = new MockDatagramSocket();
        UdpReceiver receiver = new UdpReceiver(mockService, mockDatagramSocket);
        receiver.startUdpServer();
        String json = "{\"deviceId\":1,\"statusId\":3,\"timeStamp\":\"2025-04-13T12:26:38\"}";
        byte[] testData = json.getBytes("UTF-8");
        DatagramPacket testPacket = new DatagramPacket(testData, testData.length);
        mockDatagramSocket.injectPacket(testPacket);
        Thread.sleep(500);
        AgentSignalRequest expected = new ObjectMapper().readValue(json, AgentSignalRequest.class);
        verify(mockService, times(1)).saveSignal(refEq(expected));
    }
}
