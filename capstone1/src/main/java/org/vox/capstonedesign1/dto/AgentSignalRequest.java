package org.vox.capstonedesign1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgentSignalRequest {

    private Long deviceId;
    private Long signalId;
    private String timeStamp;

}
