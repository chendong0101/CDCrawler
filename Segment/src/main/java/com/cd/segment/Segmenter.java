package com.cd.segment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

/**
 * Created by chendong on 16-1-17.
 */
public class Segmenter extends AbstractHandler{

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        if (StringUtils.equals(target, "/segment")) {
            doSegment(request, response);
        }
    }

    private void doSegment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String query = request.getParameter("query");
        StringBuilder responseBoday = new StringBuilder();
        responseBoday.append("<h1>");
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(query, JiebaSegmenter.SegMode.SEARCH);
        for (SegToken token : tokens) {
            responseBoday.append(token.word).append(",");
        }
        responseBoday.append("</h1>");
        response.getWriter().println(responseBoday.toString());
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new Segmenter());

        server.start();
        server.join();
    }
}
