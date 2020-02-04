package com.webflux.moonsoo.asyncstudy.nio;

import java.io.IOException;

public interface NioHandler {

    void handleRequest(byte[] body, String path) throws IOException;
}
