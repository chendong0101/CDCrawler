package cd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cd.scheduler.CrawlJobScheduler;

/**
 * Created by chendong on 16/4/29.
 */
public class CrawlerMain {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "applicationContext-crawler.xml",
        });

        CrawlJobScheduler scheduler = context.getBean(CrawlJobScheduler.class);

        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=N8mV27HBo6Q");
        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=wk5PFS3VQAI");
        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=ekgNJ3FdCNQ");
        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=CxzwOhJalGQ");
        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=ekgNJ3FdCNQ");
        scheduler.putCrawlJob(0, "https://www.youtube.com/watch?v=ekgNJ3FdCNQ");

        System.out.println("Crawl Job started!");
    }
}
