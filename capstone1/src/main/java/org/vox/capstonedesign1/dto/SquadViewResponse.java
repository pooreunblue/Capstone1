package org.vox.capstonedesign1.dto;

import lombok.Getter;
import org.vox.capstonedesign1.domain.Squad;

@Getter
public class SquadViewResponse {

    private final Long squadId;
    private final String squadName;

    public SquadViewResponse(Squad squad) {
        this.squadId = squad.getId();
        this.squadName = squad.getSquadName();
    }
}
