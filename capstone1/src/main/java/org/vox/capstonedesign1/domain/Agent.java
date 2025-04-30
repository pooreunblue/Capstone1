package org.vox.capstonedesign1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;

    @Column(name = "agent_name", nullable = false)
    private String agentName;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", unique = true)
    private Device device;
}
