package org.vox.capstonedesign1.domain;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
@Table(name = "agent_signal")
public class AgentSignal {

    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_serial_number", referencedColumnName = "device_serial_number", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private EstimatedStatus estimatedStatus;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "streaming_frequency")
    private int streamingFrequency;

    @Builder
    public AgentSignal(Device device, EstimatedStatus estimatedStatus, LocalDateTime timeStamp, int streamingFrequency) {
        this.device = device;
        this.estimatedStatus = estimatedStatus;
        this.timeStamp = timeStamp;
        this.streamingFrequency = streamingFrequency;
    }
}
