package org.vox.capstonedesign1.util.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Configuration
public class UdpSocketConfig {

    @Bean
    public DatagramSocket datagramSocket() throws SocketException, UnknownHostException {
        return new DatagramSocket(9999, InetAddress.getByName("0.0.0.0"));
    }
}
