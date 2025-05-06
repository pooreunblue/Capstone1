package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.Squad;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.repository.AgentRepository;
import org.vox.capstonedesign1.repository.SquadRepository;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.service.SquadService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class AgentViewController {

    private final AgentSignalService agentSignalService;
    private final AgentRepository agentRepository;
    private final SquadService squadService;

    /**
     * [GET] /main/squads/{squadId}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{squadId}")
    public String getAllAgentsInSquad(@PathVariable Long squadId, Model model) {
        Squad squad = squadService.findById(squadId);
        List<String> deviceSerialNumbers = agentSignalService.getDeviceSerialNumbers(squadId);
        List<AgentViewResponse> agentStatuses = agentSignalService.getLatestSignalsForDevices(deviceSerialNumbers);
        model.addAttribute("squad", squad);
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
