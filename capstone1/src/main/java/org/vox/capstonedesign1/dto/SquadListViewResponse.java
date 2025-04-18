package org.vox.capstonedesign1.dto;

import lombok.Builder;
import lombok.Getter;
import org.vox.capstonedesign1.domain.Squad;

@Builder
@Getter
public class SquadListViewResponse {

    private final Long squadId;
    private final String squadName;

    public SquadListViewResponse(Squad squad) {
        this.squadId = squad.getSquadId();
        this.squadName = squad.getSquadName();
    }
}
