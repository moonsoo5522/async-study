package com.webflux.moonsoo.asyncstudy.nio;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NioRequestMapping {
    String path();
}
