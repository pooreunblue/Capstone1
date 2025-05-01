package org.vox.capstonedesign1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveAgentSignalRequest {

    private String deviceIdWord;
    private Long statusId;
    private String timeStamp;
    private int streamingFrequency;

    @Builder
    public SaveAgentSignalRequest(String deviceIdWord, Long statusId, String timeStamp, int streamingFrequency) {
        this.deviceIdWord = deviceIdWord;
        this.statusId = statusId;
        this.timeStamp = timeStamp;
        this.streamingFrequency = streamingFrequency;
    }
}
