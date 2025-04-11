package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vox.capstonedesign1.domain.EstimationSignal;

@Repository
public interface EstimationSignalRepository extends JpaRepository<EstimationSignal, Long> {
}
