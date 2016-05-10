package cd.model;

import java.io.Serializable;

/**
 * Created by chendong on 16/5/10.
 */
public class CrawlJob implements Serializable {
    private String url;
    private int level;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
