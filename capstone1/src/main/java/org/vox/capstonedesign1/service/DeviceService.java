package org.vox.capstonedesign1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vox.capstonedesign1.domain.Device;
import org.vox.capstonedesign1.repository.DeviceRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public double resolveDeviceSerialNumber(long deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        return device.map(Device::getDeviceSerialNumber)
                .orElse(-1.0);
    }
}
