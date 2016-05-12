package cd.handler.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import cd.handler.DataHandler;
import cd.model.CrawlJob;
import cd.model.JSONSerializerUtils;
import cd.model.MetaData;
import cd.scheduler.CrawlJobScheduler;

/**
 * Created by chendong on 16/5/5.
 */
public class YoutubeDetailDataHandler implements DataHandler {

    private CrawlJobScheduler crawlJobScheduler;

    private OutputStreamWriter output;

    private static final String URL_REG = "www\\.youtube\\.com\\/watch.*v=.*";

    private static final String YOUTUBE_HOST = "https://www.youtube.com";

    public YoutubeDetailDataHandler(CrawlJobScheduler crawlJobScheduler) {
        this.crawlJobScheduler = crawlJobScheduler;
        try {
            String outFile = "./youtube-detail.txt";
            output = new OutputStreamWriter(new FileOutputStream(outFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean matchUrl(String url) {
        Pattern pattern = Pattern.compile(URL_REG);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void handle(MetaData parseData, CrawlJob job) {
        System.out.println(job.getLevel());
        outputDatas(parseData);
        publishCrawlJobs(parseData, job);
    }

    private void outputDatas(MetaData parseData) {
        try {
            String json = JSONSerializerUtils.toJson(parseData);
            output.write(json + "\n");
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void publishCrawlJobs(MetaData parseData, CrawlJob job) {
        if (job.getLevel() < 2) {
            for (MetaData.Entry entry : parseData.getEntries()) {
                if (entry.type.equals(MetaData.DataType.URL)) {
                    CrawlJob newJob = new CrawlJob();
                    String url = entry.data;
                    if (StringUtils.isEmpty(url)) {
                        continue;
                    }
                    if (StringUtils.startsWith(url, "/")) {
                        url = YOUTUBE_HOST + url;
                    }
                    newJob.setUrl(url);
                    newJob.setLevel(job.getLevel() + 1);
                    crawlJobScheduler.putCrawlJob(newJob);
                } else if (entry.type.equals(MetaData.DataType.SUBDATAS)) {
                    for (MetaData subData : entry.subDatas) {
                        publishCrawlJobs(subData, job);
                    }
                }
            }
        }
    }

}
