package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.dto.SquadListViewResponse;
import org.vox.capstonedesign1.repository.SquadRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SquadService {

    private final SquadRepository squadRepository;

    public List<SquadListViewResponse> getAllSquadsWithAgents() {
        return squadRepository.findAllWithAgents()
                .stream()
                .map(squad -> {
                    List<String> agentNames = squad.getAgents().stream()
                            .map(Agent::getAgentName)
                            .collect(Collectors.toList());
                    return SquadListViewResponse.builder()
                            .squadName(squad.getSquadName())
                            .agentNames(agentNames)
                            .build();
                })
                .collect(Collectors.toList());
    }
}

