package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.AgentSignal;

import java.util.Optional;

public interface AgentSignalRepository extends JpaRepository<AgentSignal, Long> {
    Optional<AgentSignal> findTopByDevice_DeviceSerialNumberOrderByTimeStampDesc(double deviceSerialNumber);
    Optional<AgentSignal> findTopByAgentOrderByTimeStampDesc(Agent agent);
}
