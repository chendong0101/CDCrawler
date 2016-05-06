package cd.scheduler;

import java.io.IOException;

import cd.crawler.SynCrawler;
import cd.handler.HandlerManager;
import cd.model.ParseData;
import cd.parser.SynParser;

/**
 * Created by chendong on 16/5/5.
 */
public class CrawlJobRunner implements Runnable {

    private SynCrawler crawler;

    private SynParser parser;

    private HandlerManager handlerManager;

    private CrawlJobQueue jobQueue;

    public CrawlJobRunner(SynParser parser, SynCrawler crawler,
                          HandlerManager handlerManager, CrawlJobQueue jobQueue) {
        this.parser = parser;
        this.crawler = crawler;
        this.handlerManager = handlerManager;
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = jobQueue.getCrawlJob();
                System.out.println(url);
                String html = crawler.getPage(url);
                ParseData data = parser.parseHtml(html, url);
                handlerManager.handleData(data, url);
                Thread.sleep(10);
            } catch (IOException | InterruptedException e) {

            }
        }
    }
}
