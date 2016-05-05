import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chendong on 15-11-4.
 */
public class ThreadFutureTest {

    static AtomicInteger num = new AtomicInteger(0);

    public static void testCompletableFuture() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            String s = "a";
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s;
        });
        long deadlineTSMillis = System.currentTimeMillis() + 100;
        synchronized(future) {
            while (!future.isDone()) {
                try {
                    future.wait(20);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        long millisFromDeadline = System.currentTimeMillis() - deadlineTSMillis;
        if (millisFromDeadline > 0) {
            System.out.println(num.getAndAdd(1) + "\ttime out: " + millisFromDeadline);
            return;
        }
        try {
            System.out.println(num.getAndAdd(1) + ": \t" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; ++i) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ThreadFutureTest.testCompletableFuture();
                }
            });
        }
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        executorService.shutdown();
        System.exit(0);
    }







    static ExecutorService executorService = Executors.newFixedThreadPool(1000);

    public static void futureGetTest(final long beginTime) {
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    for (int i = 0; i < 10; ++i) {
                        Thread.sleep(20);
                    }
//                    System.out.println("" + (System.currentTimeMillis() - beginTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "aaaa";
            }
        });
        String a = null;
        try {
            System.out.println("begin: " + (System.currentTimeMillis() - beginTime));
            a = future.get(250, TimeUnit.MILLISECONDS);
            System.out.println("end: " + (System.currentTimeMillis() - beginTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(a);

    }

    public static void completableFutureGetTest(final long beginTime) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                for (int i = 0; i < 10; ++i) {
                    Thread.sleep(20);
                }
                //System.out.println("" + (System.currentTimeMillis() - beginTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "aaaa";
        });

        try {
            System.out.println("begin: " + (System.currentTimeMillis() - beginTime));
            String a = future.get(2000, TimeUnit.MILLISECONDS);
            //String a = future.get();
            System.out.println(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("end: " + (System.currentTimeMillis() - beginTime));
    }

}
