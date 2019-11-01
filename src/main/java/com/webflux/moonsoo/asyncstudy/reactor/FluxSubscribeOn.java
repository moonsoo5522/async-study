package com.webflux.moonsoo.asyncstudy.reactor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FluxSubscribeOn<T> extends FluxOperator<T, T>  {
    private ExecutorService execService = Executors.newFixedThreadPool(10);
    private Flux<? extends T> source;


    protected FluxSubscribeOn(Flux<? extends T> source) {
        super(source);
        this.source = source;
    }

    @Override
    public void subscribe(Subscriber<? super T> sub) {
        SubscribeOnSubscriber<T> parentSubscriber = new SubscribeOnSubscriber<>(sub);
        //sub.onSubscribe(parentSubscriber);
        execService.execute(parentSubscriber);
    }

    class SubscribeOnSubscriber<T> implements Subscriber<T>, Subscription, Runnable {
        private Subscriber<? super T> actual;
        private Subscription parentSubscription;

        public SubscribeOnSubscriber(Subscriber<? super T> sub) {
            this.actual = sub;
        }


        @Override
        public void request(long var1) {
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
