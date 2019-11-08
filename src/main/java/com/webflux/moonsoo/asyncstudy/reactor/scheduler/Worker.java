package com.webflux.moonsoo.asyncstudy.reactor.scheduler;

import java.util.concurrent.Executors;

public interface Worker {


    void schedule(Runnable r);
}
