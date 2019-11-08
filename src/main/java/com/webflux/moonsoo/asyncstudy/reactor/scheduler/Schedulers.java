package com.webflux.moonsoo.asyncstudy.reactor.scheduler;

import java.util.ArrayDeque;
import java.util.Deque;

public class Schedulers {
    public static final Deque<MyThread> THREAD_POOL = new ArrayDeque<>();

    static {
        for(int i=0; i<10; i++) {
            THREAD_POOL.addFirst(new MyThread());
        }
    }

    public static Scheduler elastic() {
        return new ElasticScheduler();
    }
}
