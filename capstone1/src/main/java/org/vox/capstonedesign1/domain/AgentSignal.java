package org.vox.capstonedesign1.domain;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity // 엔티티 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
@Table(name = "agent_signal")
public class AgentSignal {

    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;

    @Builder
    public AgentSignal(Device device, Incident incident, LocalDateTime timeStamp) {
        this.device = device;
        this.incident = incident;
        this.timeStamp = timeStamp;
    }
}
