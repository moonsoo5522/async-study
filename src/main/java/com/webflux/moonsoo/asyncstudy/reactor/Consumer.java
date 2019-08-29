package com.webflux.moonsoo.asyncstudy.reactor;

public interface Consumer<T> {

    void accept(T t);
}
