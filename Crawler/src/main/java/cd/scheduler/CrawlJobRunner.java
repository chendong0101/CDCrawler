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

    private String url;

    private SynParser parser;

    private SynCrawler crawler;

    private HandlerManager handlerManager;

    public CrawlJobRunner(String url, SynParser parser,
                          SynCrawler crawler, HandlerManager handlerManager) {
        this.url = url;
        this.parser = parser;
        this.crawler = crawler;
        this.handlerManager = handlerManager;
    }

    @Override
    public void run() {
        try {
            String html = crawler.getPage(url);
            ParseData data = parser.parseHtml(html, url);
            handlerManager.handleData(data, url);
        } catch (IOException e) {

        }
    }
}
