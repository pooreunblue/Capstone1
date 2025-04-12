package org.vox.capstonedesign1.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;
import java.net.SocketException;

@Configuration
public class UdpSocketConfig {

    @Bean
    public DatagramSocket datagramSocket() throws SocketException {
        return new DatagramSocket(9999);
    }
}
