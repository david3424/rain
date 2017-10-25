package org.david.rain.common.utils;

import okhttp3.*;
import org.david.rain.common.logback.LoggerUtil;
import org.david.rain.common.text.SignatureUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttpTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType FILE = MediaType.parse("application/multipart/form-data; charset=utf-8");
    private static final String URL = "https://www.okcoin.com/api/v1/order_history.do";
    private OkHttpClient client = new OkHttpClient();

    @Test
    public void okHttpTest() throws IOException {

        Request request = new Request.Builder().url("https://www.baidu.com/").build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            LoggerUtil.info("response:{}", response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Test
    public void postTest() throws Exception {
        RequestBody body = RequestBody.create(JSON, "{\"test\":\"66\"}");
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            LoggerUtil.info("response:{}", response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Test
    public void mapTest() throws Exception {
        Map<String, Object> parmsMap = new HashMap<>();
        parmsMap.put("api_key", "1d3b6743-789f-4bc1-9fa3-fe3890a3f5fe");
        parmsMap.put("symbol", "btc_usd");
        parmsMap.put("status", "0");
        parmsMap.put("current_page", "1");
        parmsMap.put("page_length", "50");
        String signResult = SignatureUtil.signatureWithParamName(parmsMap, "BF20B18B87E48DD1D97728F55B7B45F1", "md5");
        parmsMap.put("sign", signResult.toUpperCase());
        LoggerUtil.info("md5 result :{}", signResult);

        String postBody = "";
        if (parmsMap != null && parmsMap.size() > 0) {
            for (String key : parmsMap.keySet()) {
                if (null != parmsMap.get(key)) {
                    postBody = postBody + key + "=" + parmsMap.get(key) + "&";
                }
            }
            LoggerUtil.info("params is {}", postBody);
            RequestBody formBody = RequestBody.create(FORM, postBody);

            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            LoggerUtil.info("response:{}", response.body().string());
            if (response.isSuccessful()) {
                LoggerUtil.info("SUCCESS");
            } else {
                LoggerUtil.info("response:{}", response.body().string());
                throw new IOException("Unexpected code " + response);
            }
        }

    }

}


