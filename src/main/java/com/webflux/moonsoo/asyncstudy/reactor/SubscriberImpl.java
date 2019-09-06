package com.webflux.moonsoo.asyncstudy.reactor;

public class SubscriberImpl<T> implements Subscriber<T> {
    private final Consumer consumer;
    private Subscription subscription;

    public SubscriberImpl(Consumer<? super T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        s.request(1);
    }

    @Override
    public void onNext(T data) {
        consumer.accept(data);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete called");
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
