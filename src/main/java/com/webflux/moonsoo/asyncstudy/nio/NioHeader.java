package com.webflux.moonsoo.asyncstudy.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NioHeader {
    private int length;
    private int pathLength;
    private PathInfo pathInfo;

    public NioHeader(byte[] array) {
        byte[] headerByteArray = new byte[4];
        System.arraycopy(array, 0, headerByteArray, 0, 4);
        this.length = get(headerByteArray);

        System.arraycopy(array, 4, headerByteArray, 0, 4);
        int pathLength = get(headerByteArray);
        this.pathLength = pathLength;

        if(pathLength > 248) {
            throw new RuntimeException();
        }

        this.pathInfo = new PathInfo(new String(ByteBuffer.wrap(array, 8, 8 + pathLength).array(), StandardCharsets.UTF_8));

    }

    public int getLength() {
        return length;
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public int getHeaderLength() {
        return 8 + pathLength;
    }

    private int get(byte[] bytes) {
        return ((((int)bytes[0] & 0xff) << 24) |
                (((int)bytes[1] & 0xff) << 16) |
                (((int)bytes[2] & 0xff) << 8) |
                (((int)bytes[3] & 0xff)));
    }
}
