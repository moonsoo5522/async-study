package com.webflux.moonsoo.asyncstudy.reactor.scheduler;

public class MyThread extends Thread {
    private Runnable r;
    private long lastTimeStamp = System.currentTimeMillis();

    public MyThread() {
        super();
    }

    public void setRunnable(Runnable r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            r.run();
        } finally {
            lastTimeStamp = System.currentTimeMillis();
            Schedulers.THREAD_POOL.add(this);
        }
    }

    public long getLastTimeStamp() {
        return lastTimeStamp;
    }
}
