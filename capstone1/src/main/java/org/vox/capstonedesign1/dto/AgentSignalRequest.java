package org.vox.capstonedesign1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgentSignalRequest {

    private Long deviceId;
    private Long statusId;
    private String timeStamp;
    private int streamingFrequency;
}
