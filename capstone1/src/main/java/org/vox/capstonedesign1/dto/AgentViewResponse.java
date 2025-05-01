package org.vox.capstonedesign1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class AgentViewResponse {

    private String agentName;
    private String estimatedStatus;
    private String deviceIdWord;
    private LocalDateTime timeStamp;
    private int streamingFrequency;
    private String serverIp;
}
