package cd.scheduler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cd.crawler.SynCrawler;
import cd.handler.HandlerManager;
import cd.parser.SynParser;

/**
 * Created by chendong on 16/5/6.
 */
@Component
public class CrawlJobScheduler {

    @Autowired
    private SynCrawler crawler;

    @Autowired
    private SynParser parser;

    @Autowired
    private HandlerManager handlerManager;

    private CrawlJobQueue generalQueue = new CrawlJobQueue();

    private CrawlJobQueue urgencyQueue = new CrawlJobQueue();

    private static final Integer GENERAL_RUNNER_SIZE = 10;
    private static final Integer URGENCY_RUNNER_SIZE = 5;

    ExecutorService executorService = Executors.newFixedThreadPool(21,
            new BasicThreadFactory.Builder().build());

    @PostConstruct
    public void init() {
        for (int i = 0; i < GENERAL_RUNNER_SIZE; ++i) {
            executorService.execute(new CrawlJobRunner(parser, crawler, handlerManager, generalQueue));
        }
        for (int i = 0; i < URGENCY_RUNNER_SIZE; ++i) {
            executorService.execute(new CrawlJobRunner(parser, crawler, handlerManager, urgencyQueue));
        }
    }

    public void putCrawlJob(int priority, String url) {
        if (priority <= 1) {
            generalQueue.putCrawlJob(url);
        } else {
            urgencyQueue.putCrawlJob(url);
        }
    }

}
