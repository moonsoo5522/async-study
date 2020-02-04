package com.webflux.moonsoo.asyncstudy.nio;

import java.io.Closeable;
import java.io.IOException;

public interface SocketCallback extends Closeable {

    void callback(byte[] response) throws IOException;
}
