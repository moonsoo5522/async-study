package com.webflux.moonsoo.asyncstudy.reactor;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> var1);
}
