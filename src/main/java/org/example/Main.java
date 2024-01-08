package org.example;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(threadpool);

        ListenableFuture<Integer> callback = lExecService.submit(()->{
            try {
                System.out.println("w");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("z");
                return 2;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        Executor executor =  Executors.newSingleThreadExecutor();
        Futures.addCallback(callback, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("ll");
            }
        },executor);

        lExecService.shutdown();
    }
}