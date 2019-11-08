package com.webflux.moonsoo.asyncstudy.reactor.scheduler;

import java.util.Deque;
import java.util.Queue;

public class ElasticWorker implements Worker {
    private Deque<MyThread> threadPool;

    public ElasticWorker(Deque<MyThread> threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void schedule(Runnable r) {
        MyThread thread = threadPool.poll();
        thread.setRunnable(r);
        thread.start();
    }
}
