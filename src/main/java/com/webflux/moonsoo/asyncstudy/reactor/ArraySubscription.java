package com.webflux.moonsoo.asyncstudy.reactor;

public class ArraySubscription<T> implements Subscription {
    final T[] values;
    final Subscriber<? super T> actual;

    ArraySubscription(T[] values, Subscriber<? super T> s) {
        this.values = values;
        this.actual = s;
    }

    @Override
    public void request(long var1) {
        for(T data : values) {
            actual.onNext(data);
        }
        actual.onComplete();
    }
}
