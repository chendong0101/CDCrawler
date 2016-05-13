package cd.handler.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cd.handler.DataHandler;
import cd.model.CrawlJob;
import cd.model.JSONSerializerUtils;
import cd.model.MetaData;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chendong on 16/5/13.
 */
@Slf4j
public class GoogleSearchDataHandler implements DataHandler {

    private static final String URL_REG = "www\\.google\\.com.*";

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
        try {
            String json = JSONSerializerUtils.toJson(parseData);
            System.out.println(json);
            log.info(json);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
