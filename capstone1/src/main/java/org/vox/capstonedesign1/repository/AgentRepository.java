package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.Agent;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByDevice_DeviceId(Long deviceId);
}
