package org.vox.capstonedesign1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AgentSignalResponse {
    private Long deviceId;
    private String deviceName;
    private String estimatedStatus;
    private LocalDateTime timeStamp;
}
