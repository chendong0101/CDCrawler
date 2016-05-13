package cd;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cd.model.CrawlJob;
import cd.scheduler.CrawlJobScheduler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chendong on 16/4/29.
 */
@Slf4j
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
        log.info("Crawl job started!");
    }
}
