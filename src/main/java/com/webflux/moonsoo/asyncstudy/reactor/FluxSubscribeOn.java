package com.webflux.moonsoo.asyncstudy.reactor;

import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Scheduler;
import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Worker;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FluxSubscribeOn<T> extends FluxOperator<T, T>  {
    private Flux<? extends T> source;
    private Scheduler scheduler;


    protected FluxSubscribeOn(Flux<? extends T> source, Scheduler scheduler) {
        super(source);
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void subscribe(Subscriber<? super T> sub) {
        SubscribeOnSubscriber<T> parentSubscriber = new SubscribeOnSubscriber<>(sub, source);
        //sub.onSubscribe(parentSubscriber);
        scheduler.createWorker().schedule(parentSubscriber);
    }

     class SubscribeOnSubscriber<T> implements Subscriber<T>, Subscription, Runnable {
        private Subscriber<? super T> actual;
        private Subscription parentSubscription;
        private Publisher<? extends T> source;

        SubscribeOnSubscriber(Subscriber<? super T> sub, Publisher<? extends T> source) {
            this.actual = sub;
            this.source = source;
        }


        @Override
        public void request(long var1) {
            System.out.println("request thread : " + Thread.currentThread().getName());
            parentSubscription.request(var1);
        }

        @Override
        public void onSubscribe(Subscription var1) {
            this.parentSubscription = var1;
            actual.onSubscribe(this);
        }

        @Override
        public void onNext(T var1) {
            actual.onNext(var1);
        }

        @Override
        public void onComplete() {
            actual.onComplete();
        }

        @Override
        public void onError(Throwable t) {
            actual.onError(t);
        }

        @Override
        public void run() {
            //this.onSubscribe(this);
            source.subscribe(this);
        }
    }
}
