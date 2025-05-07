package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.repository.AgentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    public List<String> getDeviceSerialNumbers(Long squadId) {
        List<Agent> agents = agentRepository.findBySquad_IdOrderByIdAsc(squadId);
        return agents.stream()
                .map(agent -> agent.getDevice().getDeviceSerialNumber())
                .collect(Collectors.toList());
    }
}
