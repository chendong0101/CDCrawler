package cd.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cd.handler.impl.YoutubeDetailDataHandler;
import cd.model.ParseData;

/**
 * Created by chendong on 16/5/5.
 */
@Component
public class HandlerManager {

    private List<DataHandler> dataHandlers = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("PosConstruct");
        dataHandlers.add(new YoutubeDetailDataHandler());
    }

    public void handleData(ParseData parseData, String url) {
        if (StringUtils.isEmpty(url) || parseData == null) {
            return;
        }
        for (DataHandler handler : dataHandlers) {
            if (handler.matchUrl(url)) {
                handler.handle(parseData);
            }
        }
    }
}
