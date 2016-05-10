package cd;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cd.model.CrawlJob;
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
        List<String> crawlSeeds = (List<String>)context.getBean("crawlSeeds");

        for (String url : crawlSeeds) {
            CrawlJob job = new CrawlJob();
            job.setLevel(0);
            job.setUrl(url);
            scheduler.putCrawlJob(job);
        }
        System.out.println("Crawl Job started!");
    }
}
