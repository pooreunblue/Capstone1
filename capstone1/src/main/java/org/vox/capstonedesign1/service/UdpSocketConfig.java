package org.vox.capstonedesign1.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;

@Configuration
public class UdpSocketConfig {

    @Bean
    public DatagramSocket datagramSocket() throws Exception {
        return new DatagramSocket(9999);
    }
}
