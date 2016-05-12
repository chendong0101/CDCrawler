package cd.handler;

import cd.model.CrawlJob;
import cd.model.MetaData;

/**
 * Created by chendong on 16/5/5.
 */
public interface DataHandler {

    boolean matchUrl(String url);

    void handle(MetaData parseData, CrawlJob job);
}
