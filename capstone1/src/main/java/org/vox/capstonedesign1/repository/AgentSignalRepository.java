package org.vox.capstonedesign1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vox.capstonedesign1.domain.AgentSignal;

import java.util.List;
import java.util.Optional;

public interface AgentSignalRepository extends JpaRepository<AgentSignal, Long> {
//  CrudRepository에 구현되어있음
//  AgentSignal save(AgentSignal signal);
//  Optional<AgentSignal> findById(Long id);
    List<AgentSignal> findAllByDeviceId(int deviceId);
    // 데이터를 최신시간순으로 정렬해서 가져오기
    List<AgentSignal> findAllByOrderByTimeStampDesc();
}
