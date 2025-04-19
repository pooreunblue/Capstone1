package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.Agent;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findBySquad_SquadIdAndAgentId(Long squadId, Long agentId);
    Optional<Agent> findByDevice_DeviceId(Long deviceId);
    List<Agent> findBySquad_SquadIdOrderByAgentIdAsc(Long squadId);
}
