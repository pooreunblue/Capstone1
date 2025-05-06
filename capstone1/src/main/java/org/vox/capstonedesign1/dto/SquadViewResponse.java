package org.vox.capstonedesign1.dto;

import lombok.Builder;
import lombok.Getter;
import org.vox.capstonedesign1.domain.Squad;

@Getter
@Builder
public class SquadViewResponse {

    private final Long squadId;
    private final String squadName;

    public SquadViewResponse(Squad squad) {
        this.squadId = squad.getSquadId();
        this.squadName = squad.getSquadName();
    }
}
