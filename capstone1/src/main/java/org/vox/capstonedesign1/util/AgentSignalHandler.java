package org.vox.capstonedesign1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vox.capstonedesign1.dto.SaveAgentSignalRequest;
import org.vox.capstonedesign1.service.AgentSignalService;

@Component
@RequiredArgsConstructor
public class AgentSignalHandler implements UdpMessageHandler {

    private final ObjectMapper objectMapper;
    private final AgentSignalService agentSignalService;

    @Override
    public void handleMessage(String message) throws Exception {
        SaveAgentSignalRequest dto = objectMapper.readValue(message, SaveAgentSignalRequest.class);
        agentSignalService.saveSignal(dto);
    }
}
