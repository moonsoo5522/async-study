package com.webflux.moonsoo.asyncstudy.reactor;

import java.io.IOException;

public interface Consumer<T> {

    void accept(T t) throws IOException;
}
