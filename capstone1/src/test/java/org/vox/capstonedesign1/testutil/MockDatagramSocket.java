package org.vox.capstonedesign1.testutil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MockDatagramSocket extends DatagramSocket {
    private final byte[] data;
    private final AtomicBoolean used = new AtomicBoolean(false);

    public MockDatagramSocket(byte[] data) throws SocketException {
        this.data = data;
    }

    @Override
    public void receive(DatagramPacket packet) throws IOException {
        if (used.get()) {
            // 무한 루프 방지용
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            return;
        }
        used.set(true); // 한 번만 데이터 전송
        packet.setData(data);
        packet.setLength(data.length);
    }

    @Override
    public void close() {
        // override to prevent actual socket closing issues
    }
}

//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class FakeDatagramSocket extends DatagramSocket {
//
//    private final BlockingQueue<DatagramPacket> packetQueue = new LinkedBlockingQueue<>();
//    private boolean closed = false;
//
//    public FakeDatagramSocket() throws SocketException {
//        super(0); // 임의 포트 사용
//    }
//
//    // 테스트용: 외부에서 패킷을 추가할 수 있도록
//    public void injectPacket(DatagramPacket packet) {
//        packetQueue.offer(packet);
//    }
//
//    @Override
//    public void receive(DatagramPacket packet) {
//        try {
//            DatagramPacket incoming = packetQueue.take(); // 큐에서 가져옴
//            packet.setData(incoming.getData(), 0, incoming.getLength());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @Override
//    public void close() {
//        closed = true;
//    }
//
//    @Override
//    public boolean isClosed() {
//        return closed;
//    }
//}
