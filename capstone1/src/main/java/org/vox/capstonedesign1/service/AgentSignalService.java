package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.AgentSignal;
import org.vox.capstonedesign1.domain.Device;
import org.vox.capstonedesign1.domain.EstimatedStatus;
import org.vox.capstonedesign1.dto.AgentSignalRequest;
import org.vox.capstonedesign1.repository.AgentSignalRepository;
import org.vox.capstonedesign1.repository.DeviceRepository;
import org.vox.capstonedesign1.repository.EstimatedStatusRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentSignalService {

    private final AgentSignalRepository agentSignalRepository;
    private final DeviceRepository deviceRepository;
    private final EstimatedStatusRepository estimatedStatusRepository;

    public AgentSignal saveSignal(AgentSignalRequest request) {
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디바이스 ID"));
        EstimatedStatus estimatedStatus = estimatedStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상황 ID"));
        LocalDateTime timeStamp = LocalDateTime.parse(request.getTimeStamp(), DateTimeFormatter.ISO_DATE_TIME);
        AgentSignal agentSignal = AgentSignal.builder()
                .device(device)
                .estimatedStatus(estimatedStatus)
                .timeStamp(timeStamp)
                .build();
        return agentSignalRepository.save(agentSignal);
    }
}
