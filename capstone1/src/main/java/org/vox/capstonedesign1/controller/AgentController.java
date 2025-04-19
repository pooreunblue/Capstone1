package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.repository.AgentRepository;
import org.vox.capstonedesign1.service.AgentSignalService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class AgentController {

    private final AgentSignalService agentSignalService;
    private final AgentRepository agentRepository;

    /**
     * [GET] /main/squads/{squadId}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{squadId}")
    public ResponseEntity<List<AgentViewResponse>> getAllAgentsInSquad(@PathVariable Long squadId) {
        List<Agent> agents = agentRepository.findBySquad_SquadIdOrderByAgentIdAsc(squadId);
        List<Long> deviceIds = agents.stream()
                .map(agent -> agent.getDevice().getDeviceId())
                .collect(Collectors.toList());
        List<AgentViewResponse> agentStatuses = agentSignalService.getLatestSignalsForDevices(deviceIds);
        return ResponseEntity.ok(agentStatuses);
    }

    /**
     * [GET] /main/squads/{squadId}/agent/{agentId}
     * 특정 요원의 최신 상태 조회
     */
    @GetMapping("/{squadId}/agents/{agentId}")
    public ResponseEntity<AgentViewResponse> getAgentStatus(@PathVariable Long squadId,@PathVariable Long agentId) {
        Agent agent = agentRepository.findBySquad_SquadIdAndAgentId(squadId, agentId)
                .orElseThrow(() -> new IllegalArgumentException("소대" + squadId + "에 요원" + agentId + "가 존재하지 않습니다."));
        Long deviceId = agent.getDevice().getDeviceId();
        AgentViewResponse response= agentSignalService.getLatestSignalByDeviceId(deviceId);
        return ResponseEntity.ok(response);
    }
}
