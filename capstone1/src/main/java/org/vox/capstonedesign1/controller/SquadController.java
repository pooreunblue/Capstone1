package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vox.capstonedesign1.dto.SquadListViewResponse;
import org.vox.capstonedesign1.service.SquadService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class SquadController {

    private final SquadService squadService;

    /**
     * [GET] /main/squads
     * 전체 소대 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<SquadListViewResponse>> findAllSquads() {
        List<SquadListViewResponse> squads = squadService.findAll()
                .stream()
                .map(SquadListViewResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(squads);
    }
}
