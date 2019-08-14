package com.icbc.index.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bytedeco.opencv.presets.opencv_core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * token的获取类
 * 将apiKey和secretKey换取token，注意有效期保存在expiresAt
 */
public class TokenHolder {



    public static final String TTS_SCOPE = "audio_tts_post";

    Logger logger = LoggerFactory.getLogger(TokenHolder.class);
    /**
     * url , Token的url，http可以改为https
     */
    private String tokenUrl;

    /**
     * asr的权限 scope 是  "audio_voice_assistant_get"
     * tts 的权限 scope 是 "audio_tts_post"
     */
    private String scope;

    /**
     * 网页上申请语音识别应用获取的apiKey
     */
    private String apiKey;

    /**
     * 网页上申请语音识别应用获取的secretKey
     */
    private String secretKey;

    /**
     * 保存访问接口获取的token
     */
    private String token;

    /**
     * 当前的时间戳，毫秒
     */
    private long expiresAt;

    /**
     * @param apiKey    网页上申请语音识别应用获取的apiKey
     * @param secretKey 网页上申请语音识别应用获取的secretKey
     */
    public TokenHolder(String apiKey, String secretKey, String scope,String tokenUrl) {
        this.tokenUrl = tokenUrl;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.scope = scope;
    }


    /**
     * 获取token，refresh 方法后调用有效
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取过期时间，refresh 方法后调用有效
     *
     * @return
     */
    public long getExpiresAt() {
        return expiresAt;
    }




    /**
     * 获取token
     *
     * @return
     * @throws IOException   http请求错误
     * @throws DemoException http接口返回不是 200, access_token未获取
     */
    public void refresh() throws IOException, DemoException {

        String getTokenURL = tokenUrl + "?grant_type=client_credentials"
                + "&client_id=" + ConnUtil.urlEncode(apiKey) + "&client_secret=" + ConnUtil.urlEncode(secretKey);
        RestTemplate template=new RestTemplate();
        JSONObject object = template.postForObject(getTokenURL, null, JSONObject.class);
        if (object==null)
            throw new DemoException("fail to get token of baidu api");
        if (!object.containsKey("access_token")) {
            // 返回没有access_token字段
            throw new DemoException("access_token not obtained, " );
        }
        if (!object.containsKey("scope")) {
            // 返回没有scope字段
            throw new DemoException("scopenot obtained, " );
        }

        if (scope != null && !object.getString("scope").contains(scope)) {
            throw new DemoException("scope not exist, " + scope + "," );
        }
        token = object.getString("access_token");
        expiresAt = System.currentTimeMillis() + object.getLong("expires_in") * 1000;
        System.out.println("token持续时间:"+object.getLong("expires_in")/24/3600);

    }


}
