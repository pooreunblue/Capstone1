package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.dto.SquadListViewResponse;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.service.SquadService;
import org.vox.capstonedesign1.repository.AgentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class SquadController {

    private final SquadService squadService;
    private final AgentSignalService agentSignalService;
    private final AgentRepository agentRepository;

    /**
     * [GET] /main/squads
     * 전체 소대 목록 조회
     */
    @GetMapping
    public List<SquadListViewResponse> getAllSquads() {
        return squadService.getAllSquadsWithAgents();
    }

    /**
     * [GET] /main/squads/{squadId}_agents
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{squadId}_agents")
    public List<AgentViewResponse> getAllAgentsInSquad(@PathVariable Long squadId) {
        List<Long> deviceIds = agentRepository.findBySquad_SquadId(squadId).stream()
                .map(agent -> agent.getDevice().getDeviceId())
                .collect(Collectors.toList());
        return agentSignalService.getLatestSignalsForDevices(deviceIds);
    }

    /**
     * [GET] /main/squads/{squadId}_agents/{agentId}
     * 특정 요원의 최신 상태 조회
     */
    @GetMapping("/{squadId}_agents/{agentId}")
    public AgentViewResponse getAgentStatus(@PathVariable Long squadId,
                                            @PathVariable Long agentId) {
        Long deviceId = agentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("agentId 잘못됨"))
                .getDevice()
                .getDeviceId();
        return agentSignalService.getLatestSignalByDeviceId(deviceId);
    }
}
