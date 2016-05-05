package cd.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cd.handler.impl.YoutubeDetailDataHandler;
import cd.model.ParseData;

/**
 * Created by chendong on 16/5/5.
 */
public class HandlerManager {

    private List<DataHandler> dataHandlers = new ArrayList<>();

    public HandlerManager() {
        init();
    }
    private void init() {
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
