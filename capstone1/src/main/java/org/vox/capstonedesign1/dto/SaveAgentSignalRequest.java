package org.vox.capstonedesign1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveAgentSignalRequest {

    private String agentName;
    private String deviceSerialNumber;
    private Long statusId;
    private String timeStamp;
    private int streamingFrequency;

    @Builder
    public SaveAgentSignalRequest(String agentName, String deviceSerialNumber, Long statusId, String timeStamp, int streamingFrequency) {
        this.agentName = agentName;
        this.deviceSerialNumber = deviceSerialNumber;
        this.statusId = statusId;
        this.timeStamp = timeStamp;
        this.streamingFrequency = streamingFrequency;
    }
}
