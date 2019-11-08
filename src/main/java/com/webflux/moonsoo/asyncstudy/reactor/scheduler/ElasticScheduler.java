package com.webflux.moonsoo.asyncstudy.reactor.scheduler;

import java.util.Deque;

public class ElasticScheduler implements Scheduler {
    private Deque<MyThread> threadPool = Schedulers.THREAD_POOL;

    @Override
    public Worker createWorker() {
        if(threadPool.isEmpty()) {
            for(int i=0; i<5; i++) {
                threadPool.add(new MyThread());
            }
        } else {
            MyThread myThread = threadPool.getLast();
            if(myThread.getLastTimeStamp() + 1000000 < System.currentTimeMillis()) {
                threadPool.peekLast();
            }
        }
        return new ElasticWorker(threadPool);
    }
}
