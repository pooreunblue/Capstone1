package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vox.capstonedesign1.domain.Squad;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.dto.SquadViewResponse;
import org.vox.capstonedesign1.service.AgentService;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.service.SquadService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class AgentViewController {

    private final AgentSignalService agentSignalService;
    private final SquadService squadService;
    private final AgentService agentService;

    /**
     * [GET] /main/squads/{squadId}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{squadId}")
    public String getAllAgentsInSquad(@PathVariable Long squadId, Model model) {
        Squad squad = squadService.findById(squadId);
        List<String> deviceSerialNumbers = agentService.getDeviceSerialNumbersBySquadId(squadId);
        List<AgentViewResponse> agentStatuses = agentSignalService.getLatestSignalsForDevices(deviceSerialNumbers);
        log.info("agentStatuses size: {}", agentStatuses.size());
        agentStatuses.forEach(status -> log.info("Agent: {}", status.getAgentName()));
        model.addAttribute("squad", new SquadViewResponse(squad));
        model.addAttribute("agentStatuses", agentStatuses);
        return "squad-detail";
    }

    /**
     * [GET] /main/squads/{squadId}/agent/{agentId}
     * 특정 요원의 최신 상태 조회
     */
    @GetMapping("/{squadId}/agents/{agentId}")
    public ResponseEntity<AgentViewResponse> getAgentStatus(@PathVariable Long squadId, @PathVariable Long agentId, Model model) {
        String deviceSerialNumber = agentService.getDeviceSerialNumberBySquadIdAndId(squadId, agentId);
        AgentViewResponse agentStatus = agentSignalService.getLatestSignalByDeviceSerialNumber(deviceSerialNumber);
        model.addAttribute("agentStatus", agentStatus);
        return ResponseEntity.ok(agentStatus);
    }
}
