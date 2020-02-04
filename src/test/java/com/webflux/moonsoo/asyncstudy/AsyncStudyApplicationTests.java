package com.webflux.moonsoo.asyncstudy;

import com.webflux.moonsoo.asyncstudy.nio.NioStudy;
import com.webflux.moonsoo.asyncstudy.reactor.Flux;
import com.webflux.moonsoo.asyncstudy.reactor.scheduler.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

    @Test
    public void test() throws InterruptedException, IOException {
        NioStudy nioStudy = new NioStudy();
        Thread thread = new Thread(nioStudy);
        thread.start();

        Thread.sleep(1000);

        clientSocketTask();
        thread.join();
    }

    private void clientSocketTask() {
        for(int i=0; i<10; i++) {
            final int cnt = i;
            String a = "";
            for(int j=0; j<1000; j++) {
                a = a.concat(Integer.toString(i));
            }
            String finalA = a;
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 12345);
                    OutputStream outputStream = socket.getOutputStream();

                    outputStream.write(toByteArray(finalA.length()));
                    outputStream.write(finalA.getBytes());

                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = inputStream.readAllBytes();
                    System.out.println("response : " + new String(bytes, StandardCharsets.UTF_8));
                } catch (Exception e) {

                }
            }).start();
        }
    }

    private byte[] toByteArray(int value) {
        return new byte[] {
                (byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)value };
    }
}
