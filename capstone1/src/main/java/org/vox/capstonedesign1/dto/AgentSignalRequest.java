package org.vox.capstonedesign1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgentSignalRequest {

    private double deviceSerialNumber;
    private Long statusId;
    private String timeStamp;
    private double streamingFrequency;

    @Builder
    public AgentSignalRequest(double deviceSerialNumber, Long statusId, String timeStamp, double streamingFrequency) {
        this.deviceSerialNumber = deviceSerialNumber;
        this.statusId = statusId;
        this.timeStamp = timeStamp;
        this.streamingFrequency = streamingFrequency;
    }
}
