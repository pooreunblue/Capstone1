package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vox.capstonedesign1.domain.Signal;

@Repository
public interface SignalRepository extends JpaRepository<Signal, Long> {
}
