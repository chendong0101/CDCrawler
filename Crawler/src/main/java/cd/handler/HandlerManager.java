package cd.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cd.handler.impl.DataOutputHandler;
import cd.handler.impl.GoogleSearchDataHandler;
import cd.handler.impl.IHeartSongsDataHandler;
import cd.handler.impl.YoutubeDetailDataHandler;
import cd.model.CrawlJob;
import cd.model.MetaData;
import cd.scheduler.CrawlJobScheduler;

/**
 * Created by chendong on 16/5/5.
 */
@Component
public class HandlerManager {

    private List<DataHandler> dataHandlers = new ArrayList<>();

    @Autowired
    private CrawlJobScheduler crawlJobScheduler;

    @PostConstruct
    public void init() {
        dataHandlers.add(new DataOutputHandler());
        dataHandlers.add(new YoutubeDetailDataHandler(crawlJobScheduler));
        dataHandlers.add(new GoogleSearchDataHandler());
        dataHandlers.add(new IHeartSongsDataHandler());
    }

    public void handleData(MetaData parseData, CrawlJob job) {
        if (job == null) {
            return;
        }
        String url = job.getUrl();
        if (StringUtils.isEmpty(url) || parseData == null) {
            return;
        }
        for (DataHandler handler : dataHandlers) {
            if (handler.matchUrl(url)) {
                handler.handle(parseData, job);
            }
        }
    }
}
