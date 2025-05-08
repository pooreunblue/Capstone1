package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.repository.AgentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    public List<Agent> getAgentsBySquadId(Long squadId) {
        return agentRepository.findBySquad_IdOrderByIdAsc(squadId);
    }

    public String getDeviceSerialNumberBySquadIdAndId(Long squadId, Long agentId) {
        Agent agent = agentRepository.findBySquad_IdAndId(squadId, agentId)
                .orElseThrow(() -> new IllegalArgumentException("소대" + squadId + "에 요원" + agentId + "가 존재하지 않습니다."));
        return agent.getDevice().getDeviceSerialNumber();
    }
}
