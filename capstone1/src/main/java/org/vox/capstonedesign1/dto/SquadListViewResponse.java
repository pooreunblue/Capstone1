package org.vox.capstonedesign1.dto;

import lombok.Getter;
import org.vox.capstonedesign1.domain.Squad;

@Getter
public class SquadListViewResponse {

    private final Long squadId;
    private final String squadName;

    public SquadListViewResponse(Squad squad) {
        this.squadId = squad.getSquadId();
        this.squadName = squad.getSquadName();
    }
}
