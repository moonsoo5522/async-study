package com.webflux.moonsoo.asyncstudy.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NioProtocol {
    private NioHeader header;
    private byte[] body;
    private int curPos;

    public NioProtocol(byte[] array, int len) {
        if(len < 8) {
            throw new RuntimeException("header is invalid");
        }
        this.header = new NioHeader(array);
        int localPos = header.getHeaderLength();

        byte[] bodyByteArray = new byte[header.getLength()];
        System.arraycopy(array, localPos, bodyByteArray, 0, len - localPos);
        this.body = bodyByteArray;

        this.curPos = len - 4;
    }

    public String getBodyAsString() {
        return new String(this.body, StandardCharsets.UTF_8);
    }

    public byte[] getBody() {
        return body;
    }

    public void add(byte[] array, int len) {
        System.arraycopy(array, 0, body, curPos, len);
        curPos += len;
    }

    public boolean isComplete() {
        return curPos == header.getLength();
    }

    public NioHeader getHeader() {
        return header;
    }
}
