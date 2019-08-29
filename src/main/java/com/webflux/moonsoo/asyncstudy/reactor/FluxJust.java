package com.webflux.moonsoo.asyncstudy.reactor;

import java.util.Objects;

public class FluxJust<T> extends Flux<T> {

    final T data;

    FluxJust(T data) {
        this.data = Objects.requireNonNull(data, "value");
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new JustSubscription(data, subscriber));
    }
}
