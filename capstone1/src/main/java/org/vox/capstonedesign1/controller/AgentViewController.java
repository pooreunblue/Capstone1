package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AgentViewController.class);

    private final AgentSignalService agentSignalService;
    private final SquadService squadService;
    private final AgentService agentService;

    /**
     * [GET] /main/squads/{squadId}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{id}")
    public String getAllAgentsInSquad(@PathVariable Long id, Model model) {
        logger.info("🔍 요청된 Squad ID: {}", id);
        Squad squad = squadService.findById(id);
        List<String> deviceSerialNumbers = agentService.getDeviceSerialNumbersBySquadId(id);
        List<AgentViewResponse> agentStatuses = agentSignalService.getLatestSignalsForDevices(deviceSerialNumbers);
        logger.info("👥 가져온 에이전트 수: {}", agentStatuses.size());
        agentStatuses.forEach(status -> log.info("Agent: {}", status.getAgentName()));
        model.addAttribute("squad", new SquadViewResponse(squad));
        model.addAttribute("agentStatuses", agentStatuses);
        return "squad-detail";
    }

    /**
     * [GET] /main/squads/{squadId}/agent/{agentId}
     * 특정 요원의 최신 상태 조회
     */
    @GetMapping("/{id}/agents/{agentId}")
    public ResponseEntity<AgentViewResponse> getAgentStatus(@PathVariable Long id, @PathVariable Long agentId, Model model) {
        String deviceSerialNumber = agentService.getDeviceSerialNumberBySquadIdAndId(id, agentId);
        AgentViewResponse agentStatus = agentSignalService.getLatestSignalByDeviceSerialNumber(deviceSerialNumber);
        model.addAttribute("agentStatus", agentStatus);
        return ResponseEntity.ok(agentStatus);
    }
}
