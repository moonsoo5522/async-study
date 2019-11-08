package com.webflux.moonsoo.asyncstudy.reactor;

public class FluxTake<T> extends FluxOperator<T, T>{
    private final long n;

    protected FluxTake(Flux<? extends T> source, long n) {
        super(source);
        this.n = n;
    }

    @Override
    public void subscribe(Subscriber<? super T> sub) {
        this.source.subscribe(new TakeSubscriber<>(sub, n));
    }

    class TakeSubscriber<T> implements Subscription, Subscriber<T> {
        private Subscriber<? super T> actual;
        private Subscription parentS;

        private long cnt;

        public TakeSubscriber(Subscriber<? super T> actual, long cnt) {
            this.actual = actual;
            this.cnt = cnt;
        }

        @Override
        public void request(long var1) {
            System.out.println("request thread : " + Thread.currentThread().getName());
            parentS.request(var1);
        }

        @Override
        public void onSubscribe(Subscription var1) {
            this.parentS = var1;
            this.actual.onSubscribe(this);
        }

        @Override
        public void onNext(T var1) {
            if(cnt-- <= 0) {
                return;
            }
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
    }
}
