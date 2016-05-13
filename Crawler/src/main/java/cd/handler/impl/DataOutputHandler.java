package cd.handler.impl;

import java.io.IOException;

import cd.handler.DataHandler;
import cd.model.CrawlJob;
import cd.model.JSONSerializerUtils;
import cd.model.MetaData;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chendong on 16/5/13.
 */
@Slf4j
public class DataOutputHandler implements DataHandler {
    @Override
    public boolean matchUrl(String url) {
        return true;
    }

    @Override
    public void handle(MetaData parseData, CrawlJob job) {
        try {
            String json = JSONSerializerUtils.toJson(parseData);
            System.out.println(json);
            log.info(json);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
