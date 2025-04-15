package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vox.capstonedesign1.domain.Squad;

import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long> {
    @Query("SELECT s FROM Squad s JOIN FETCH s.agents")
    List<Squad> findAllWithAgents();
}
