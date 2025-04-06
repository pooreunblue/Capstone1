package org.vox.capstonedesign1.repository;

import org.vox.capstonedesign1.domain.AgentSignal;

import java.util.List;
import java.util.Optional;

public class AgentSignalRepositoryImpl implements AgentSignalRepository {
    @Override
    public AgentSignal save(AgentSignal signal) {
        return null;
    }

    @Override
    public Optional<AgentSignal> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<AgentSignal> findByDeviceId(int deviceId) {
        return Optional.empty();
    }

    @Override
    public List<AgentSignal> findAllByOrderByTimeStampDesc() {
        return List.of();
    }
}
