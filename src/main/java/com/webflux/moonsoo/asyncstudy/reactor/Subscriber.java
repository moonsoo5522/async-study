package com.webflux.moonsoo.asyncstudy.reactor;

public interface Subscriber<T> {

    void onSubscribe(Subscription var1);

    void onNext(T var1);

    void onComplete();

    void onError(Throwable t);
}
