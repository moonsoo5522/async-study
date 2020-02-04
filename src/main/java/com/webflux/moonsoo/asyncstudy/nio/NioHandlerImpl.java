package com.webflux.moonsoo.asyncstudy.nio;

import com.webflux.moonsoo.asyncstudy.reactor.Flux;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class NioHandlerImpl implements NioHandler {
    private NidController nidController;
    private SocketCallback socketCallback;

    public NioHandlerImpl(SocketCallback socketCallback) {
        this.nidController = new NidController();
        this.socketCallback = socketCallback;
    }

    @Override
    public void handleRequest(byte[] body, String path) throws IOException {
        for(Method method : nidController.getClass().getMethods()) {
            NioRequestMapping annotation = method.getAnnotation(NioRequestMapping.class);
            if(annotation.path().equals(path)) {
                Flux<?> flux = (Flux<?>) ReflectionUtils.invokeMethod(method, nidController, body);
                if(flux == null) {
                    socketCallback.close();
                }
                flux.subscribe(data -> socketCallback.callback((byte[]) data));
                return;
            }
        }
    }
}
