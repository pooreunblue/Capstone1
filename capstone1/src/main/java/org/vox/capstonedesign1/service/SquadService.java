package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Squad;
import org.vox.capstonedesign1.repository.SquadRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SquadService {

    private final SquadRepository squadRepository;

    public List<Squad> findAll() {
        return squadRepository.findAll();
    }
}

