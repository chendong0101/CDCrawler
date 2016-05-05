package cd.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chendong on 16/4/29.
 */
@Slf4j
public class SynCrawler {

    public static int DEFAULT_RETRY_TIMES = 3;

    private HttpClient client;

    public SynCrawler() {
        client = HttpClientUtils.getNewClient(10000, 20000);
    }

    public String getPage(String url) throws IOException {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        GetMethod get = HttpClientUtils.getNewGet(url);
        try {
            int retCode = client.executeMethod(get);
            String response = getResponseBodyAsString(get);
            if (retCode != HttpStatus.SC_OK) {
                throw new IOException("status code: " + retCode
                        + ", response: " + response);
            }
            return response;
        } finally {
            get.releaseConnection();
        }
    }

    public String getPageWithRetrying(String url) {
        return getPageWithRetrying(url, DEFAULT_RETRY_TIMES);
    }

    public String getPageWithRetrying(String url, int retryCount) {
        String page = null;
        int i;
        for (i = 0; i < retryCount; i++) {
            try {
                page = getPage(url);
                break;
            } catch (IOException e) {
            }
        }
        if (page == null) {
        }
        return page;
    }

    private String getResponseBodyAsString(GetMethod get) throws IOException {
        if(get.getResponseBody() == null) {
            return null;
        } else if(get.getResponseHeader("Content-Encoding") != null &&
                get.getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip") > -1) {
            InputStream is = get.getResponseBodyAsStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, get.getResponseCharSet());
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            String tempbf;
            while((tempbf = br.readLine()) != null) {
                sb.append(tempbf);
                sb.append("\r\n");
            }

            isr.close();
            gzin.close();
            return sb.toString();
        } else {
            return get.getResponseBodyAsString();
        }
    }
}
