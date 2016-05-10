package cd.scheduler;

import java.util.concurrent.LinkedBlockingQueue;

import cd.model.CrawlJob;

/**
 * Created by chendong on 16/5/6.
 */
public class CrawlJobQueue {

    private LinkedBlockingQueue<CrawlJob> blockingQueue;

    public CrawlJobQueue() {
        init();
    }

    public void init() {
        blockingQueue = new LinkedBlockingQueue();
    }

    public CrawlJob getCrawlJob() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putCrawlJob(CrawlJob job) {
        try {
            blockingQueue.put(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
