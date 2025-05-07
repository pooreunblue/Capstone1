package org.vox.capstonedesign1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.AgentSignal;

import java.time.LocalDateTime;

@Getter
public class AgentViewResponse {

    private final String agentName;
    private final String estimatedStatus;
    private final String deviceSerialNumber;
    private final LocalDateTime timeStamp;
    private final int streamingFrequency;
    private final String serverIp;

    public AgentViewResponse(Agent agent, AgentSignal agentSignal) {
        this.agentName = agent.getAgentName();
        this.estimatedStatus = agentSignal.getEstimatedStatus().getStatusInformation();
        this.deviceSerialNumber = agent.getDevice().getDeviceSerialNumber();
        this.timeStamp = agentSignal.getTimeStamp();
        this.streamingFrequency = agentSignal.getStreamingFrequency();
        this.serverIp = "15.165.51.47";
    }
}
