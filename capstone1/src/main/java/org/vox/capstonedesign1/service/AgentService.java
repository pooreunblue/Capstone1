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

    public List<String> getDeviceSerialNumbersBySquadId(Long squadId) {
        List<Agent> agents = agentRepository.findBySquad_IdOrderByIdAsc(squadId);
        return agents.stream()
                .map(agent -> agent.getDevice().getDeviceSerialNumber())
                .collect(Collectors.toList());
    }

    public String getDeviceSerialNumberBySquadIdAndId(Long squadId, Long agentId) {
        Agent agent = agentRepository.findBySquad_IdAndId(squadId, agentId)
                .orElseThrow(() -> new IllegalArgumentException("소대" + squadId + "에 요원" + agentId + "가 존재하지 않습니다."));
        return agent.getDevice().getDeviceSerialNumber();
    }
}
