package org.vox.capstonedesign1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class SquadListViewResponse {

    private String squadName;
    private List<String> agentNames;
}
