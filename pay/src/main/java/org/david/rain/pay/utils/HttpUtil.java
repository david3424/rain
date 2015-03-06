package org.david.rain.pay.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class HttpUtil {
    private final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    //get方法封装
    public String getRequest(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(get);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //post方法封装
    public String postRequest(String url, Map<String, Object> rawParams) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            if (rawParams != null && rawParams.size() > 0) {
                List<NameValuePair> params = new ArrayList<>();
                for (String key : rawParams.keySet()) {
                    params.add(new BasicNameValuePair(key, rawParams.get(key).toString()));
                }
                post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            }
            CloseableHttpResponse httpResponse = httpclient.execute(post);
            int returncode = httpResponse.getStatusLine().getStatusCode();
            if (returncode == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            } else {
                return "{\"status\":" + returncode + "}";
            }
        } catch (Exception e) {
            logger.error("response is break ,the post abort ");
            e.printStackTrace();
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
