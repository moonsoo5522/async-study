package com.webflux.moonsoo.asyncstudy.reactor;

public class JustSubscription<T> implements Subscription {
    final T value;
    final Subscriber<? super T> actual;

    JustSubscription(T data, Subscriber<? super T> s) {
        this.value = data;
        this.actual = s;
    }

    @Override
    public void request(long var1) {
        actual.onNext(value);
        actual.onComplete();
    }
}
