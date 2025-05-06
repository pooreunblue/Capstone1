package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.repository.AgentRepository;
import org.vox.capstonedesign1.service.AgentSignalService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class AgentViewController {

    private final AgentSignalService agentSignalService;
    private final AgentRepository agentRepository;

    /**
     * [GET] /main/squads/{squadId}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{squadId}")
    public String getAllAgentsInSquad(@PathVariable Long squadId, Model model) {
        List<Agent> agents = agentRepository.findBySquad_SquadIdOrderByAgentIdAsc(squadId);
        List<String> deviceSerialNumbers = agents.stream()
                .map(agent -> agent.getDevice().getDeviceSerialNumber())
                .collect(Collectors.toList());
        List<AgentViewResponse> agentStatuses = agentSignalService.getLatestSignalsForDevices(deviceSerialNumbers);
        model.addAttribute("agentStatuses", agentStatuses);
        return "squad-detail";
    }

    /**
     * [GET] /main/squads/{squadId}/agent/{agentId}
     * 특정 요원의 최신 상태 조회
     */
    @GetMapping("/{squadId}/agents/{agentId}")
    public ResponseEntity<AgentViewResponse> getAgentStatus(@PathVariable Long squadId,@PathVariable Long agentId) {
        Agent agent = agentRepository.findBySquad_SquadIdAndAgentId(squadId, agentId)
                .orElseThrow(() -> new IllegalArgumentException("소대" + squadId + "에 요원" + agentId + "가 존재하지 않습니다."));
        String deviceSerialNumber = agent.getDevice().getDeviceSerialNumber();
        AgentViewResponse response= agentSignalService.getLatestSignalByDeviceSerialNumber(deviceSerialNumber);
        return ResponseEntity.ok(response);
    }
}
