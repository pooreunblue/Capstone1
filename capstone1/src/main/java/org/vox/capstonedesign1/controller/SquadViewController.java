package org.vox.capstonedesign1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vox.capstonedesign1.dto.SquadListViewResponse;
import org.vox.capstonedesign1.service.SquadService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/squads")
public class SquadViewController {

    private final SquadService squadService;

    /**
     * [GET] /main/squads
     * 전체 소대 목록 조회
     */
    @GetMapping
    public String findAllSquads(Model model) {
        List<SquadListViewResponse> squads = squadService.findAll()
                .stream()
                .map(SquadListViewResponse::new)
                .toList();
        model.addAttribute("squads", squads);
        return "squadList";
    }
}
