package com.webflux.moonsoo.asyncstudy.nio;

import com.webflux.moonsoo.asyncstudy.reactor.Flux;
import com.webflux.moonsoo.asyncstudy.reactor.Publisher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class NioStudy implements Runnable {

    private void handle(ByteBuffer buffer, SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        int len = client.read(buffer);

        byte[] array = buffer.array();

        NioProtocol nioProtocol;
        if(key.attachment() == null) {
            nioProtocol = new NioProtocol(array, len);
            key.attach(nioProtocol);
        } else {
            nioProtocol = (NioProtocol) key.attachment();
            nioProtocol.add(array, len);
        }
        buffer.clear();

        if(nioProtocol.isComplete()) {
            // default async task
            SocketCallbackImpl callback = new SocketCallbackImpl(client);
            NioHandler handler = new NioHandlerImpl(callback);
            handler.handleRequest(nioProtocol.getBody(), nioProtocol.getHeader().getPathInfo().getPath());
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    @Override
    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 12345));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(256);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        handle(buffer, key);
                    }
                    iter.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
