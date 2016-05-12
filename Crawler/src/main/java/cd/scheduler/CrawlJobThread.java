package cd.scheduler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cd.crawler.SynCrawler;
import cd.handler.HandlerManager;
import cd.model.CrawlJob;
import cd.model.MetaData;
import cd.parser.SynParser;

/**
 * Created by chendong on 16/5/5.
 */
@Component
public class CrawlJobThread {

    @Autowired
    private SynCrawler crawler;

    @Autowired
    private SynParser parser;

    @Autowired
    private HandlerManager handlerManager;

    private static final Integer JOB_RUNNER_SIZE = 5;

    private ExecutorService executorService = Executors.newFixedThreadPool(21,
            new BasicThreadFactory.Builder().build());

    public void bindQueue(CrawlJobQueue queue) {
        for (int i = 0; i < JOB_RUNNER_SIZE; ++i) {
            executorService.execute(new CrawlJobRunner(queue));
        }
    }

    private class CrawlJobRunner implements Runnable {

        private CrawlJobQueue jobQueue;

        public CrawlJobRunner(CrawlJobQueue jobQueue) {
            this.jobQueue = jobQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    CrawlJob job = jobQueue.getCrawlJob();
                    String url = job.getUrl();
                    System.out.println(url);
                    String html = crawler.getPage(url);
                    MetaData data = parser.parseHtml(html, url);
                    handlerManager.handleData(data, job);
                    Thread.sleep(10);
                } catch (IOException | InterruptedException e) {

                }
            }
        }
    }
}
