package com.webflux.moonsoo.asyncstudy.nio;

import com.webflux.moonsoo.asyncstudy.reactor.Flux;

import java.nio.charset.StandardCharsets;

public class NidController {

    @NioRequestMapping(path = "/hello")
    public Flux<String> hello(byte[] data) {
        return Flux.just(new String(data, StandardCharsets.UTF_8));
    }
}
