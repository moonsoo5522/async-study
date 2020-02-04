package com.webflux.moonsoo.asyncstudy.reactor;

import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Scheduler;

import java.util.Objects;

public abstract class Flux<T> implements Publisher<T> {
    private static Flux lastFlux;

    public static <T> Flux<T> just(T data) {
        lastFlux = new FluxJust<>(data);
        return lastFlux;
    }

    public static <T> Flux<T> just(T... data) {
        lastFlux = new FluxArray<>(data);
        return lastFlux;
    }

    public <T> Flux<T> take(long n) {
        lastFlux = new FluxTake(this, n);
        return lastFlux;
    }

    public void subscribe(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        lastFlux.subscribe(new SubscriberImpl(consumer));
    }

    public <T> Flux<T> subscribeOn(Scheduler scheduler) {
        return lastFlux = new FluxSubscribeOn(this, scheduler);
    }

    public <T> Flux<T> publishOn(Scheduler scheduler) {
        return lastFlux = new FluxPublishOn(this, scheduler);
    }
}
