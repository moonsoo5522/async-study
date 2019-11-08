package com.webflux.moonsoo.asyncstudy;

import com.webflux.moonsoo.asyncstudy.reactor.Flux;
import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncStudyApplicationTests {

    @Test
    public void contextLoads() throws InterruptedException {
        Flux<String> sss = Flux.just("sss");
        sss.subscribe(System.out::println);

        Flux<String> array = Flux.just("a", "b", "cc")
                .take(2)
                .subscribeOn(Schedulers.elastic())
                .take(1);
        array.subscribe(System.out::println);
        System.out.println("main function : " + Thread.currentThread().getName());

        Thread.sleep(100);
    }

}
