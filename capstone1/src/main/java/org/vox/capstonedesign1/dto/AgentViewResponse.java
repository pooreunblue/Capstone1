package org.vox.capstonedesign1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AgentViewResponse {

    private final String agentName;
    private final String estimatedStatus;
    private final String deviceSerialNumber;
    private final LocalDateTime timeStamp;
    private final int streamingFrequency;
    private final String serverIp;
}
