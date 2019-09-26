package com.webflux.moonsoo.asyncstudy.reactor;

import java.util.Objects;

public abstract class FluxOperator<I, O> extends Flux<O> {
    protected final Flux<? extends I> source;

    protected FluxOperator(Flux<? extends I> source) {
        this.source = (Flux) Objects.requireNonNull(source);
    }
}
