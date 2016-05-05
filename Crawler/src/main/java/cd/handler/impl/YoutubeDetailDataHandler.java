package cd.handler.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cd.handler.DataHandler;
import cd.model.JSONSerializerUtils;
import cd.model.ParseData;

/**
 * Created by chendong on 16/5/5.
 */
public class YoutubeDetailDataHandler implements DataHandler {

    private OutputStreamWriter output;

    private static final String URL_REG = "www\\.youtube\\.com\\/watch.*v=.*";

    public YoutubeDetailDataHandler() {
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
    public void handle(ParseData parseData) {
        try {
            String json = JSONSerializerUtils.toJson(parseData);
            output.write(json + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
