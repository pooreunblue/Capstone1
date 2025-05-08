package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.Agent;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByAgentName(String agentName);
    Optional<Agent> findBySquad_IdAndId(Long squadId, Long agentId);
    Optional<Agent> findByDevice_DeviceSerialNumber(String deviceSerialNumber);
    List<Agent> findBySquad_IdOrderByIdAsc(Long squadId);
}
