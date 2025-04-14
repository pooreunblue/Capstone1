package org.vox.capstonedesign1.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SquadAgentsResponse {
    private String squadName;
    private List<String> agentNames;
}
