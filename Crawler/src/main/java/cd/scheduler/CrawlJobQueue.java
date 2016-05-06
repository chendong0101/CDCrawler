package cd.scheduler;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chendong on 16/5/6.
 */
public class CrawlJobQueue {

    private LinkedBlockingQueue<String> blockingQueue;

    public CrawlJobQueue() {
        init();
    }

    public void init() {
        blockingQueue = new LinkedBlockingQueue();
    }

    public String getCrawlJob() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putCrawlJob(String url) {
        try {
            blockingQueue.put(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
