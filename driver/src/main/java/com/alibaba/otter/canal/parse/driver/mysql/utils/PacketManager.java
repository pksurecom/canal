package com.alibaba.otter.canal.parse.driver.mysql.utils;

import java.io.IOException;
import com.alibaba.otter.canal.parse.driver.mysql.packets.HeaderPacket;

public abstract class PacketManager {

    public static HeaderPacket readHeader(SocketChannel ch, int len) throws IOException {
        HeaderPacket header = new HeaderPacket();
        header.fromBytes(ch.read(len));
        return header;
    }

    public static byte[] readBytes(SocketChannel ch, int len) throws IOException {
        return ch.read(len);
    }

    public static void writePkg(SocketChannel ch, byte[]... srcs) throws IOException {
        ch.writeChannel(srcs);
    }
    
    public static void writeBody(SocketChannel ch, byte[] body) throws IOException {
    	writeBody0(ch, body, (byte) 0);
    }

    public static void writeBody0(SocketChannel ch, byte[] body, byte packetSeqNumber) throws IOException {
        HeaderPacket header = new HeaderPacket();
        header.setPacketBodyLength(body.length);
        header.setPacketSequenceNumber(packetSeqNumber);
        ch.writeChannel(header.toBytes(),body);
    }
}
