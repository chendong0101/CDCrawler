package cd.handler;

import cd.model.ParseData;

/**
 * Created by chendong on 16/5/5.
 */
public interface DataHandler {
    boolean matchUrl(String url);
    void handle(ParseData parseData);
}