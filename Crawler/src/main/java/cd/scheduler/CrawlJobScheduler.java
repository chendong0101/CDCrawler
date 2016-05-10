package cd.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cd.model.CrawlJob;

/**
 * Created by chendong on 16/5/6.
 */
@Component
public class CrawlJobScheduler {

    @Autowired
    CrawlJobThread jobThread;

    private CrawlJobQueue urgencyQueue = new CrawlJobQueue();

    @PostConstruct
    public void init() {
        jobThread.bindQueue(urgencyQueue);
    }

    public void putCrawlJob(CrawlJob job) {
        urgencyQueue.putCrawlJob(job);
    }

}
