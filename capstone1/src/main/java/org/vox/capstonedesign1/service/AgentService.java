package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.AgentSignal;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.repository.AgentRepository;
import org.vox.capstonedesign1.repository.AgentSignalRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final AgentSignalRepository agentSignalRepository;

    public List<Agent> getAgentsBySquadId(Long squadId) {
        return agentRepository.findBySquad_IdOrderById(squadId);
    }

    public List<AgentViewResponse> getAgentStatusesBySquadId(Long squadId) {
        List<Agent> agents = agentRepository.findBySquad_IdOrderById(squadId);
        return agents.stream()
                .map(agent -> {
                    Optional<AgentSignal> signalOpt = agentSignalRepository.findTopByAgentOrderByTimeStampDesc(agent);
                    return signalOpt.map(signal -> new AgentViewResponse(agent, signal)).orElseThrow(() -> new RuntimeException("요원 " + agent + "의 signal을 찾을 수 없습니다."));
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public String getDeviceSerialNumberBySquadIdAndId(Long squadId, Long agentId) {
        Agent agent = agentRepository.findBySquad_IdAndId(squadId, agentId)
                .orElseThrow(() -> new IllegalArgumentException("소대" + squadId + "에 요원" + agentId + "가 존재하지 않습니다."));
        return agent.getDevice().getDeviceSerialNumber();
    }
}
