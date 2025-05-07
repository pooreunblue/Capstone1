package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Agent;
import org.vox.capstonedesign1.domain.AgentSignal;
import org.vox.capstonedesign1.domain.Device;
import org.vox.capstonedesign1.domain.EstimatedStatus;
import org.vox.capstonedesign1.dto.SaveAgentSignalRequest;
import org.vox.capstonedesign1.dto.AgentViewResponse;
import org.vox.capstonedesign1.repository.AgentRepository;
import org.vox.capstonedesign1.repository.AgentSignalRepository;
import org.vox.capstonedesign1.repository.DeviceRepository;
import org.vox.capstonedesign1.repository.EstimatedStatusRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentSignalService {

    private final AgentSignalRepository agentSignalRepository;
    private final AgentRepository agentRepository;
    private final DeviceRepository deviceRepository;
    private final EstimatedStatusRepository estimatedStatusRepository;

    public void saveSignal(SaveAgentSignalRequest request) {
        Device device = fetchDeviceBySerialNumber(request);
        EstimatedStatus estimatedStatus = fetchStatusById(request);
        LocalDateTime timeStamp = LocalDateTime.parse(request.getTimeStamp(), DateTimeFormatter.ISO_DATE_TIME);
        AgentSignal agentSignal = AgentSignal.builder()
                .device(device)
                .estimatedStatus(estimatedStatus)
                .timeStamp(timeStamp)
                .build();
        agentSignalRepository.save(agentSignal);
    }

    public List<String> getDeviceSerialNumbers(Long squadId) {
        List<Agent> agents = agentRepository.findBySquad_SquadIdOrderByAgentIdAsc(squadId);
        return agents.stream()
                .map(agent -> agent.getDevice().getDeviceSerialNumber())
                .collect(Collectors.toList());
    }

    public AgentViewResponse getLatestSignalByDeviceSerialNumber(String deviceSerialNumber) {
        AgentSignal latestSignal = agentSignalRepository.findTopByDevice_DeviceSerialNumberOrderByTimeStampDesc(deviceSerialNumber)
                .orElseThrow(() -> new IllegalArgumentException("deviceSerialNumber" + deviceSerialNumber + "의 데이터가 없습니다."));
        Agent agent = agentRepository.findByDevice_DeviceSerialNumber(deviceSerialNumber)
                .orElseThrow(() -> new IllegalArgumentException("deviceSerialNumber" + deviceSerialNumber + "에 연결된 agent가 없습니다."));
        return AgentViewResponse.builder()
                .agentName(agent.getAgentName())
                .estimatedStatus(latestSignal.getEstimatedStatus().getStatusInformation())
                .deviceSerialNumber(latestSignal.getDevice().getDeviceSerialNumber())
                .timeStamp(latestSignal.getTimeStamp())
                .streamingFrequency(latestSignal.getStreamingFrequency())
                .serverIp("192.168.0.0")
                .build();
    }

    // 여러 명의 최신 상태 조회 (ex: 최대 6명)
    public List<AgentViewResponse> getLatestSignalsForDevices(List<String> deviceSerialNumbers) {
        return deviceSerialNumbers.stream()
                .map(this::getLatestSignalByDeviceSerialNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Device fetchDeviceBySerialNumber(SaveAgentSignalRequest request) {
        return deviceRepository.findByDeviceSerialNumber(request.getDeviceSerialNumber())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디바이스 일련번호"));
    }

    private EstimatedStatus fetchStatusById(SaveAgentSignalRequest request) {
        return estimatedStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상황 ID"));
    }
}
