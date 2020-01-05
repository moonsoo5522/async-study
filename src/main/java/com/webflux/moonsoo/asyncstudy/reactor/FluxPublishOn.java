package com.webflux.moonsoo.asyncstudy.reactor;

import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Scheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FluxPublishOn<T> extends FluxOperator<T, T>  {
    private Flux<? extends T> source;
    private Scheduler scheduler;


    protected FluxPublishOn(Flux<? extends T> source, Scheduler scheduler) {
        super(source);
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void subscribe(Subscriber<? super T> sub) {
        source.subscribe(new PublishOnSubscriber<>(sub, scheduler));
    }

    class PublishOnSubscriber<T> implements Subscriber<T>, Subscription, Runnable {
        private Queue<T> queue = new ConcurrentLinkedQueue<>();

        private Scheduler scheduler;
        private Subscriber<T> actual;
        private Subscription s;
        long cnt;

        public PublishOnSubscriber(Subscriber<T> actual, Scheduler scheduler) {
            this.actual = actual;
            this.scheduler = scheduler;
        }

        @Override
        public void onSubscribe(Subscription var1) {
            actual.onSubscribe(var1);
            s.request(cnt);
        }

        @Override
        public void onNext(T var1) {
            queue.add(var1);
            scheduler.createWorker().schedule(this);
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
        public void request(long var1) {
            // NTD
        }

        @Override
        public void run() {
            while(!queue.isEmpty()) {
                actual.onNext(queue.poll());
            }
        }
    }
}
