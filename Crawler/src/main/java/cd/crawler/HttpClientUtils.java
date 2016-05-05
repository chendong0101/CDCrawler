package cd.crawler;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by chendong on 16/4/29.
 */
public class HttpClientUtils {

    public static HttpClient getNewClient(int soTimeout, int connTimeout) {
        return getNewClient(soTimeout, connTimeout, 100, 20, 2097152);
    }

    public static HttpClient getNewClient(int soTimeout, int connTimeout, int maxConn, int maxConnPerIp, int maxContentSize) {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setMaxTotalConnections(maxConn);
        connectionManager.getParams().setConnectionTimeout(connTimeout);
        connectionManager.getParams().setSoTimeout(soTimeout);
        connectionManager.getParams().setSendBufferSize(maxContentSize);
        connectionManager.getParams().setReceiveBufferSize(maxContentSize);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnPerIp);
        return new HttpClient(connectionManager);
    }

    public static GetMethod getNewGet(String url) {
        GetMethod get = new GetMethod(url);
        get.addRequestHeader("Accept-Encoding", "gzip");
        get.addRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1");
        get.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        get.setFollowRedirects(true);
        get.getParams().setCookiePolicy("ignoreCookies");
        return get;
    }

    public static PostMethod getNewPost(String url) {
        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Accept-Encoding", "gzip");
        post.addRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1");
        post.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        post.setFollowRedirects(true);
        post.getParams().setCookiePolicy("ignoreCookies");
        return post;
    }

    public static PostMethod getNewPostWithAppkey(String url) {
        PostMethod post = getNewPost(url);
        post.addParameter("appkey", "21381238124vjsgjsdjvx!@#!@#%%@#^#6");
        post.addParameter("app", "alpha");
        return post;
    }

    public static void setCookie(PostMethod post, String value) {
        Header header = post.getRequestHeader("Cookie");
        if(header == null) {
            header = new Header();
            header.setName("Cookie");
            header.setValue(value);
            post.addRequestHeader(header);
        } else {
            header.setValue(value);
        }

    }

    public static void addCookie(PostMethod post, String name, String value) {
        Header header = post.getRequestHeader("Cookie");
        if(header == null) {
            header = new Header();
            header.setName("Cookie");
            header.setValue(name + "=" + value);
            post.addRequestHeader(header);
        } else {
            header.setValue(header.getValue() + ";" + name + "=" + value);
        }

    }
}
