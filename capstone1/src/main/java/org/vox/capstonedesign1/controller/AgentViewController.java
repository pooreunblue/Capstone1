package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.AgentSignal;
import org.vox.capstonedesign1.domain.Squad;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.dto.SquadViewResponse;
import org.vox.capstonedesign1.service.AgentService;
import org.vox.capstonedesign1.service.AgentSignalService;
import org.vox.capstonedesign1.service.SquadService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class AgentViewController {

    private final SquadService squadService;
    private final AgentService agentService;

    /**
     * [GET] /main/squads/{id}
     * 특정 소대의 요원 전체 상태 조회
     */
    @GetMapping("/{id}")
    public String getAllAgentStatusInSquad(@PathVariable Long id, Model model) {
        Squad squad = squadService.findById(id);
        SquadViewResponse squadViewResponse = new SquadViewResponse(squad);
        List<AgentViewResponse> statuses = agentService.getAgentStatusesBySquadId(id);
        model.addAttribute("squad", squadViewResponse);
        model.addAttribute("statuses", statuses);
        return "squadDetail";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<AgentViewResponse>> findAgents(@PathVariable Long id) {
//        List<Agent> agents = agentService.getAgentsBySquadId(id);
//        List<AgentViewResponse> agentStatuses = agents.stream()
//                .map(agent -> {
//                    Optional<AgentSignal> latestSignalOpt = agentSignalService.findLatestSignalByAgentName(agent.getAgentName());
//                    return latestSignalOpt.map(AgentViewResponse::new).orElse(null);
//                })
//                .filter(Objects::nonNull)
//                .toList();
//
//        return ResponseEntity.ok()
//                .body(agentStatuses);
//    }

//    /**
//     * [GET] /main/squads/{squadId}/agent/{agentId}
//     * 특정 요원의 최신 상태 조회
//     */
//    @GetMapping("/{id}/agents/{agentId}")
//    public ResponseEntity<AgentViewResponse> getAgentStatus(@PathVariable Long id, @PathVariable Long agentId, Model model) {
//        String deviceSerialNumber = agentService.getDeviceSerialNumberBySquadIdAndId(id, agentId);
//        AgentViewResponse agentStatus = agentSignalService.getLatestSignalByDeviceSerialNumber(deviceSerialNumber);
//        model.addAttribute("agentStatus", agentStatus);
//        return ResponseEntity.ok(agentStatus);
//    }
}
