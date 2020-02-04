package com.webflux.moonsoo.asyncstudy.nio;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketCallbackImpl implements SocketCallback {
    private SocketChannel client;

    public SocketCallbackImpl(SocketChannel client) {
        this.client = client;
    }

    @Override
    public void callback(byte[] response) throws IOException {
        client.write(ByteBuffer.wrap(response));
        close();
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
