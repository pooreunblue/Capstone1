package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.AgentSignal;

import java.util.List;
import java.util.Optional;

public interface AgentSignalRepository extends JpaRepository<AgentSignal, Long> {
    Optional<AgentSignal> findTopByDevice_DeviceIdOrderByTimeStampDesc(Long deviceId);
}
