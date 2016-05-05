package cd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import cd.crawler.SynCrawler;
import cd.handler.HandlerManager;
import cd.parser.SynParser;
import cd.scheduler.CrawlJobRunner;

/**
 * Created by chendong on 16/4/29.
 */
public class CrawlerMain {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(21,
                new BasicThreadFactory.Builder().build());
        SynCrawler crawler = new SynCrawler();
        SynParser parser = new SynParser();
        HandlerManager handlerManager = new HandlerManager();
        List<String> urls = new ArrayList();
        urls.add("https://www.youtube.com/watch?v=N8mV27HBo6Q");
        urls.add("https://www.youtube.com/watch?v=wk5PFS3VQAI");
        urls.add("https://www.youtube.com/watch?v=ekgNJ3FdCNQ");
        urls.add("https://www.youtube.com/watch?v=CxzwOhJalGQ");
        urls.add("https://www.youtube.com/watch?v=ekgNJ3FdCNQ");
        urls.add("https://www.youtube.com/watch?v=ekgNJ3FdCNQ");
        for (String url : urls) {
            executorService.execute(new CrawlJobRunner(url, parser, crawler, handlerManager));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished");
    }
}
