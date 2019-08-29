package com.webflux.moonsoo.asyncstudy.reactor;

import java.util.Objects;

public class FluxArray<T> extends Flux<T> {
    final T[] values;

    FluxArray(T[] values) {
        this.values = Objects.requireNonNull(values, "value");
    }


    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new ArraySubscription<>(values, subscriber));
    }
}
